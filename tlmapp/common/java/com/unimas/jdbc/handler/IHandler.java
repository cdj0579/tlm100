package com.unimas.jdbc.handler;

import java.sql.ResultSet;

public interface IHandler<T> {
	
	public T handler(ResultSet rs) throws Exception;

}
