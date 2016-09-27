package com.bit2016.network.echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
	private final static int PORT = 5500;
	
	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		
		try
		{
			//1. 소켓생성
			serverSocket = new ServerSocket();
		
			//2. 바인딩 ( 소켓에 소켓주소 연결 ( IP + PORT )
				// getLocalHost() ip주소 반환
			InetAddress inetAddress = InetAddress.getLocalHost();
				// getHostAddress() - 호스트의 IP 주소를 점으로 구분되는 10진수 형태로 반환
			String hostAddress = inetAddress.getHostAddress();
			serverSocket.bind( new InetSocketAddress(hostAddress, PORT) );
			System.out.println( "[server] binding" + hostAddress + ":" + PORT);
			
			//3. accept ( 클라이언트로부터 연결요청 대기 )
			Socket clientSocket = serverSocket.accept();
				// getRemoteSocketAddress() - 연결된 시스템에 대한 주소를 반환
			InetSocketAddress inetSocketAddress = (InetSocketAddress) clientSocket.getRemoteSocketAddress();
			InetAddress inetRemoteHostAddress = inetSocketAddress.getAddress();
			String remoteHostAddress = inetRemoteHostAddress.getHostAddress();
			int remoteHostPort = inetSocketAddress.getPort();
			
			System.out.println( "[server] connected by client [" + remoteHostAddress + ":" + remoteHostPort + "]" );
			
			//4. IOStream 받아오기
			InputStream inputStream = clientSocket.getInputStream();
			OutputStream outputStream = clientSocket.getOutputStream();
			
			BufferedReader br = new BufferedReader( new InputStreamReader( clientSocket.getInputStream(), "UTF-8" ) );
			PrintWriter pw = new PrintWriter( new OutputStreamWriter( clientSocket.getOutputStream(), "UTF-8"), true );
			
			while( true )
			{
				//5. 데이터 읽기
				String data = br.readLine();
				if( data == null )
				{
					System.out.println( "[server] closed by client" );
					break;
				}
				System.out.println( "[server] received: " + data );
				//6. 쓰기
				pw.println( data );
			}
		
		//7. 자원정리
		}
		catch ( ConnectException ex	)
		{
			ex.printStackTrace();
		}
		catch ( IOException ex )
		{
			ex.printStackTrace();
		}
		finally
		{
			try
			{
				if( serverSocket != null && serverSocket.isClosed() == false )
				{
					serverSocket.close();
				}
			}
			catch ( IOException ex )
			{
				ex.printStackTrace();
			}
		}
		
	}
}
