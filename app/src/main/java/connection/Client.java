package connection;

/**
 * Created by Cedric on 04.09.2017.
 */
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import model.Node;


public class Client {

    protected static final int portNr = 8080;
    Serialization serialization = new Serialization();
    Socket socket = null;



    /**
     * Methode, um eine File/ein Image als ByteArray zu senden
     * @param file							= die File/ das Image zum Senden
     * @uses imageSerializer				= Hilfsmethode, um eine File/ein Image als ByteArray wiederzugeben
     * @uses sendByteArray 					= Hilfsmethode, zum Senden eines ByteArrays
     * @throws UnknownHostException			= unbekannter Host
     * @throws IOException					= Fehler beim Input/Output
     */

    protected void sendImageAsByteArray(Socket socket, File file){

        this.socket = socket;

        try {

            byte[] buffer = serialization.imageSerializer(file);

            sendByteArray(socket, buffer);
            try{
                if(socket!= null){
                    socket.close();
                }
            } catch(IOException e){
                e.printStackTrace();
            }
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

    protected void sendByteArray (Socket socket, byte[] buffer)throws IOException, UnknownHostException{

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
     *
     * @param ip						= IP Adresse des Empfaengers
     * @param node						= der zu uebertragende Knoten/Node
     * @throws UnknownHostException		= Unknown Host
     * @throws IOException				= Fehler beim Input/Output
     */

    protected void sendNodeAsByteArray(String ip, Node node) throws UnknownHostException, IOException{

        Socket socket = new Socket(ip, portNr);

        byte[] buffer = serialization.serializeNode(node);

        sendByteArray(socket, buffer);

    }

    private String convertDoubleToString(double wert){
        String newString = Double.toString(wert);
        return newString;
    }


    @SuppressWarnings("unused")
    private void sendAll(String ip,String method,String secondString, double x, double y, int schlahmichtot) throws IOException{
        Socket s = new Socket(ip, portNr);

        sendMethod(s, method);
        sendString(s, secondString);
        sendX(s, x);
        sendY(s,y);
        sendInt(s, schlahmichtot);
        s.close();
    }

    private void sendString(Socket s, String secondString) throws IOException{

        OutputStreamWriter osw = new OutputStreamWriter(s.getOutputStream());
        PrintWriter out = new PrintWriter(osw);
        out.write(secondString + "@");
        osw.flush();
    }
    private void sendMethod(Socket s, String method) throws UnknownHostException, IOException{

        OutputStreamWriter osw = new OutputStreamWriter(s.getOutputStream());
        PrintWriter out = new PrintWriter(osw);
        out.write(method + ",");
        osw.flush();

    }


    private void sendX(Socket s, double wert) throws UnknownHostException, IOException{

        OutputStreamWriter osw = new OutputStreamWriter(s.getOutputStream());
        PrintWriter out = new PrintWriter(osw);
        String wertAlsString = convertDoubleToString(wert);
        out.write(wertAlsString + "X");
        osw.flush();

    }

    private void sendY(Socket s, double wert) throws UnknownHostException, IOException{

        OutputStreamWriter osw = new OutputStreamWriter(s.getOutputStream());
        PrintWriter out = new PrintWriter(osw);
        String wertAlsString = convertDoubleToString(wert);
        out.write(wertAlsString + "Y");
        osw.flush();

    }

    private void sendInt(Socket s, int wert) throws UnknownHostException, IOException{

        OutputStreamWriter osw = new OutputStreamWriter(s.getOutputStream());
        PrintWriter out = new PrintWriter(osw);
        String wertAlsString = Integer.toString(wert);
        out.write(wertAlsString + "I");
        osw.flush();

    }
}