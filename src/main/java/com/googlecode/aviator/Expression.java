package com.googlecode.aviator;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Map;

import com.googlecode.aviator.exception.ExpressionRuntimeException;


/**
 * Compiled expression,encapsulate a java class generated by expression string
 * 
 * @author dennis
 * 
 */
public class Expression {
    private final Class<?> clazz;
    private final Method runMethod;


    public Expression(Class<?> clazz) throws NoSuchMethodException, SecurityException {
        super();
        this.clazz = clazz;
        this.runMethod = clazz.getDeclaredMethod("run", Map.class);
        this.runMethod.setAccessible(true);
    }


    /**
     * Execute expression with environment
     * 
     * @param env
     *            Binding variable environment
     * @return
     */
    public Object execute(Map<String, Object> env) {
        if (env == null) {
            env = Collections.emptyMap();
        }
        try {
            return this.runMethod.invoke(null, env);
        }
        catch (Throwable e) {
            throw new ExpressionRuntimeException("Execute expression error", e);
        }
    }


    /**
     * Execute expression with empty environment
     * 
     * @return
     */
    public Object execute() {
        return execute(null);
    }


    /**
     * Get generated java class
     * 
     * @return
     */
    public Class<?> getJavaClass() {
        return this.clazz;
    }
}
