package com.bit2016.network.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class NSLookup {
	public static void main(String[] args) {
		
		String host = "www.naver.com";
		try {
			InetAddress[] inetAddresses = InetAddress.getAllByName( host );
			for( InetAddress inetAddress : inetAddresses )
			{
				System.out.println( inetAddress.getHostAddress() );
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
