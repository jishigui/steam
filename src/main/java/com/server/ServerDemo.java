package com.server;

import com.alipay.api.java_websocket.WebSocket;
import com.alipay.api.java_websocket.handshake.ClientHandshake;
import com.alipay.api.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author 99514925@qq.com
 * @Create 2020/2/3 23:03
 **/

public class ServerDemo extends WebSocketServer {


    public static Map<String,WebSocket> map=new HashMap<String,WebSocket>();


    public ServerDemo() throws UnknownHostException {
    }


    public ServerDemo(int port) throws UnknownHostException {
        super(new InetSocketAddress(port));
        System.out.println("websocket Server start at port:"+port);
    }


    /**
     * 触发连接事件
     */
    @Override
    public void onOpen(WebSocket conn, ClientHandshake clientHandshake) {
        System.out.println("new connection ===" + conn.getRemoteSocketAddress().getAddress().getHostAddress());
    }

    /**
     *
     * 连接断开时触发关闭事件
     */
    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {

    }

    /**
     * 客户端发送消息到服务器时触发事件
     */
    @Override
    public void onMessage(WebSocket conn, String message) {
        System.out.println("you have a new message: "+ message);
        map.put(message,conn);
        //向客户端发送消息
       // conn.send(message);
    }

    /**
     * 触发异常事件
     */
    @Override
    public void onError(WebSocket conn, Exception e) {
        //e.printStackTrace();
        if( conn != null ) {
            //some errors like port binding failed may not be assignable to a specific websocket
        }
    }

    @Override
    public void onStart() {

    }

    /**
     * 启动服务端
     * @param args
     * @throws UnknownHostException
     */
//    public static void main(String[] args) throws UnknownHostException {
//        new ServerDemo(8887).start();
//    }
}
