package com.unimas.web.auth;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.unimas.jdbc.DBFactory;
import com.unimas.txl.bean.user.ShiYongZheInfo;
import com.unimas.txl.service.user.UserService;

import java.io.Serializable;
import java.sql.Connection;

/**
 * 系统使用的shiro认证控制中心
 * 根据配置文件导向登录的验证和相应页面的身份认证都将转移到此处进行处理
 * 相当于拦截器的作用
 * 各url认证情况说明:
 * <pre>
 * /favicon.ico = anon 支持匿名访问
 * /install =anon  支持匿名访问
 * /login = authc 标准认证 处理登录请求
 * /logout = logout  处理注销请求
 * /assets/** = anon 静态资源可以匿名访问
 * /**  ＝ user 其它请求则都需要进行身份认证
 * </pre>
 *
 * @author 李涛(lt)
 *         Date: 12-8-27
 *         Time: 上午10:42
 *         todo 需要考虑惟一IP抢占登录
 *         todo 详细说明 密码的加密过程 供以后密码丢失的情况下的处理
 */

public class AuthRealm extends AuthorizingRealm {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthRealm.class);
    
    /**
     * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo info = null;
        ShiroUser shiroUser = (ShiroUser) principalCollection.getPrimaryPrincipal();
        info = new SimpleAuthorizationInfo();
    	info.addRole(shiroUser.getRole());
    	/*info.addStringPermission("user:*");
    	info.addStringPermission("query:view");*/
        return info;
    }


    /**
     * 认证回调函数, 登录时调用.     
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        SimpleAuthenticationInfo simpleAuthenticationInfo = null;
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        String password = new String(token.getPassword());
        Connection conn = null;
        try {
        	conn = DBFactory.getConn();
        	UserService service = new UserService();
        	String userNo = service.verify(conn, username, password);
        	System.out.println("lus ==userNo===== "+userNo);
        	if(userNo.startsWith("C")){
        		
        		String realName = null;
            	String role = "syz"; //使用者
            	int userId = -1;
            	ShiYongZheInfo info = null;
            	info = service.getShiYongZheByUserNo(conn,userNo);
            	userId = info.getId();
            	realName = info.getName();
    			ShiroUser user = new ShiroUser(userId, userNo, username, realName, role);
    			user.setInfo(info);
    			simpleAuthenticationInfo = new SimpleAuthenticationInfo(user, password, getName());
        	}
        } catch (AuthenticationException e) {
            LOGGER.error(e.getMessage(),e);
            throw e;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(),e);
            throw new AuthenticationException(e);
        } finally {
			DBFactory.close(conn, null, null);
		}

        return simpleAuthenticationInfo;
    }


    /**
     * Hash密码对
     */
    public static class HashPassword {
        public String salt;
        public String password;
    }

    /**
     * 用户认证对象 存储在session中的对象
     * 不使用User对象是为了过滤不需要存储的信息
     *
     */
    public static class ShiroUser implements Serializable {
        public static final long serialVersionUID = -4235825356126500405L;
        private int userId;
        private String userNo;
        private String role;
        private String loginName;
        private String realName;
        private String txImg;
        private Object info;

        public ShiroUser(int userId, String userNo, String loginName, String realName, String role) {
            this.loginName = loginName;
            this.realName = realName;
            this.userNo = userNo;
            this.role = role;
            this.userId = userId;
        }

        public String getLoginName() {
            return loginName;
        }
        public void setLoginName(String loginName) {
            this.loginName = loginName;
        }
        public String getRealName() {
            return realName;
        }
        public void setRealName(String realName) {
            this.realName = realName;
        }
        public String getUserNo() {
			return userNo;
		}
		public void setUserNo(String userNo) {
			this.userNo = userNo;
		}
		public int getUserId() {
			return userId;
		}
		public void setUserId(int userId) {
			this.userId = userId;
		}
		public String getRole() {
			return role;
		}
		public void setRole(String role) {
			this.role = role;
		}
		public Object getInfo() {
			return info;
		}
		public void setInfo(Object info) {
			this.info = info;
		}

		public static long getSerialversionuid() {
			return serialVersionUID;
		}

		@Override
        public String toString() {
            return loginName;
        }

		public String getTxImg() {
			return txImg;
		}

		public void setTxImg(String txImg) {
			this.txImg = txImg;
		}

    }

}
