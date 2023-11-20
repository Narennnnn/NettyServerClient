package com.nettyIoChatApp;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundMessageHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class ChatServerHandler extends ChannelInboundMessageHandlerAdapter<String> {
    private  static  final  ChannelGroup channels =
            new DefaultChannelGroup (GlobalEventExecutor.INSTANCE);
    @Override
    public void messageReceived(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        Channel incoming=channelHandlerContext.channel();
        for(Channel channel:channels){
            if(channel!=incoming){
                channel.write("["+incoming.remoteAddress()+"]"+s+"\n");
            }
        }
        System.out.println(s);

    }
    @Override
    public void handlerAdded(ChannelHandlerContext ctx)throws  Exception{//when new client added
        Channel incoming=ctx.channel();
        for(Channel channel:channels){
            channel.write("[SERVER]-"+incoming.remoteAddress()+" has joined\n");
        }
        channels.add(incoming);
    }
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx)throws  Exception{
        Channel incoming=ctx.channel();
        for(Channel channel:channels){
            channel.write("[SERVER]-"+incoming.remoteAddress()+" has left\n");
        }
        channels.remove(incoming);
    }
    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext channelHandlerContext) throws Exception {

    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext channelHandlerContext) throws Exception {

    }
}
