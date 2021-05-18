package org.example;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * IO操作类，及代理执行方法
 */
public class ProcessHandler implements Runnable {

    private Socket socket;

    private Object service;

    public ProcessHandler(Socket socket,Object service) {
        this.socket = socket;
        this.service = service;
    }

    @Override
    public void run() {
        ObjectOutputStream outputStream = null;
        ObjectInputStream inputStream = null;

        try {
            //接收客户端的传输协议对象
            inputStream = new ObjectInputStream(socket.getInputStream());
            //反序列化
            RpcRequest o = (RpcRequest) inputStream.readObject();
            //代理执行
            Object invoke = invoke(o);
            System.out.println("服务端的执行结果："+invoke);
            //序列化结果并返回客户端
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(invoke);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }finally {
            //关闭流
            if (null!=inputStream){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null!=outputStream){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 代理执行
     * @param request
     * @return
     */
    public Object invoke(RpcRequest request) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //创建待执行的类对象
        Class<?> aClass = Class.forName(request.getClassName());
        //获取待执行的目标方法
        Method method = aClass.getMethod(request.getMethodName(), request.getTypes());
        //返回执行结果
        return method.invoke(this.service,request.getArgs());


    }
}
