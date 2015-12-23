import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;


public class ServerControlThread extends Thread{
	private String znak=null;
	Socket socketControl=null;
	ServerSocket serverSocketForFileTransfer = null;
	BufferedReader inputToClient = null;
	PrintStream outputToClient = null;
	
	
	
	
	/**
	 * Construtor for ServerControlThread
	 * @param socketControl
	 * @param serverSocketForFileTransfer
	 */
	public ServerControlThread(Socket socketControl,ServerSocket serverSocketForFileTransfer) {
		super();
		this.socketControl = socketControl;
		this.serverSocketForFileTransfer = serverSocketForFileTransfer;
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
					System.out.println("Request For Calculation");
					outputToClient.println("approved");
					Socket newSocket=serverSocketForFileTransfer.accept();
					new ServerFileTransferThread(newSocket).start();
				}
				
			}
		} catch (IOException e) {
			//e.printStackTrace();
			System.out.println("Lost connection with Client.");
			ServerCalculator.serverControlSocket.remove(this);
		}
		
	}

}
