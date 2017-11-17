package com.unimas.jdbc.handler.entry;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.unimas.common.util.StringUtils;
import com.unimas.jdbc.handler.BaseHandler;

/**
 * <p>描述: Select词句模型</p>
 * @author hxs
 * @date 2017年1月12日 上午10:35:55
 */
public class SelectSqlModal<T> extends SqlModal {
	
	public static final String FIELD_NAME_ALIAS = "A";
	public static final String TABLE_NAME_ALIAS = "T";
	
	private DbType dbType;
	private Class<T> returnClasz;
	private TableData table;
	private List<Condition> conditions = Lists.newArrayList();
	private Map<Column, Integer> columns = Maps.newHashMap();
	private Map<LField, Integer> leftFields = Maps.newHashMap();
	private Map<TableData, Integer> tables = Maps.newHashMap();
	private Map<String, Order> orders = Maps.newLinkedHashMap();
	private List<LeftJoin> lefts = Lists.newArrayList();
	private int limit = -1;
	private int offset = 0;
	private Operator operator = Operator.AND;
	
	/**
	 * 指定数据库类型和返回结果类型
	 * @param dbType
	 */
	public SelectSqlModal(DbType dbType, TableData table, Class<T> returnClasz){
		this.dbType = dbType;
		this.returnClasz = returnClasz;
		this.table = table;
		this.addConditions(this.table);
		putTable(this.table);
	}
	
	/**
	 * 处理结果
	 * @param rs
	 * @return
	 * @throws Exception  
	 */
	public List<T> getResult(ResultSet rs) throws Exception {
		//return ResultSetHandler.listBean(rs, returnClasz);
		List<T> list = null;
		if(rs != null){
			list = new ArrayList<T>();
			ResultSetMetaData md = rs.getMetaData();
			while (rs.next()){
				T t = returnClasz.newInstance();
				Map<Class<?>, Object> map = Maps.newHashMap();
				for(LeftJoin l : this.lefts){
					if(l.autoLoadField != null){
						Class<?> clasz = l.table.getClasz();
						Object obj = clasz.newInstance();
						l.autoLoadField.setAccessible(true);
						l.autoLoadField.set(t, obj);
						l.autoLoadField.setAccessible(false);
						map.put(clasz, obj);
					}
				}
				md = md==null?rs.getMetaData():md;
				for(Column column : this.columns.keySet()){
					Field field = column.field;
					String fieldName = getFieldAlias(column);
					Object value = BaseHandler.getValue(rs, fieldName, field);
					if(value != null){
						if(column.table.getClasz().equals(returnClasz)){
							field.setAccessible(true);
							field.set(t, value);
							field.setAccessible(false);
						} else {
							Object obj = map.get(column.table.getClasz());
							if(obj != null){
								field.setAccessible(true);
								field.set(obj, value);
								field.setAccessible(false);
							}
						}
					}
				}
				for(LField field : this.leftFields.keySet()){
					int index = this.leftFields.get(field);
					String fieldAlias = getFieldAlias(index+this.columns.size());
					Object value = BaseHandler.getValue(rs, fieldAlias, field.field);
					field.field.setAccessible(true);
					field.field.set(t, value);
					field.field.setAccessible(false);
				}
				list.add(t);
			}
		}
		return list;
	}
	
	/**
	 * 获取表的别名
	 * @param index
	 * @return
	 */
	public static String getTableAlias(int index){
		return TABLE_NAME_ALIAS+index;
	}
	
	/**
	 * 获取字段的别名
	 * @param index
	 * @return
	 */
	public static String getFieldAlias(int index){
		return FIELD_NAME_ALIAS+index;
	}
	
	public String getTableAlias(TableData table){
		if(table == null) throw new NullPointerException();
		return getTableAlias(this.tables.get(table));
	}
	
	public String getFieldAlias(Column column){
		if(column == null) throw new NullPointerException();
		return getFieldAlias(this.columns.get(column));
	}
	
	/**
	 * 设置查询条数
	 * @param limit    返回条数，小于0时忽略
	 * @param offset   起始位置，小于0时忽略
	 */
	public void setLimit(int limit, int offset){
		if(limit > 0){
			this.limit = limit;
			if(offset > 0){
				this.offset = offset;
			}
		}
	}
	
