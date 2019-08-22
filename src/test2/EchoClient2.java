package test2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class EchoClient2 {

	private static String SERVER_IP = "192.168.56.1";
	private static int SERVER_PORT = 8000;

	public static void main(String[] args) {
		Socket socket = null;

			try {
				// 1. �냼耳볦깮�꽦
				socket = new Socket();

				// 2. �꽌踰꾩뿰寃�
				InetSocketAddress inetSocketAddress = new InetSocketAddress(SERVER_IP, SERVER_PORT);
				socket.connect(inetSocketAddress);
				//System.out.println("[TCPClient] connected");

				// 3. IOStream 諛쏆븘�삤湲�
				InputStream is = socket.getInputStream();
				OutputStream os = socket.getOutputStream();

				// 4. �벐湲�
				//String data = "�븞�뀞�븯�꽭�슂\n";
				
				while (true) {
					
					Scanner sc = new Scanner(System.in);
					System.out.print(">>");
					String inputData = sc.next();
	
					if (inputData.equals("exit")) {
						break;
					}
					os.write(inputData.getBytes("UTF-8"));
	
				
					// 5. �씫湲�
					byte[] buffer = new byte[256];
					int readByteCount = is.read(buffer); // Blocking
					if (readByteCount == -1) { //client가 종료되면 -1이 발생되니까 정상 종료가 가능
						System.out.println("[TCPClient] closed by client");
						return;
					}
	
					inputData = new String(buffer, 0, readByteCount, "UTF-8");
					System.out.println("<<" + inputData);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (socket != null && socket.isClosed() == false) {
						socket.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

