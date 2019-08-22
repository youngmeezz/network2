package util2;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class NSLookup2 {

	public static void main(String[] args) {
		
		while(true) {
		Scanner sc = new Scanner(System.in);
		
		String domainName = sc.next();
		
		if(domainName.equals("exit")) {
			break;
		}
		try {
			InetAddress[] inetAddresses = InetAddress.getAllByName(domainName);
			
			for(InetAddress inetAddress : inetAddresses) {
				System.out.println(domainName + " : "+ inetAddress.getHostAddress());
			}
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
			}
		}
	}
}


