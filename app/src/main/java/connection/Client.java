package connection;

import connection.RoutHelper;
import model.*;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;

/**
 * Klasse Client, die diverse SendeMethoden fuer die benoetigten Objekte bietet.
 * 
 * @author Cedric
 *
 */

public class Client implements java.io.Serializable {

	private static final long serialVersionUID = 2677455955263183331L;
	protected static final int portNr = 9797;
	Serialization serialization = new Serialization();
	Socket socket = null;
	

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
			if(socket != null){
				socket.close();
			}
		}
	}

	
	
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
	 * Methode, um ein Knoten/Node Objekt als ByteArray zu senden
	 * 
	 * @param socket  					= das Socket, auf dem uebertragen wird	 
	 * @param node						= der zu uebertragende Knoten/Node
	 * @throws UnknownHostException		= Unknown Host
	 * @throws IOException				= Fehler beim Input/Output
	 */

	public void sendNodeAsByteArray(Socket socket, Node node) throws UnknownHostException, IOException{

		this.socket = socket;

		byte[] buffer = serialization.fillNodeByteArray(node);

		sendByteArray(socket, buffer);
	}

	

	/**
	 * Methode, um eine PeerMemo-Liste als ByteArray zu senden
	 * 
	 * @param socket  					= das Socket, auf dem uebertragen wird
	 * @param list						= die Liste, die gesendet wird
	 * @throws UnknownHostException     = Unknown Host
	 * @throws IOException				= Fehler beim Input/Output
	 */
	
	public void sendPeerMemoListAsByteArray(Socket socket, ArrayList<PeerMemo> list) throws UnknownHostException, IOException{

		this.socket = socket;

		byte[] buffer = serialization.fillPeerMemoListByteArray(list);

		sendByteArray(socket, buffer);
	}
	
	
	
	/**
	 * Methode, um eine Neighbour-Liste als ByteArray zu senden
	 * 
	 * @param socket  					= das Socket, auf dem uebertragen wird
	 * @param list						= die Liste, die gesendet wird
	 * @throws UnknownHostException     = Unknown Host
	 * @throws IOException				= Fehler beim Input/Output
	 */

	public void sendNeighbourListAsByteArray(Socket socket, ArrayList<Neighbour> list) throws UnknownHostException, IOException{

		this.socket = socket;

		byte[] buffer = serialization.fillNeighbourListByteArray(list);

		sendByteArray(socket, buffer);
	}

	
	
	/**
	 * Methode, um ein RoutHelper-Objekt als ByteArray zu senden
	 * 
	 * @param socket  					= das Socket, auf dem uebertragen wird
	 * @param routhelper				= der RoutHelper, der uebertragen wird
	 * @throws UnknownHostException     = Unknown Host
	 * @throws IOException				= Fehler beim Input/Output
	 */
	
	public void sendRoutHelperAsByteArray(Socket socket, RoutHelper routhelper) throws UnknownHostException, IOException{

		this.socket = socket;

		byte[] buffer = serialization.fillRoutHelperByteArray(routhelper);
		sendByteArray(socket, buffer);
	}



	/**
	 * Methode, um ein RoutHelper-Objekt als ByteArray zu senden
	 *
	 * @param socket  					= das Socket, auf dem uebertragen wird
	 * @param routhelper				= der RoutHelper, der uebertragen wird
	 * @throws UnknownHostException     = Unknown Host
	 * @throws IOException				= Fehler beim Input/Output
	 */

	public void sendRoutHelperPicAsByteArray(Socket socket, RoutHelper routhelper) throws UnknownHostException, IOException{

		this.socket = socket;

		byte[] buffer = serialization.fillRoutHelperPicByteArray(routhelper);
		sendByteArray(socket, buffer);
	}

	

	/**
	 * Methode, um ein Neighbour-Objekt als ByteArray zu senden
	 * 
	 * @param socket  					= das Socket, auf dem uebertragen wird
	 * @param neighbour					= der Neighbour, der uebertragen wird
	 * @throws UnknownHostException     = Unknown Host
	 * @throws IOException				= Fehler beim Input/Output
	 */
	
	public void sendNeighbourAsByteArray(Socket socket, Neighbour neighbour) throws UnknownHostException, IOException{

		this.socket = socket;

		byte[] buffer = serialization.fillNeighbourByteArray(neighbour);

		sendByteArray(socket, buffer);
	}
	
	

	/**
	 * Methode, um ein PeerMemo-Objekt als ByteArray zu senden
	 * 
	 * @param socket  					= das Socket, auf dem uebertragen wird
	 * @param peerMemo				= das PeerMemo, das uebertragen wird
	 * @throws UnknownHostException     = Unknown Host
	 * @throws IOException				= Fehler beim Input/Output
	 */

	public void sendPeerMemoAsByteArray(Socket socket, PeerMemo peerMemo) throws UnknownHostException, IOException{

		this.socket = socket;

		byte[] buffer = serialization.fillPeerMemoByteArray(peerMemo);

		sendByteArray(socket, buffer);
	}

	
	
	/**
	 * Methode, um ein ForeignData-Objekt als ByteArray zu senden
	 * 
	 * @param socket  					= das Socket, auf dem uebertragen wird
	 * @param foreignData				= die ForeignData, die uebertragen wird
	 * @throws UnknownHostException     = Unknown Host
	 * @throws IOException				= Fehler beim Input/Output
	 */

	public void sendForeignDataAsByteArray(Socket socket, ForeignData foreignData) throws UnknownHostException, IOException{

		this.socket = socket;

		byte[] buffer = serialization.fillForeignDataByteArray(foreignData);

		sendByteArray(socket, buffer);
	}

	
	
	/**
	 * Methode, um die eigene IP-Adresse wiederzugeben
	 * 
	 * @return   = Eigene IP-Adresse
	 */
	
	public static String getOwnIpAddress() {
		String ip = "";
		try {
			
			Enumeration<NetworkInterface> enumNetworkInterfaces = NetworkInterface.getNetworkInterfaces();
			while (enumNetworkInterfaces.hasMoreElements()) {
				NetworkInterface networkInterface = enumNetworkInterfaces
						.nextElement();
				Enumeration<InetAddress> enumInetAddress = networkInterface
						.getInetAddresses();
				while (enumInetAddress.hasMoreElements()) {
					InetAddress inetAddress = enumInetAddress.nextElement();

					if (inetAddress.isSiteLocalAddress()) {
						ip = inetAddress.getHostAddress();
					}

				}
			}
		} catch (java.net.SocketException e){
			e.printStackTrace();
		}
		return ip;
	}
}
