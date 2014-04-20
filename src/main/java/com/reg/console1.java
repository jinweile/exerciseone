package com.reg;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.zip.CRC32;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Event;
import com.dianping.cat.message.Transaction;

//import 

public class console1 {

	public static void print(){
		
		Charset s = Charset.forName("gbk");
		Set<String> ss = s.aliases();
		for(String name : ss){
			System.out.println(name);
		}
		
		Iterator<String> sss = ss.iterator();
		while(sss.hasNext()){
			System.out.println(sss.next());
		}
		
		Map<String,String> dd = new HashMap<String,String>();

	}
	
	public static void crc32() throws IOException{
		String filename = "G:\\soft\\mysql-connector-net-5.0.7-noinstall.zip";
		CRC32 crc = new CRC32();
		long start = (new Date()).getTime();
		
		//普通输入流
		InputStream in = new FileInputStream(filename);
		int c;
		while((c=in.read()) != -1){
			crc.update(c);
		}
		System.out.println(crc.getValue());
		
		long one = (new Date()).getTime();
		System.out.println("普通流耗时:" + (one-start));
		
		//nio
		FileChannel channel = ((FileInputStream)in).getChannel();
		long length = channel.size();
		MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, length);
		for(int p = 0; p < length; p++){
			c = buffer.get(p);
			crc.update(c);
		}
		System.out.println(crc.getValue());
		
		long two = (new Date()).getTime();
		System.out.println("nio耗时:" + (two-one));
		
		//随机访问文件
		RandomAccessFile file = new RandomAccessFile(filename,"r");
		length = file.length();
		for(long p = 0; p < length; p++){
			file.seek(p);
			c = file.readByte();
			crc.update(c);
		}
		System.out.println(crc.getValue());
		
		long three = (new Date()).getTime();
		System.out.println("随机访问文件耗时:" + (three-two));
		
		//缓存流
		in = new BufferedInputStream(new FileInputStream(filename));
		while((c = in.read()) != -1){
			crc.update(c);
		}
		System.out.println(crc.getValue());
		
		long four = (new Date()).getTime();
		System.out.println("缓存流耗时:" + (four-three));
	}
	
	public static void cattest() throws Exception{
		//Cat.initialize(new File("file:/E:/data/appdatas/cat/client.xml"));
		String pageName = "java test cat demo";
		String serverIp = "192.168.1.3";
		double amount = 0;
		Transaction t = Cat.newTransaction("Java", pageName);
		try{
			for(int i = 0; i < 100; i++){
				if(i % 30 != 0){
					Transaction t1 = Cat.newTransaction("JavaError", pageName);
					Cat.logEvent("Call", "localhost", Event.SUCCESS, "ip="+serverIp+"/smsservice/findall");
					Cat.logMetricForCount("PayCount");
					Cat.logMetricForSum("PayAmount", amount);
					t1.setStatus(Transaction.SUCCESS);
					t1.complete();
				}else{
					Transaction t1 = Cat.newTransaction("JavaError", pageName);
					Thread.sleep(1000);
					Cat.logError(new Exception("java test error_" + i));
					t1.setStatus(new Exception("java test error_" + i));
					t1.complete();
				}
			}
		}catch(Exception e){
			t.setStatus(e);
		}finally{
			t.complete();
		}
		Cat.destroy();
		//Thread.sleep(30000);
	}
	
	public static void main(String[] args) throws Exception {
		cattest();
	}
	
}