	private String createSql(){
		StringBuffer sql = new StringBuffer();
		sql.append("select ");
		boolean firstColumn = true;
		for(Column column : this.columns.keySet()){
			if(firstColumn){
				firstColumn = false;
			} else {
				sql.append(",");
			}
			sql.append(getColumnSql(column));
		}
		if(!this.leftFields.isEmpty()){
			for(LField lf : this.leftFields.keySet()){
				if(firstColumn){
					firstColumn = false;
				} else {
					sql.append(",");
				}
				sql.append(getLeftFieldSql(lf, this.leftFields.get(lf)));
			}
		}
		sql.append(" from ");
		sql.append(getTableSql(table));
		if(this.lefts != null && !this.lefts.isEmpty()){
			for(LeftJoin leftJoin : this.lefts){
				sql.append(getLeftJoinSql(leftJoin));
			}
		}
		if(!this.leftFields.isEmpty()){
			for(LField lf : this.leftFields.keySet()){
				sql.append(getLeftJoinSql(lf, this.leftFields.get(lf)));
			}
		}
		if(this.conditions != null && this.conditions.size() > 0){
			sql.append(" where 1=1 ");
			for(Condition cond : this.conditions){
				sql.append(" "+this.operator.value()+" ");
				sql.append(cond.toSql(this));
			}
		}
		if(this.orders != null && !this.orders.isEmpty()){
			boolean firstOrder = true;
			sql.append(" order by ");
			for(String name : this.orders.keySet()){
				if(firstOrder){
					firstOrder = false;
				} else {
					sql.append(",");
				}
				sql.append(name);
				sql.append(" ");
				sql.append(this.orders.get(name).value());
			}
		}
		String limitSql = getLimitSql();
		if(limitSql != null){
			sql.append(limitSql);
		}
		return sql.toString();
	}
	
	/**
	 * 获取列SQL格式，如： T0.id as A0
	 * @param column
	 * @return
	 */
	private String getColumnSql(Column column){
		StringBuffer sql = new StringBuffer();
		int tableIndex = this.tables.get(column.table);
		String fieldName = BaseHandler.getFieldName(column.field);
		int fieldIndex = this.columns.get(column);
		sql.append(getTableAlias(tableIndex));
		sql.append(".");
		sql.append(fieldName);
		sql.append(" as ");
		sql.append(getFieldAlias(fieldIndex));
		return sql.toString();
	}
	
	/**
	 * 获取列SQL格式，如： T0.id as A0
	 * @param column
	 * @return
	 */
	private String getLeftFieldSql(LField field, int index){
		StringBuffer sql = new StringBuffer();
		int tableIndex = this.tables.size()+index;
		int fieldIndex = this.columns.size()+index;
		sql.append(getTableAlias(tableIndex));
		sql.append(".");
		sql.append(field.name);
		sql.append(" as ");
		sql.append(getFieldAlias(fieldIndex));
		return sql.toString();
	}
	
	/**
	 * 获取表SQL格式，如： user as T0
	 * @param column
	 * @return
	 */
	private String getTableSql(TableData table){
		StringBuffer sql = new StringBuffer();
		sql.append(table.getName());
		sql.append(" as ");
		sql.append(getTableAlias(table));
		return sql.toString();
	}
	
	/**
	 * 获取表SQL格式，如： left join role as T1 on (T1.id = T0.role_id)
	 * @return
	 */
	private String getLeftJoinSql(LField field, int index){
		int tableIndex = this.tables.size()+index;
		String tableAlias = getTableAlias(tableIndex);
		StringBuffer sql = new StringBuffer();
		sql.append(" left join ");
		sql.append(field.joinTable);
		sql.append(" as ");
		sql.append(tableAlias);
		sql.append(" on (");
		sql.append(tableAlias);
		sql.append(".");
		sql.append(field.joinField);
		sql.append(" = ");
		sql.append(getTableAlias(table));
		sql.append(".");
		sql.append(BaseHandler.getFieldName(field.refField));
		sql.append(")");
		return sql.toString();
	}
	
	/**
	 * 获取表SQL格式，如： left join role as T1 on (T1.id = T0.role_id)
	 * @param column
	 * @return
	 */
	private String getLeftJoinSql(LeftJoin leftJoin){
		StringBuffer sql = new StringBuffer();
		sql.append(" left join ");
		sql.append(getTableSql(leftJoin.table));
		sql.append(" on (");
		for(Field field : leftJoin.joins.keySet()){
			Object value = leftJoin.joins.get(field);
			if(value instanceof Field){
				Field v = (Field)value;
				sql.append(getTableAlias(leftJoin.table));
				sql.append(".");
				sql.append(BaseHandler.getFieldName(field));
				sql.append(" = ");
				sql.append(getTableAlias(table));
				sql.append(".");
				sql.append(BaseHandler.getFieldName(v));
			}
		}
		sql.append(")");
		return sql.toString();
	}
	
	private String getLimitSql(){
		if(limit > 0){
			if(DbType.POSTGRES.equals(dbType)){
				return "limit "+limit+" offset "+offset;
			} else if(DbType.MYSQL.equals(dbType)){
				return "limit "+offset+","+limit;
			}
		}
		return null;
	}
	
