package org.example;

import java.io.Serializable;

/**
 * 远程协议传输对象
 * @Author zy
 * @Date 2020/06/20 00:37 AM
 */
public class RpcRequest implements Serializable {

    private String className;
    private String methodName;
    private Object[] args;
    private Class[] types;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public Class[] getTypes() {
        return types;
    }

    public void setTypes(Class[] types) {
        this.types = types;
    }
}
