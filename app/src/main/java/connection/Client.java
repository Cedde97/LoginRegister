package connection;
import model.*;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

	protected static final int portNr = 8080;
	Serialization serialization = new Serialization();
	Socket socket = null;


	/**
	 * Methode, um eine File/ein Image als ByteArray zu senden
	 *
	 * @param socket 						= das Socket, auf dem Uebertragen wird
	 * @param file							= die File/ das Image zum Senden
	 * @uses imageSerializer				= Hilfsmethode, um eine File/ein Image als ByteArray wiederzugeben
	 * @uses sendByteArray 					= Hilfsmethode, zum Senden eines ByteArrays
	 * @throws UnknownHostException			= unbekannter Host
	 * @throws IOException					= Fehler beim Input/Output
	 */

	public void sendImageAsByteArray(Socket socket, File file){

		this.socket = socket;

		try {

			byte[] message = serialization.fillImageByteArray(file);

			sendByteArray(socket, message);

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}



	/**
	 * Methode zum Senden eines ByteArrays
	 *
	 * @param socket 	= das Socket, auf dem Uebertragen wird
	 * @param buffer	= das zu uebertragende ByteArray
	 */

	public void sendByteArray (Socket socket, byte[] buffer)throws IOException, UnknownHostException{

		byte[] message = buffer;
		this.socket    = socket;

		try{
			DataOutputStream dOut = new DataOutputStream(socket.getOutputStream());

			dOut.writeInt(message.length);
			dOut.write(message);

		}catch (IOException e){
			e.printStackTrace();
		}finally{
		}
		try{
			if(socket != null){
				socket.close();
			}
		} catch (IOException ex){
			ex.printStackTrace();
		}
	}




	/**
	 * Methode, um ein Knoten/Node Objekt als ByteArray zu senden
	 *		 = IP Adresse des Empfaengers
	 * @param node						= der zu uebertragende Knoten/Node
	 * @throws UnknownHostException		= Unknown Host
	 * @throws IOException				= Fehler beim Input/Output
	 */

	public void sendNodeAsByteArray(Socket socket, Node node) throws UnknownHostException, IOException{

		this.socket = socket;

		byte[] buffer = serialization.fillNodeByteArray(node);

		sendByteArray(socket, buffer);
	}



	public void sendRoutHelperAsByteArray(Socket socket, RoutHelper routhelper) throws UnknownHostException, IOException{

		this.socket = socket;

		byte[] buffer = serialization.fillRoutHelperByteArray(routhelper);

		sendByteArray(socket, buffer);
	}



	public void sendNeighbourAsByteArray(Socket socket, Neighbour neighbour) throws UnknownHostException, IOException{

		this.socket = socket;

		byte[] buffer = serialization.fillNeighbourByteArray(neighbour);

		sendByteArray(socket, buffer);
	}



	public void sendPeerMemoAsByteArray(Socket socket, PeerMemo peerMemo) throws UnknownHostException, IOException{

		this.socket = socket;

		byte[] buffer = serialization.fillPeerMemoByteArray(peerMemo);

		sendByteArray(socket, buffer);
	}



	public void sendForeignDataAsByteArray(Socket socket, ForeignData foreignData) throws UnknownHostException, IOException{

		this.socket = socket;

		byte[] buffer = serialization.fillForeignDataByteArray(foreignData);

		sendByteArray(socket, buffer);
	}
}
