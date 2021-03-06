package com.reg;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

public class nioserver {

	public static void main(String[] args) throws Exception {
		bindtimeservice();
	}
	
	private static void bindtimeservice(){
		ChannelFactory factory =new NioServerSocketChannelFactory (Executors.newCachedThreadPool(),Executors.newCachedThreadPool());
		ServerBootstrap bootstrap = new ServerBootstrap (factory);
		//TimeServerHandler handler = new TimeServerHandler();
		//ChannelPipeline pipeline = bootstrap.getPipeline();
		//pipeline.addLast("handler", handler);
		bootstrap.setPipelineFactory(new TimeClientPipelineFactory());
		bootstrap.setOption("child.tcpNoDelay", true);
		bootstrap.setOption("child.keepAlive", true);
		bootstrap.bind(new InetSocketAddress(9000));
	}
	
	private static void binddiscard(){
		ChannelFactory factory =new NioServerSocketChannelFactory (Executors.newCachedThreadPool(),Executors.newCachedThreadPool());
		ServerBootstrap bootstrap = new ServerBootstrap (factory);
		DiscardServerHandler handler = new DiscardServerHandler();
		ChannelPipeline pipeline = bootstrap.getPipeline();
		pipeline.addLast("handler", handler);
		bootstrap.setOption("child.tcpNoDelay", true);
		bootstrap.setOption("child.keepAlive", true);
		bootstrap.bind(new InetSocketAddress(9000));
	}
	
}
