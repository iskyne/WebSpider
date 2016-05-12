package test.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

public class FileChannelTest {
	public static void main(String args[]){
		Charset charset=Charset.forName("GBK");
		FileInputStream input=null;
		FileChannel channel=null;
		ByteBuffer buffer=ByteBuffer.allocate(2048);
		StringBuffer content=new StringBuffer();
		try {
			input=new FileInputStream(new File("./content/file1"));
			channel=input.getChannel();
			

			while(channel.position()<channel.size()){
				channel.read(buffer);
				buffer.flip();
				content.append(charset.decode(buffer));
				buffer.flip();
			}
		
		System.out.println(content.toString());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(IOException e){
			e.printStackTrace();
		}
	}
}
