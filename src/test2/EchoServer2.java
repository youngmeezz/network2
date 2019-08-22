package test2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class EchoServer2 {
	private static final int PORT = 8000;
	
	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		
		try {
			//1. �꽌踰꾩냼耳� �깮�꽦
			serverSocket = new ServerSocket();
			
			//2. Binding:
			//   Socket�뿉 SocketAddress(IPAddress + Port)
			//   諛붿씤�뵫�븳�떎.
			InetAddress inetAddress = InetAddress.getLocalHost();
			InetSocketAddress inetSocketAddress = new InetSocketAddress(inetAddress, PORT);
			serverSocket.bind(inetSocketAddress);
			System.out.println("[TCPServer] binding " + inetAddress.getHostAddress() + ":" + PORT);
			
			//3. accept:
			//   �겢�씪�씠�뼵�듃濡� 遺��꽣 �뿰寃곗슂泥�(Connect)�쓣 湲곕떎由곕떎.
			Socket socket = serverSocket.accept(); // Blocking
			InetSocketAddress inetRemoteSocketAddress = 
					(InetSocketAddress)socket.getRemoteSocketAddress();
			
			String remoteHostAdress = inetRemoteSocketAddress.getAddress().getHostAddress();
			int remoteHostPort = inetRemoteSocketAddress.getPort();
			
			System.out.println("[TCPServer] connected from client[" + 
				remoteHostAdress + ":" + 
				remoteHostPort + "]");
			
			try {
				//4. IOStream 諛쏆븘�삤湲�
				InputStream is = socket.getInputStream();
				OutputStream os = socket.getOutputStream();
				
				while(true) {
					
					//5. �뜲�씠�꽣 �씫湲�
					byte[] buffer = new byte[256];
					int readByteCount = is.read(buffer); //Blocking
					if(readByteCount == -1) {
						// �젙�긽醫낅즺: remote socket�씠 close()
						//         硫붿냼�뱶瑜� �넻�빐�꽌 �젙�긽�쟻�쑝濡� �냼耳볦쓣 �떕�� 寃쎌슦
						System.out.println("[TCPServer] closed by client");
						break;
					}
					
					String data = new String(buffer, 0, readByteCount, "UTF-8"); //디코딩(알아들을 수 있는 말로)(byte를 String으로)
					System.out.println("[TCPServer] received:" + data);
				
					//6. �뜲�씠�꽣 �벐湲�
					os.write(data.getBytes("UTF-8"));	//인코딩 (String을 byte로)
				}
				
			} catch(SocketException e) {
				System.out.println("[TCPServer] abnormal closed by client");
			} catch(IOException e) {
				e.printStackTrace();
			} finally {
				//7. Socket �옄�썝�젙由�
				if(socket != null && socket.isClosed() == false) {
					socket.close();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			//8. Server Socket �옄�썝�젙由�
			try {
				if(serverSocket != null && serverSocket.isClosed() == false) {
					serverSocket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}