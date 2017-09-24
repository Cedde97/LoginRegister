package connection;

import android.os.Environment;
import android.util.Log;

import model.*;
import source.ForeignDataDbSource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


/**
 * Klasse Serialization, die Methoden zum Serialisieren und Deserialisieren von Objekten bietet.
 * 
 * @author Cedric 
 */

public class Serialization {

	protected static final int MAX_GR_PIC_IN_KB                 = 5000; //5MB
	protected static final int RESERVED_BYTES_FOR_METHOD_CALL   = 1;
	
	protected static final int STR_SEND_IMG                     = 1;
	protected static final int STR_SEND_NODE                    = 2;
	protected static final int STR_SEND_ROUTING_REQUEST         = 3;
	protected static final int STR_SEND_NEIGHBOUR               = 4;
	protected static final int STR_SEND_PEERMEMO                = 5;
	protected static final int STR_SEND_FOREIGNDATA             = 6;
	protected static final int STR_SEND_LIST_PEERMEMO           = 7;
	protected static final int STR_SEND_LIST_NEIGHBOUR          = 8;
	protected static final int STR_SEND_ROUT_HELPER_PIC         = 9;


	/**
	 * Methode, die ein Objekt in ein ByteArray umwandelt.
	 * 
	 * @param object			= das Objekt, das umgewandelt werden soll
	 * @return					= das Objekt als ByteArray
	 */

	protected byte[] serializeObject(Object object){

		byte[] buffer = null;

		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		try{
			ObjectOutput out = new ObjectOutputStream(bos);
			out.writeObject(object);
			out.flush();
			buffer = bos.toByteArray();
			bos.close();

		} catch(IOException e){
			e.printStackTrace();
		}
		return buffer;
	}


	
	/**
	 * Methode, um aus einem ByteArray ein Knoten/Node wiederherzustellen
	 *
	 * @param buffer    = ByteArray, aus dem Knoten/Node Objekt hergestellt werden soll
	 * @return          = Knoten/Node
	 */

