package com.bit2016.network.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class TCPServer {
	private static final int PORT = 5500;

	public static void main(String[] args) {

		ServerSocket serverSocket = null;
		try {
			// 1. 서버소켓
			serverSocket = new ServerSocket();

			// 2. binding( 소켓에 소켓주소 (IP + PORT)를 연결한다. )
			InetAddress inetAddress = InetAddress.getLocalHost();
			String hostAddress = inetAddress.getHostAddress();
			serverSocket.bind(new InetSocketAddress(hostAddress, PORT));
			System.out.println("[server] binding " + hostAddress + ":" + PORT);

			// 3. accept( 클라이언트로 부터 연결요청을 기다린다. )
			Socket socket = serverSocket.accept(); // block 된다. ( 정지 )
			InetSocketAddress inetSocketAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
			InetAddress inetRemoteHostAddress = inetSocketAddress.getAddress();
			String remoteHostAddress = inetRemoteHostAddress.getHostAddress();
			int remoteHostPort = inetSocketAddress.getPort();

			System.out.println("[server] connected by client [" + remoteHostAddress + ":" + remoteHostPort + "]");

			try {
				// 4. IOStream 받아오기
				InputStream inputStream = socket.getInputStream();
				OutputStream outputStream = socket.getOutputStream();

				while (true) {
					// 5. 데이터 읽기
					byte[] buffer = new byte[256];
					int readByteCount = inputStream.read(buffer);

					if (readByteCount == -1) {
						// 정상종료 ( remote socket이 close()를불러서 정상적으로 소켓닫음 )
						System.out.println("[server] closed by client");
						break;
					}
					String data = new String(buffer, 0, readByteCount, "UTF-8"); // byte->String 으로

					System.out.println("[server] received: " + data);

					// 6. 쓰기
					outputStream.write(data.getBytes("UTF-8"));
				}
			} catch (SocketException ex) {
				System.out.println("[server] abnormal closed by client");
			} catch (IOException ex) {
				ex.printStackTrace();
			} finally {
				try {
					// 7. 자원정리( 소켓 닫기 )
					socket.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}

			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (serverSocket != null && serverSocket.isClosed() == false) {
					serverSocket.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}

		}

	}
}
