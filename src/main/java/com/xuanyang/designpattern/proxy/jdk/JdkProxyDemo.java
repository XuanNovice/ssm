package com.xuanyang.designpattern.proxy.jdk;

import com.xuanyang.designpattern.proxy.intercepter.Intercepter;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by Young on 2018/4/5.
 */
public class JdkProxyDemo implements InvocationHandler {

    /** 需要代理的目标对象*/
    private Object target = null;

    private Intercepter intercepter = null;

    /** 获取代理对象的方法*/
    public Object bind(Object o, Intercepter intercepter) {
        this.target = o;
        this.intercepter = intercepter;
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;
        if (intercepter == null){
            return method.invoke(target, args);
        }
        if (intercepter.before(proxy, target, method, args)) {
            result = method.invoke(target, args);
        } else {
            intercepter.around(proxy, target, method, args);
        }
        intercepter.after(proxy, target, method, args);
        return result;
    }
}
