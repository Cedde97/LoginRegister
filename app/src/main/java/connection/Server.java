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

@SuppressWarnings("unused")
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

	public RoutHelper getRoutHelper(byte[] buffer){

		RoutHelper routHelper = null;

		byte[] bufferBody = serialization.getByteData(buffer);

		routHelper = serialization.deserializdeRoutHelper(bufferBody);

		return routHelper;
	}

	public Neighbour getNeighbour(byte[] buffer){

		Neighbour neighbour = null;

		byte[] bufferBody = serialization.getByteData(buffer);

		neighbour = serialization.deserializdeNeighbour(bufferBody);

		return neighbour;
	}

	public PeerMemo getPeerMemo(byte[] buffer){

		PeerMemo peerMemo = null;

		byte[] bufferBody = serialization.getByteData(buffer);

		peerMemo = serialization.deserializdePeerMemo(bufferBody);

		return peerMemo;
	}

	public ForeignData getForeignData(byte[] buffer){

		ForeignData foreignData = null;

		byte[] bufferBody = serialization.getByteData(buffer);

		foreignData = serialization.deserializdeForeignData(bufferBody);

		return foreignData;
	}

	public ArrayList<PeerMemo> getListPeer(byte[] buffer){

		ArrayList<PeerMemo> list;

		byte[] bufferBody = serialization.getByteData(buffer);

		list = serialization.deserializePeerMemoList(bufferBody);

		return list;
	}

	public ArrayList<Neighbour> getListNeighbour(byte[] buffer){

		ArrayList<Neighbour> list;

		byte[] bufferBody = serialization.getByteData(buffer);

		list = serialization.deserializeNeighbourList(bufferBody);

		return list;
	}

}
