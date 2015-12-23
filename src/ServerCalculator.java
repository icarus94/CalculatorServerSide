import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;


public class ServerCalculator {
	/**
	 * List of Socket Threads listening on port 13413
	 */
	static LinkedList<ServerControlThread> serverControlSocket = new LinkedList<ServerControlThread>();

	public static void main(String[] args) {
		try {
			ServerSocket serverSocketForControl = new ServerSocket(13413);
			ServerSocket serverSocketForFileTransfer = new ServerSocket(1908);
			
			while(true){
				Socket newSocket=serverSocketForControl.accept();
				serverControlSocket.addFirst(new ServerControlThread(newSocket,serverSocketForFileTransfer));
				serverControlSocket.getFirst().start(); 
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Port is already in use!");
		}
		

	}

}
