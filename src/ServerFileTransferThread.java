import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.LinkedList;


public class ServerFileTransferThread extends Thread {
	Socket socketFileTransfer=null;
	String znak=null;
	LinkedList<Broj> nizBrojeva = new LinkedList<Broj>();
	BufferedReader inputToClient = null;
	PrintStream outputToClient = null;
	ObjectInputStream dataToClient=null;
	String result;
	
	
	public ServerFileTransferThread(Socket socketFileTransfer) {
		super();
		this.socketFileTransfer = socketFileTransfer;
	}


	public void run(){
		try {
			inputToClient = new BufferedReader(new InputStreamReader(socketFileTransfer.getInputStream()));
			outputToClient = new PrintStream(socketFileTransfer.getOutputStream());
			dataToClient = new ObjectInputStream(new BufferedInputStream(socketFileTransfer.getInputStream()));
			outputToClient.println("operation");
			znak=inputToClient.readLine();
			outputToClient.println("numbers");
			Object readObject = dataToClient.readObject();
			nizBrojeva= (LinkedList<Broj>) readObject;
			
			
			if(znak=="+")
				result=""+sabiranje(nizBrojeva);
			if(znak=="-")
				result=""+oduzimanje(nizBrojeva);
			if(znak=="*")
				result=""+mnozenje(nizBrojeva);
			if(znak=="/"){
				boolean a=true;
				for (int i = 1; i < nizBrojeva.size(); i++) {
					if(nizBrojeva.get(i).getBroj()==0){
						a=false;
					}
				}if(a){
					result=""+deljenje(nizBrojeva);
				}else{
					result="ne mozes deliti 0";
				}
			}
			while(true){
				outputToClient.println(result);
				if(inputToClient.readLine().contains("recived"))
					break;
			}
			socketFileTransfer.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Greska u prenosu podataka");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("neodg objekat");
		}
	}
	private double sabiranje(LinkedList<Broj> listaBrojeva){
		int i=0;
		if(listaBrojeva.isEmpty())
			return 0;
		double result=listaBrojeva.get(i).getBroj();
		for (i = 1; i < listaBrojeva.size(); i++) {
			result=result+listaBrojeva.get(i).getBroj();
		}
		return result;
	}
	private double oduzimanje(LinkedList<Broj> listaBrojeva){
		int i=0;
		if(listaBrojeva.isEmpty())
			return 0;
		double result=listaBrojeva.get(i).getBroj();
		for (i = 1; i < listaBrojeva.size(); i++) {
			result=result-listaBrojeva.get(i).getBroj();
		}
		return result;
	}
	private double mnozenje(LinkedList<Broj> listaBrojeva){
		int i=0;
		if(listaBrojeva.isEmpty())
			return 0;
		double result=listaBrojeva.get(i).getBroj();
		for (i = 1; i < listaBrojeva.size(); i++) {
			result=result*listaBrojeva.get(i).getBroj();
		}
		return result;
	}
	/**
	 * pazi na 0 kao unet broj!!!!
	 * @param listaBrojeva
	 * @return
	 */
	private double deljenje(LinkedList<Broj> listaBrojeva){
		int i=0;
		if(listaBrojeva.isEmpty())
			return 0;
		double result=listaBrojeva.get(i).getBroj();
		for (i = 1; i < listaBrojeva.size(); i++) {
			if(listaBrojeva.get(i).getBroj()==0)
				return 0;
			result=result/listaBrojeva.get(i).getBroj();
		}
		return result;
	}
}
