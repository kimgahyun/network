package com.bit2016.network.echo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class EchoClient {
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
			Scanner sc = new Scanner(System.in);
			String data = null;
			while(true)
			{
				System.out.print(">>");
				data = sc.nextLine() + "\n";

				if( data.equals("exit\n"))
				{
					break;
				}
				
				os.write( data.getBytes("UTF-8") );
				System.out.print( "<<" + data );
			}
			sc.close();
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
