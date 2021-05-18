package org.example;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args )
    {

        RpcProxyClient rpcProxyClient = new RpcProxyClient();
        IOrderService orderService = rpcProxyClient.clientProxy(IOrderService.class, "localhost", 8080);
        String s = orderService.queryList();

        System.out.println( s );
    }
}
