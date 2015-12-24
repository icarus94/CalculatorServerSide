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
	LinkedList<Double> nizBrojeva = new LinkedList<Double>();
	BufferedReader inputToClient = null;
	PrintStream outputToClient = null;
	ObjectInputStream dataFromClient=null;
	String result;
	
	/**
	 * Constructor for Transfer Data
	 * @param socketFileTransfer
	 */
	public ServerFileTransferThread(Socket socketFileTransfer) {
		super();
		this.socketFileTransfer = socketFileTransfer;
	}


	synchronized public void run(){
		try {
			inputToClient = new BufferedReader(new InputStreamReader(socketFileTransfer.getInputStream()));
			outputToClient = new PrintStream(socketFileTransfer.getOutputStream());
			//requsts & anwsers
			outputToClient.println("operation");
			znak=inputToClient.readLine();
			outputToClient.println("numbers");
			String c;
			while(!((c=inputToClient.readLine()).contains("done"))){
				double b;
				try {
					b = Double.parseDouble(c);
				} catch (NumberFormatException e) {
					outputToClient.println("unet String kao broj");
					inputToClient.close();
					outputToClient.close();
					socketFileTransfer.close();
					return;
				}
				nizBrojeva.addFirst(b);
			}
			
			/*dataFromClient = new ObjectInputStream(socketFileTransfer.getInputStream());
			Object readObject = dataFromClient.readObject();
			dataFromClient.close();
			if(readObject instanceof LinkedList<?>)
				nizBrojeva = (LinkedList<Double>) readObject;
			*/
			
			if(znak.contains("+"))
				result=""+sabiranje(nizBrojeva);
			if(znak.contains("-"))
				result=""+oduzimanje(nizBrojeva);
			if(znak.contains("*"))
				result=""+mnozenje(nizBrojeva);
			if(znak.contains("/")){
				boolean isItDividedByZero=false;
				for (int i = 1; i < nizBrojeva.size(); i++) {
					if(nizBrojeva.get(i)==0){
						isItDividedByZero=true;
						break;
					}
				}if(!isItDividedByZero){
					result=""+deljenje(nizBrojeva);
				}else{
					result="ne mozes deliti 0";
				}
			}
			
			if(result==null)
				result="Operacija nije dobro uneta";
			if(nizBrojeva.isEmpty())
			 	result="Greska u listi brojeva"; //suvisan kod,ako ovo ne radi,ukazuje na problem sa klijentske strane,
													//gde se ne moze iz GUI-a ispraviti logicka greska
			outputToClient.println(result);
			/*while(true){
				if(inputToClient.readLine().contains("recived")){
					outputToClient.println("OK");
					break;
				}
			}*/
			inputToClient.close();
			outputToClient.close();
			socketFileTransfer.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error in transfering data!");
		}
	}
	private double sabiranje(LinkedList<Double> listaBrojeva){
		int i=0;
		if(listaBrojeva.isEmpty())
			return 0;
		double res=listaBrojeva.get(i);
		for (i = 1; i < listaBrojeva.size(); i++) {
			res=res+listaBrojeva.get(i);
		}
		return res;
	}
	private double oduzimanje(LinkedList<Double> listaBrojeva){
		int i=0;
		if(listaBrojeva.isEmpty())
			return 0;
		double res=listaBrojeva.get(i);
		for (i = 1; i < listaBrojeva.size(); i++) {
			res=res-listaBrojeva.get(i);
		}
		return res;
	}
	private double mnozenje(LinkedList<Double> listaBrojeva){
		int i=0;
		if(listaBrojeva.isEmpty())
			return 0;
		double res=listaBrojeva.get(i);
		for (i = 1; i < listaBrojeva.size(); i++) {
			res=res*listaBrojeva.get(i);
		}
		return res;
	}
	/**
	 * pazi na 0 kao unet broj!!!!
	 * @param listaBrojeva
	 * @return
	 */
	private double deljenje(LinkedList<Double> listaBrojeva){
		int i=0;
		if(listaBrojeva.isEmpty())
			return 0;
		double result=listaBrojeva.get(i);
		for (i = 1; i < listaBrojeva.size(); i++) {
			if(listaBrojeva.get(i)==0)
				return 0;
			result=result/listaBrojeva.get(i);
		}
		return result;
	}
}
