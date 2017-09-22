package connection;
import model.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Klasse Server, die Methoden zum Umwandeln des empfangenen Byte-Arrays bietet.
 * 
 * @author Cedric
 *
 */

public class Server {
	ServerSocket serverSocket = null;
	Serialization serialization = new Serialization();



	/**
	 * Methode zum Empfangen eines ByteArrays des Senders
	 *
	 * @param serverSocket 		= ServerSocket, auf dem Empfangen wird
	 * @return					= ByteArray, das gesendet wurde
	 * @throws IOException		= Fehler bei Input/Output
	 */

	public byte[] receiveByteArray(ServerSocket serverSocket, Socket socket) throws IOException{

		byte[] buffer = null;

		try{

			DataInputStream dIn = new DataInputStream(socket.getInputStream());
			int length = dIn.readInt();

			if(length>0) {
				buffer = new byte[length];
				dIn.readFully(buffer, 0, buffer.length);
			}
			try{
				if(serverSocket!= null){
					serverSocket.close();
				}
			} catch(IOException e){
				e.printStackTrace();
			}
		}catch (IOException e){
			e.printStackTrace();
		}
		return buffer;
	}



	/**
	 * Methode, um ein ByteArray in ein Bild zu konvertieren
	 *
	 * @param buffer 			= das ByteArray, das konvertiert werden soll
	 * @return 					= die File/das Image
	 */

	protected void saveFileFromByteArray(byte[] buffer, File file){

		try{
			serialization.imageDeSerializer(buffer, file);

		} catch(IOException e){
			e.printStackTrace();
		}


	}

	
	
	/**
	 * Methode, die ein empfangenes ByteArray in ein RoutHelper Objekt umwandelt
	 * 
	 * @param buffer			= das empfangene ByteArray
	 * @return					= das RoutHelper Objekt
	 */
	
	public RoutHelper getRoutHelper(byte[] buffer){

		RoutHelper routHelper = null;

		byte[] bufferBody = serialization.getByteData(buffer);

		routHelper = serialization.deserializeRoutHelper(bufferBody);

		return routHelper;
	}



	/**
	 * Methode, die ein empfangenes ByteArray in ein Node Objekt umwandelt
	 *
	 * @param buffer			= das empfangene ByteArray
	 * @return					= das Node Objekt
	 */

	public Node getNode(byte[] buffer){

		Node node = null;

		byte[] bufferBody = serialization.getByteData(buffer);

		node = serialization.deserializeNode(bufferBody);

		return node;
	}

	
	
	/**
	 * Methode, die ein empfangenes ByteArray in ein Neighbour Objekt umwandelt
	 * 
	 * @param buffer			= das empfangene ByteArray
	 * @return					= das Neighbour Objekt
	 */
	
	public Neighbour getNeighbour(byte[] buffer){

		Neighbour neighbour = null;

		byte[] bufferBody = serialization.getByteData(buffer);

		neighbour = serialization.deserializeNeighbour(bufferBody);

		return neighbour;
	}

	
	
	/**
	 * Methode, die ein empfangenes ByteArray in ein PeerMemo Objekt umwandelt
	 * 
	 * @param buffer			= das empfangene ByteArray
	 * @return					= das PeerMemo Objekt
	 */
	
	public PeerMemo getPeerMemo(byte[] buffer){

		PeerMemo peerMemo = null;

		byte[] bufferBody = serialization.getByteData(buffer);

		peerMemo = serialization.deserializePeerMemo(bufferBody);

		return peerMemo;
	}

	
	
	/**
	 * Methode, die ein empfangenes ByteArray in ein PeerMemo Objekt umwandelt
	 * 
	 * @param buffer			= das empfangene ByteArray
	 * @return					= das PeerMemo Objekt
	 */
	
	public ForeignData getForeignData(byte[] buffer){

		ForeignData foreignData = null;

		byte[] bufferBody = serialization.getByteData(buffer);

		foreignData = serialization.deserializeForeignData(bufferBody);

		return foreignData;
	}

	
	
	/**
	 * Methode, die ein empfangenes ByteArray in eine PeerMemo-Liste umwandelt
	 * 
	 * @param buffer			= das empfangene ByteArray
	 * @return					= die PeerMemo-Liste
	 */
	
	public ArrayList<PeerMemo> getListPeer(byte[] buffer){

		ArrayList<PeerMemo> list;

		byte[] bufferBody = serialization.getByteData(buffer);

		list = serialization.deserializePeerMemoList(bufferBody);

		return list;
	}
	
	

	/**
	 * Methode, die ein empfangenes ByteArray in eine Neighbour-Liste umwandelt
	 * 
	 * @param buffer			= das empfangene ByteArray
	 * @return					= die Neighbour-Liste
	 */
	
	public ArrayList<Neighbour> getListNeighbour(byte[] buffer){

		ArrayList<Neighbour> list;

		byte[] bufferBody = serialization.getByteData(buffer);

		list = serialization.deserializeNeighbourList(bufferBody);

		return list;
	}

}
