package com.lixiang.netty.protocoltcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @Description //TODO
 * @Author 李项
 * @Date 2020/2/22
 * @Version 1.0
 */
public class MyClientHandler extends SimpleChannelInboundHandler<MessageProtocol> {
    private  int count;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
        int len=msg.getLen();
        byte[] cotent = msg.getCotent();
        System.out.println("客户端收到消息如下");
        System.out.println("长度="+len);
        System.out.println("内容="+new String(cotent,CharsetUtil.UTF_8));
        System.out.println("客户端接收到的次数"+(++count));
    }

    //使用客户端发送10条数据
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
       for (int i = 0; i < 5; i++) {
           String mesage="今天天气冷，吃火锅";
           byte [] count=mesage.getBytes(CharsetUtil.UTF_8);
           int length = mesage.getBytes(CharsetUtil.UTF_8).length;
           //创建协议包对象
           MessageProtocol messageProtocol = new MessageProtocol();
           messageProtocol.setLen(length);
           messageProtocol.setCotent(count);
           ctx.writeAndFlush(messageProtocol);
       }
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("异常消息"+cause.getMessage());
        ctx.close();
    }
}
