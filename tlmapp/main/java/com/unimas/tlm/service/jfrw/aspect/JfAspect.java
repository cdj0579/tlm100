package com.unimas.tlm.service.jfrw.aspect;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.unimas.tlm.service.jfrw.aspect.annotations.RwPointcut;

@Aspect
@Component
public class JfAspect {
	
    /**
     * 定义切点： 注解方式<br/>  添加了此注解的方法，将被拦截
     */
    @Pointcut("@annotation(com.unimas.tlm.service.jfrw.aspect.annotations.JfPointcut)")
     public  void jfAspect() {    
    }  
    
    /**
     * 定义切点： 表达式方式<br/>  利用正则表达式定义需求被拦截的方法或类 <br/> 括号中各个pattern分别表示:<br/>
     * 修饰符匹配（modifier-pattern?）<br/>
     * 返回值匹配（ret -type-pattern）<br/>
     * 类路径匹配（declaring -type-pattern?）<br/>
     * 方法名匹配（name-pattern）<br/>
     * 参数匹配（(param -pattern)）<br/>
     * 异常类型匹配（throws-pattern?）<br/>
     * 其中后面跟着“?”的是可选项。
     */
    /*@Pointcut("execution(* com.unimas.las.service..*.*(..))")
    public void executionPointcut() { }*/
    
    /**  
     * 切点之前执行
     * @param joinPoint 切点  
     */    
    @Before("jfAspect()")
    public  void beforePointcut(JoinPoint joinPoint) {
    	RwPointcut pointcut = getAnnotation(joinPoint);
    	RwHandlerFactory.createContext(pointcut);
    }
    
    /**  
     * return之后执行  
     * @param joinPoint 切点  
     */    
    @AfterReturning(pointcut="jfAspect()", returning="retVal")
    public  void afterReturning(JoinPoint joinPoint, Object retVal) { 
    	RwHandlerFactory.save();
    }
    
    /**
     * 获取任务注解
     * @param joinPoint
     * @return
     */
    private RwPointcut getAnnotation(JoinPoint joinPoint){
    	Signature signature = joinPoint.getSignature();
    	MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if(method.isAnnotationPresent(RwPointcut.class)){
        	return method.getAnnotation(RwPointcut.class);
        } else {
        	return null;
        }
    }

}