	protected TableData getTableByClass(Class<?> c){
		if(c != null){
			for(TableData table : this.tables.keySet()){
				if(c.equals(table.getClasz())){
					return table;
				}
			}
		}
		return null;
	}
	
	/**
	 * 添加时间范围参数，如：date >= '2014-11-23 00:00:00' and date <= '2014-11-24 23:59:59'
	 * @param c
	 * @param fieldName
	 * @param startTime
	 * @param endTime
	 */
	public void addDateBetween(Class<?> c, String fieldName, String startTime, Object endTime){
		String field = BaseHandler.getFieldName(c, fieldName);
		TableData table = getTableByClass(c);
		if(startTime != null){
			this.addCondition(table, field, ">=", startTime);
		}
		if(endTime != null){
			this.addCondition(table, field, "<=", endTime);
		}
		
	}
	
	/**
	 * 添加In条件，如: id in (1,2,3)
	 * @param c
	 * @param fieldName
	 * @param values
	 */
	public void addInList(Class<?> c, String fieldName, List<?> values){
		String field = BaseHandler.getFieldName(c, fieldName);
		TableData table = getTableByClass(c);
		this.addCondition(new InCondition(table, field, values));
	}
	
	/**
	 * 添加排序方式
	 * @param c
	 * @param fieldName
	 * @param order
	 */
	public void addOrder(Class<?> c, String fieldName, Order order){
		order = order==null?Order.DESC:order;
		String name = BaseHandler.getFieldName(c, fieldName);
		TableData table = getTableByClass(c);
		if(table != null){
			name = getTableAlias(table)+"."+name;
		}
		this.orders.put(name, order);
	}
	
	/**
	 * 添加查询条件
	 * @param c
	 * @param fieldName
	 * @param operator
	 * @param value
	 */
	public void addCondition(Class<?> c, String fieldName, String operator, Object value){
		this.addCondition(getTableByClass(c), BaseHandler.getFieldName(c, fieldName), operator, value);
	}
	
	protected void addCondition(TableData table, String field, String operator, Object value){
		this.addCondition(new Condition(table, field, value, operator));
	}
	
	private void addCondition(Condition cond){
		if(cond != null){
			this.conditions.add(cond);
		}
	}
	
	private void addConditions(TableData table){
		if(table != null){
			List<DataValue> datas = table.getDatas();
			if(datas != null && datas.size() > 0){
				for(DataValue dv : datas){
					addCondition(table, dv.getField(), "=", dv.getRealValue());
				}
			}
		}
	}
	
	@Override
	public String getSql() {
		if(this.sql == null){
			this.values.clear();
			this.sql = this.createSql();
		}
		return this.sql;
	}
	
	/**
	 * 添加左连接
	 * @param c                   要进行左连接的表对象类
	 * @param fieldName           要左连接的表字段
	 * @param leftJoinFieldName   关联的表字段
	 * @param autoLoadFieldName   加载左连接表信息的字段
	 */
	public void addLeftJoin(Class<?> c, String fieldName, String leftJoinFieldName, String autoLoadFieldName){
		TableData table = BaseHandler.toData(c, TableData.Action.select);
		Field leftField = BaseHandler.getFieldFromClass(c, fieldName);
		Field mainField = BaseHandler.getFieldFromClass(this.table.getClasz(), leftJoinFieldName);
		Field autoLoadField = BaseHandler.getFieldFromClass(this.table.getClasz(), autoLoadFieldName);
		addLeftJoin(table, leftField, mainField, autoLoadField);
	}
	
	/**
	 * 添加左连接
	 * @param c                   要进行左连接的表对象类
	 * @param fieldName           要左连接的表字段
	 * @param leftJoinFieldName   关联的表字段
	 */
	public void addLeftJoin(Class<?> c, String fieldName, String leftJoinFieldName){
		addLeftJoin(c, leftJoinFieldName, leftJoinFieldName, null);
	}
	
	private void addLeftJoin(TableData table, Field leftField, Field mainField, Field autoLoadField){
		LeftJoin leftJoin = new LeftJoin();
		leftJoin.table = table;
		leftJoin.autoLoadField = autoLoadField;
		leftJoin.joins.put(leftField, mainField);
		this.lefts.add(leftJoin);
		this.addConditions(leftJoin.table);
		if(autoLoadField == null){
			this.tables.put(table, this.tables.size());
		} else {
			this.putTable(leftJoin.table);
		}
	}
	
