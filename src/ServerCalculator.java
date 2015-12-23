import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;


public class ServerCalculator {
	
	static LinkedList<ServerControlThread> serverControlSocket = new LinkedList<ServerControlThread>();

	public static void main(String[] args) {
		try {
			ServerSocket serverSocketForControl = new ServerSocket(1908);
			ServerSocket serverSocketForFileTransfer = new ServerSocket(13413);
			
			while(true){
				Socket newSocket=serverSocketForControl.accept();
				serverControlSocket.addFirst(new ServerControlThread(newSocket,serverSocketForFileTransfer));
				serverControlSocket.getFirst().run();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
