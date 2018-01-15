package com.unimas.web.http;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class StatusExposingServletResponse extends HttpServletResponseWrapper {  
	  
    private int httpStatus;
    private boolean isError;
    private String errorMsg;
  
    public StatusExposingServletResponse(HttpServletResponse response) {  
        super(response);  
    }  
  
    @Override  
    public void sendError(int sc) throws IOException {  
        httpStatus = sc;  
        this.httpStatus = sc;
        this.isError = true;
        super.setStatus(200);
        //super.sendError(sc);
    }  
  
    @Override  
    public void sendError(int sc, String msg) throws IOException {  
    	this.httpStatus = sc;
        this.isError = true;
        this.errorMsg = msg;
        super.setStatus(200);
        //super.sendError(sc, msg); 
    }  
  
    @Override  
    public void setStatus(int sc) {  
        httpStatus = sc;  
        super.setStatus(sc);  
    }  
  
    public int getStatus() {  
        return httpStatus;  
    }

	public boolean isError() {
		return isError;
	}

	public String getErrorMsg() {
		return errorMsg;
	}  
  
}
