package com.reg;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

public class TimeServerHandler extends SimpleChannelHandler {
	
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
		Channel ch = e.getChannel();
		ChannelBuffer time = ChannelBuffers.buffer(4);
		time.writeLong(System.currentTimeMillis() / 1000);
		ChannelFuture f = ch.write(time);
		f.addListener(new ChannelFutureListener() {
			public void operationComplete(ChannelFuture future) {
				Channel ch = future.getChannel();
				ch.close();
			}
		});
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
		e.getCause().printStackTrace();
		Channel ch = e.getChannel();
		ch.close();
	}
	
}
