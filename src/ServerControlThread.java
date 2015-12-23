import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;


public class ServerControlThread implements Runnable{
	private String znak=null;
	Socket socketControl=null;
	ServerSocket serverSocketForFileTransfer = null;
	BufferedReader inputToClient = null;
	PrintStream outputToClient = null;
	
	
	
	

	public ServerControlThread(Socket socketControl,ServerSocket serverSocketForFileTransfer) {
		super();
		this.socketControl = socketControl;
		this.serverSocketForFileTransfer = serverSocketForFileTransfer;
	}

	public static void main(String[] args) {
		
	}

	public void run() {
		try {
			inputToClient = new BufferedReader(new InputStreamReader(socketControl.getInputStream()));
			outputToClient = new PrintStream(socketControl.getOutputStream());
			outputToClient.println("Dobrodosli!");
			while(true){
				String response=inputToClient.readLine();
				if(response.contains("exit")){
					outputToClient.println("Dovidjenja");
					System.out.println("Klijent je izasao!");
					inputToClient.close();
					outputToClient.close();
					socketControl.close();
					ServerCalculator.serverControlSocket.remove(this);
					return;
				}
				if(response.contains("need_to_calculate")){
					System.out.println("Hoce da izvrsi racunsku operaciju");
					Socket newSocket=serverSocketForFileTransfer.accept();
				}
				
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Lost connection");
		}
		
	}

}
