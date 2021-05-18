package org.example;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 远程通信协议服务端代理对象
 */
public class RpcProxyServer {

    private static final ExecutorService executor = Executors.newCachedThreadPool();


    /**
     * 发布服务方法
     * @param service
     * @param port
     */
    public void publisher(Object service,int port){
        ServerSocket socket = null;
        try {
            socket = new ServerSocket(port);
            while (true){
                Socket accept = socket.accept();
                //使用线程池处理IO操作
                executor.execute(new ProcessHandler(accept,service));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