	private void putTable(TableData table){
		this.tables.put(table, this.tables.size());
		this.addColumns(table);
	}
	private void addColumns(TableData table){
		Class<?> clasz = table.getClasz();
		List<Field> list = BaseHandler.getColumnsFromClass(clasz);
		int index = this.columns.size();
		if(list != null && list.size() > 0){
			for(Field field : list){
				Column column = new Column();
				column.table = table;
				column.field = field;
				this.columns.put(column, index++);
			}
		}
		if(this.table.equals(table)){
			List<Field> fields = BaseHandler.getFieldsFromClass(clasz);
			if(fields != null && fields.size() > 0){
				int i = this.leftFields.size();
				for(Field field : fields){
					if(field.isAnnotationPresent(LeftField.class)){
						LeftField leftField = field.getAnnotation(LeftField.class);
						LField lf = new LField();
						String name = leftField.name();
						if(!StringUtils.isNotEmpty(name)){
							name = field.getName();
						}
						lf.name = name;
						lf.field = field;
						lf.joinTable = leftField.joinTable();
						lf.joinField = leftField.joinField();
						String refFieldName = leftField.refField();
						lf.refField = BaseHandler.getFieldFromClass(clasz, refFieldName);
						if(lf.refField != null){
							this.leftFields.put(lf, i++);
						}
					}
				}
			}
		}
	}
	public TableData getTable(){
		return this.table;
	}
	public Operator getOperator() {
		return operator;
	}
	public void setOperator(Operator operator) {
		this.operator = operator;
	}

	/**
	 * <p>描述: 查询条件</p>
	 * @author hxs
	 * @date 2017年1月12日 下午2:01:56
	 */
	public class Condition {
		TableData table;
		String field;
		Object value;
		String operator;
		
		public Condition(TableData table, String field, Object value, String operator){
			this.table = table;
			this.field = field;
			this.value = value;
			this.operator = operator;
		}
		
		String toSql(SelectSqlModal<?> sm){
			StringBuffer sql = new StringBuffer();
			sql.append(getTableAlias(table));
			sql.append(".");
			sql.append(field);
			sql.append(" "+operator);
			sql.append(" ?");
			sm.addValue(value);
			return sql.toString();
		}
	}
	
	/**
	 * <p>描述: in词句</p>
	 * @author hxs
	 * @date 2017年1月12日 下午5:11:24
	 */
	public class InCondition extends Condition {

		public InCondition(TableData table, String field, List<?> values) {
			super(table, field, values, "in");
		}
		
		@SuppressWarnings("unchecked")
		String toSql(SelectSqlModal<?> sm){
			StringBuffer sql = new StringBuffer();
			sql.append(field);
			sql.append(" "+operator);
			sql.append(" (");
			if(this.value != null){
				List<Object> values = (List<Object>)this.value;
				for(int i=0;i<values.size();i++){
					if(i != 0){
						sql.append(",");
					}
					sql.append("?");
					sm.addValue(values.get(i));
				}
			}
			sql.append(")");
			return sql.toString();
		}
		
	}
	
	/**
	 * <p>描述: 左连接</p>
	 * @author hxs
	 * @date 2017年1月12日 下午2:01:56
	 */
	public static class LeftJoin {
		TableData table;
		Field autoLoadField;
		Map<Field, Object> joins = Maps.newHashMap();
	}
	
	/**
	 * <p>描述: 左连接</p>
	 * @author hxs
	 * @date 2017年1月12日 下午2:01:56
	 */
	public static class JoinRef {
		String tableName;
		String fieldName;
		String refFieldName;
		public JoinRef(String table, String field, String refField){
			this.tableName = table;
			this.fieldName = field;
			this.refFieldName = refField;
		}
	}
	
	/**
	 * <p>描述: 列</p>
	 * @author hxs
	 * @date 2017年1月12日 下午2:01:56
	 */
	public static class Column {
		TableData table;
		Field field;
	}
	
	public static enum Operator {
		
		AND("and"),OR("or");
		
		private String v;
		private Operator(String v){
			this.v = v;
		}
		public String value(){ return this.v;};
	}
	
	public static enum Order {
		
		DESC("desc"),ASC("asc");
		
		private String v;
		private Order(String v){
			this.v = v;
		}
		public String value(){ return this.v;};
	}
	
	public static class LField {
		String name;
		Field field;
		String joinTable;
		String joinField;
		Field refField;
	}
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	public static @interface LeftField {
		/**
		 * 字段名
		 * @return
		 */
		public String name() default "";
		/**
		 * 连接的表名
		 * @return
		 */
		public String joinTable();
		/**
		 * 连接的字段名
		 * @return
		 */
		public String joinField();
		/**
		 * 关联的字段名
		 * @return
		 */
		public String refField();
	}

}