	protected Node deserializeNode(byte[] buffer){

		Node node = null;
		ByteArrayInputStream bis = new ByteArrayInputStream(buffer);

		try{
			ObjectInputStream in = new ObjectInputStream(bis);

			node = (Node) in.readObject();

		}catch (IOException e){
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return node;
	}



	/**
	 * Methode, die ein ByteArray zurueck in ein RoutHelper Objekt umwandelt.
	 * 
	 * @param buffer 				= das ByteArray, das umgewandelt wird
	 * @return						= der RoutHelper aus dem ByteArray
	 */
	
	protected RoutHelper deserializeRoutHelper(byte[] buffer){

		RoutHelper routHelper = null;
		ByteArrayInputStream bis = new ByteArrayInputStream(buffer);

		try{

			ObjectInputStream in = new ObjectInputStream(bis);

			routHelper = (RoutHelper) in.readObject();

		}catch (IOException e){
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return routHelper;
	}

	

	/**
	 * Methode, die ein ByteArray zurueck in ein Neighbour Objekt umwandelt.
	 * 
	 * @param buffer 				= das ByteArray, das umgewandelt wird
	 * @return						= der Neighbour aus dem ByteArray
	 */
	
	protected Neighbour deserializeNeighbour(byte[] buffer){

		Neighbour neighbour = null;
		ByteArrayInputStream bis = new ByteArrayInputStream(buffer);

		try{
			ObjectInputStream in = new ObjectInputStream(bis);

			neighbour = (Neighbour) in.readObject();

		}catch (IOException e){
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return neighbour;
	}



	/**
	 * Methode, die ein ByteArray zurueck in ein PeerMemo Objekt umwandelt.
	 * 
	 * @param buffer 				= das ByteArray, das umgewandelt wird
	 * @return						= das PeerMemo aus dem ByteArray
	 */
	
	protected PeerMemo deserializePeerMemo(byte[] buffer){

		PeerMemo peerMemo = null;
		ByteArrayInputStream bis = new ByteArrayInputStream(buffer);

		try{
			ObjectInputStream in = new ObjectInputStream(bis);

			peerMemo = (PeerMemo) in.readObject();

		}catch (IOException e){
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return peerMemo;
	}



	/**
	 * Methode, die ein ByteArray zurueck in ein ForeignData Objekt umwandelt.
	 * 
	 * @param buffer 				= das ByteArray, das umgewandelt wird
	 * @return						= die ForeignData aus dem ByteArray
	 */
	
	protected ForeignData deserializeForeignData(byte[] buffer){

		ForeignData foreignData= null;
		ByteArrayInputStream bis = new ByteArrayInputStream(buffer);

		try{
			ObjectInputStream in = new ObjectInputStream(bis);

			foreignData = (ForeignData) in.readObject();

		}catch (IOException e){
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return foreignData;
	}

	
	
	/**
	 * Methode, die ein ByteArray zurueck in eine Neighbour-Liste umwandelt.
	 * 
	 * @param buffer 				= das ByteArray, das umgewandelt wird
	 * @return						= die Neigbour-Liste aus dem ByteArray
	 */
	
	protected ArrayList<Neighbour> deserializeNeighbourList(byte[] buffer){

		ArrayList<Neighbour> list = new ArrayList<Neighbour>();

		ByteArrayInputStream bis = new ByteArrayInputStream(buffer);

		try{
			ObjectInputStream in = new ObjectInputStream(bis);

			list = (ArrayList<Neighbour>) in.readObject();

		}catch (IOException e){
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return list;
	}

	
	
	/**
	 * Methode, die ein ByteArray zurueck in eine PeerMemo-Liste umwandelt.
	 * 
	 * @param buffer 				= das ByteArray, das umgewandelt wird
	 * @return						= die PeerMemo-Liste aus dem ByteArray
	 */
	
	protected ArrayList<PeerMemo> deserializePeerMemoList(byte[] buffer){

		ArrayList<PeerMemo> list = new ArrayList<PeerMemo>();

		ByteArrayInputStream bis = new ByteArrayInputStream(buffer);

		try{
			ObjectInputStream in = new ObjectInputStream(bis);

			list = (ArrayList<PeerMemo>) in.readObject();

		}catch (IOException e){
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	
	/**
	 * Methode, die eine File(/Image) in ein ByteArray liest.
	 *
	 * @param file                              = File(/Bild), die eingespeist werden soll
	 * @return                                  = ByteArray, in dem das Bild "steht"
	 * @throws FileNotFoundException            = falls die File nicht existiert
	 * @throws IOException                      = Fehler beim Input/Output
	 */
	protected byte[] imageSerializer(File file){

		ByteArrayOutputStream os = new ByteArrayOutputStream();

		try{
			
			byte[] buffer = new byte[MAX_GR_PIC_IN_KB*1024];
			FileInputStream fis      = new FileInputStream(file);
			int read;

			while((read = fis.read(buffer)) != -1){
				os.write(buffer, 0, read);
			}

			fis.close();
			os.close();

		}catch (FileNotFoundException e){
			e.printStackTrace();

		}catch (IOException e){
			e.printStackTrace();
		}
		return os.toByteArray();
	}



	/**
	 * Methode, die ein ByteArray zurueck in ein Bild konvertiert und in einer File speichert
	 *
	 * @param buffer                            = das ByteArray
	 * @param destination                       = ZielDatei, in die das Bild geschrieben werden soll
	 * @throws FileNotFoundException            = falls die File nicht existiert
	 * @throws IOException                      = Fehler beim Input/Output
	 */
	
	public void imageDeSerializer(byte[] buffer, File destination)throws IOException{

		try{
			FileOutputStream fos = new FileOutputStream(destination);
			fos.write(buffer);
			fos.close();
		}catch (Exception e){
			e.printStackTrace();
		}

	}


	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////   FILLER    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	
	/**
	 * Methode getByteHeader, die den Header eines HilfsByteArrays zurueckgibt
	 *
	 * @param buffer        = das HilfsByteArray
	 * @return              = der Header (= die MethodenAufrufZiffer)
	 */

	protected int getByteHeader(byte[] buffer){
		int methodName = buffer[0];

		return methodName;
	}



	/**
	 * Methode getByteData, die den Body/Nutzdaten eines HilfsbyteArrays zurueckgibt
	 *
	 * @param buffer        = das HilfsByteArray
	 * @return              = den Body/die Nutzdaten als ByteArray
	 */

	protected byte[] getByteData(byte[] buffer){

		byte[] bufferBody = new byte[buffer.length -1];

		System.arraycopy(buffer, 1, bufferBody, 0, buffer.length -1);

		return bufferBody;
	}
	
	
	
	/**
	 * Methode fillImageByteArray, zum Fuellen eines HilfsByteArrays
	 *      Der Header (byte[0]) ist fuer den MethodenAufrufNamen reserviert (1)
	 *      Im Body (ab byte[1]) stehen die eigentlichen Nutzdaten
	 *
	 * @param file      = die File, die in das HilfsByteArray geschrieben werden soll
	 * @return          = das erzeugte HilfsByteArray
	 */

	protected byte[] fillImageByteArray(File file){

		byte methodName = (byte) STR_SEND_IMG;


		byte[] bufferBody = imageSerializer(file);

		byte[] bufferTarget = new byte[RESERVED_BYTES_FOR_METHOD_CALL + bufferBody.length];

		bufferTarget[0] = methodName;

		System.arraycopy(bufferBody, 0, bufferTarget, 1, bufferBody.length);

		return bufferTarget;
	}



	/**
	 * Methode fillNodeByteArray, zum Fuellen eines HilfsByteArrays
	 *      Der Header (byte[0]) ist fuer den MethodenAufrufNamen reserviert (2)
	 *      Im Body (ab byte[1]) stehen die eigentlichen Nutzdaten
	 *
	 * @param node      = der Node, der in das HilfsByteArray geschrieben werden soll
	 * @return          = das erzeugte HilfsByteArray
	 */

	protected byte[] fillNodeByteArray(Node node){

		byte methodName = (byte) STR_SEND_NODE;

		byte[] bufferBody = serializeObject(node);

		int bufferLength = bufferBody.length;

		byte[] bufferTarget = new byte[RESERVED_BYTES_FOR_METHOD_CALL + bufferBody.length];

		bufferTarget[0] = methodName;

		System.arraycopy(bufferBody, 0, bufferTarget, 1, bufferBody.length);

		return bufferTarget;
	}

	
	
	/**
	 * Methode fillRoutHelperByteArray, zum Fuellen eines HilfsByteArrays
	 *      Der Header (byte[0]) ist fuer den MethodenAufrufNamen reserviert (3)
	 *      Im Body (ab byte[1]) stehen die eigentlichen Nutzdaten
	 *
	 * @param routHelper	  = der RoutHelper, der in das HilfsByteArray geschrieben werden soll
	 * @return           	  = das erzeugte HilfsByteArray
	 */
	
	protected byte[] fillRoutHelperByteArray(RoutHelper routHelper){

			byte methodName = (byte) STR_SEND_ROUTING_REQUEST;

			byte[] bufferBody = serializeObject(routHelper);

			byte[] bufferTarget = new byte[RESERVED_BYTES_FOR_METHOD_CALL + bufferBody.length];

			bufferTarget[0] = methodName;

			System.arraycopy(bufferBody, 0, bufferTarget, 1, bufferBody.length);

			return bufferTarget;
		}

	
	
	/**
	 * Methode fillNeighbourByteArray, zum Fuellen eines HilfsByteArrays
	 *      Der Header (byte[0]) ist fuer den MethodenAufrufNamen reserviert (4)
	 *      Im Body (ab byte[1]) stehen die eigentlichen Nutzdaten
	 *
	 * @param neighbour      = der Neighbour, der in das HilfsByteArray geschrieben werden soll
	 * @return               = das erzeugte HilfsByteArray
	 */
	
	protected byte[] fillNeighbourByteArray(Neighbour neighbour){

		byte methodName = (byte) STR_SEND_NEIGHBOUR;

		byte[] bufferBody = serializeObject(neighbour);

		byte[] bufferTarget = new byte[RESERVED_BYTES_FOR_METHOD_CALL + bufferBody.length];

		bufferTarget[0] = methodName;

		System.arraycopy(bufferBody, 0, bufferTarget, 1, bufferBody.length);

		return bufferTarget;
	}

	
	
	/**
	 * Methode fillPeerMemoByteArray, zum Fuellen eines HilfsByteArrays
	 *      Der Header (byte[0]) ist fuer den MethodenAufrufNamen reserviert (5)
	 *      Im Body (ab byte[1]) stehen die eigentlichen Nutzdaten
	 *
	 * @param peerMemo      = das PeerMemo, das in das HilfsByteArray geschrieben werden soll
	 * @return               = das erzeugte HilfsByteArray
	 */
	
	protected byte[] fillPeerMemoByteArray(PeerMemo peerMemo){

		byte methodName = (byte) STR_SEND_PEERMEMO;

		byte[] bufferBody = serializeObject(peerMemo);

		byte[] bufferTarget = new byte[RESERVED_BYTES_FOR_METHOD_CALL + bufferBody.length];

		bufferTarget[0] = methodName;

		System.arraycopy(bufferBody, 0, bufferTarget, 1, bufferBody.length);

		return bufferTarget;
	}

	
	
	/**
	 * Methode fillForeignDataByteArray, zum Fuellen eines HilfsByteArrays
	 *      Der Header (byte[0]) ist fuer den MethodenAufrufNamen reserviert (6)
	 *      Im Body (ab byte[1]) stehen die eigentlichen Nutzdaten
	 *
	 * @param foreignData    = die ForeignData, die in das HilfsByteArray geschrieben werden soll
	 * @return               = das erzeugte HilfsByteArray
	 */
	
	protected byte[] fillForeignDataByteArray(ForeignData foreignData){

		byte methodName = (byte) STR_SEND_FOREIGNDATA;

		byte[] bufferBody = serializeObject(foreignData);

		byte[] bufferTarget = new byte[RESERVED_BYTES_FOR_METHOD_CALL + bufferBody.length];

		bufferTarget[0] = methodName;

		System.arraycopy(bufferBody, 0, bufferTarget, 1, bufferBody.length);

		return bufferTarget;
	}
	
	
	
	/**
	 * Methode fillPeerMemoListByteArray, zum Fuellen eines HilfsByteArrays
	 *      Der Header (byte[0]) ist fuer den MethodenAufrufNamen reserviert (7)
	 *      Im Body (ab byte[1]) stehen die eigentlichen Nutzdaten
	 *
	 * @param list      = die PeerMemo-Liste, die in das HilfsByteArray geschrieben werden soll
	 * @return          = das erzeugte HilfsByteArray
	 */
	
	protected byte[] fillPeerMemoListByteArray(ArrayList<PeerMemo> list){

		byte methodName = (byte) STR_SEND_LIST_PEERMEMO;

		byte[] bufferBody = serializeObject(list);

		byte[] bufferTarget = new byte[RESERVED_BYTES_FOR_METHOD_CALL + bufferBody.length];

		bufferTarget[0] = methodName;

		System.arraycopy(bufferBody, 0, bufferTarget, 1, bufferBody.length);

		return bufferTarget;
	}
	
	
	
	/**
	 * Methode fillNeighbourListByteArray, zum Fuellen eines HilfsByteArrays
	 *      Der Header (byte[0]) ist fuer den MethodenAufrufNamen reserviert (8)
	 *      Im Body (ab byte[1]) stehen die eigentlichen Nutzdaten
	 *
	 * @param list      = die Neighbour-Liste, die in das HilfsByteArray geschrieben werden soll
	 * @return          = das erzeugte HilfsByteArray
	 */

	protected byte[] fillNeighbourListByteArray(ArrayList<Neighbour> list){

		byte methodName = (byte) STR_SEND_LIST_NEIGHBOUR;

		byte[] bufferBody = serializeObject(list);

		byte[] bufferTarget = new byte[RESERVED_BYTES_FOR_METHOD_CALL + bufferBody.length];

		bufferTarget[0] = methodName;

		System.arraycopy(bufferBody, 0, bufferTarget, 1, bufferBody.length);

		return bufferTarget;
	}



	/**
	 * Methode fillRoutHelperByteArray, zum Fuellen eines HilfsByteArrays
	 *      Der Header (byte[0]) ist fuer den MethodenAufrufNamen reserviert (9)
	 *      Im Body (ab byte[1]) stehen die eigentlichen Nutzdaten
	 *
	 * @param routHelper	  = der RoutHelper, der in das HilfsByteArray geschrieben werden soll
	 * @return           	  = das erzeugte HilfsByteArray
	 */

	protected byte[] fillRoutHelperPicByteArray(RoutHelper routHelper){

		byte methodName = (byte) STR_SEND_ROUT_HELPER_PIC;

		byte[] bufferBody = serializeObject(routHelper);

		byte[] bufferTarget = new byte[RESERVED_BYTES_FOR_METHOD_CALL + bufferBody.length];

		bufferTarget[0] = methodName;

		System.arraycopy(bufferBody, 0, bufferTarget, 1, bufferBody.length);

		return bufferTarget;
	}

	///////////////////////////// von Alex  ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	
	private String getFoto(int uid, ForeignDataDbSource foreignDataDb)
	{
		String foto = "";
		Log.d("TEST",""+ uid );
		if (uid == foreignDataDb.getUidForeign()) {
			foto = foreignDataDb.getFotoId(foreignDataDb.getUidForeign()) + ".jpg";
			Log.d("TEST", "FOTO " + foto);
		}
		return foto;
	}

	private File getFilePath(int uid, ForeignDataDbSource foreignDataDb)
	{
		return new File(Environment.getExternalStorageDirectory() + File.separator+"images"
				+ File.separator + "CAN_PICS" + File.separator + getFoto(uid,foreignDataDb) );
	}
}

