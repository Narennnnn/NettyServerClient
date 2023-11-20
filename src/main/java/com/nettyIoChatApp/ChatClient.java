package com.nettyIoChatApp;

import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.bootstrap.Bootstrap;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ChatClient {
    public static void main(String args[]){
        new ChatClient("localhost",8080).run();
    }
    private final String host;
    private final int port;
    public ChatClient(String host,int port){
        this.host=host;
        this.port=port;
    }
    public void run(){
        EventLoopGroup group=new NioEventLoopGroup();
        try{
            Bootstrap bootstrap=new Bootstrap()
                    .group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChatClientIntializer());
            Channel channel=bootstrap.connect(host,port).sync().channel();
            BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
            while(true){
                channel.write(in.readLine()+"\r\n");
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        finally {
            group.shutdownGracefully();
        }
    }
}
