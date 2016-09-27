package com.bit2016.network.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class TCPClient {
	private static final String SERVER_IP = "192.168.1.20";
	private static final int SERVER_PORT = 5500;
	
	public static void main(String[] args) {
		Socket socket = null;
		try
		{
			//1. socket 생성
			socket = new Socket();
			
			//2. 서버연결
			socket.connect( new InetSocketAddress( SERVER_IP, SERVER_PORT) );
			System.out.println( "[client] connected" );
			
			//3. IOStream 받아오기
			InputStream is = socket.getInputStream();
			OutputStream os = socket.getOutputStream();
			
			//4. 쓰기
			String data = "Hello World\n";
			os.write( data.getBytes("UTF-8") );
			
			//5. 읽기
			byte[] buffer = new byte[256];
			int readByteCount = is.read( buffer );
			if( readByteCount == -1 )
			{
				System.out.println( "[Client] closed by server" );
				return;
			}
			
			data = new String( buffer, 0, readByteCount, "UTF-8" );
			System.out.print( "[Client] received: " + data );
				
		}
		catch ( IOException ex )
		{
			ex.printStackTrace();
		}
		finally
		{
			try
			{
				if( socket != null && socket.isClosed() == false )
				socket.close();
			}
			catch( IOException ex )
	
			{
				ex.printStackTrace();
			}
		}
		
	}
}
