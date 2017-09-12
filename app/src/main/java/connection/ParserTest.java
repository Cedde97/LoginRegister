package connection;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import android.util.Log;
import exception.XMustBeLargerThanZeroException;
import exception.YMustBeLargerThanZeroException;
import model.Corner;
import model.Node;
import model.Zone;

public class ParserTest {

    public static final int ROUTING = 7;
    public static final int PORTNR = 9797;

    public static void main (String [] args) throws Exception, XMustBeLargerThanZeroException, YMustBeLargerThanZeroException{

        Server server 	= new Server();
        Serialization serialization = new Serialization();
        ServerSocket ss = null;
        Node node = null;

        try{
            System.out.println("Server is started");
            ss = new ServerSocket(PORTNR);

            System.out.println("Server is waiting for request");
            Socket s = ss.accept();

            System.out.println("Client Connected");

            byte[] buffer = server.receiveByteArray(ss, s);

            System.out.println("BufferBytes: " + buffer.length);

            System.out.println("Received ByteArray");

            int methodName = serialization.getByteHeader(buffer);

            System.out.println(Integer.toString(methodName));


            switch(methodName){

                case 1: {
                    System.out.println("File Transfer Request");

                    String pathDestination = "C://Users/Cedric/Pictures/test/placeholderNew1.jpg";

                    File newFile = new File(pathDestination);

                    byte[] bufferBody = serialization.getByteData(buffer);

                    server.saveFileFromByteArray(bufferBody, newFile);

                    System.out.println("Converted ByteArray");

                    System.out.println("Saved File to: " + pathDestination);

                    break;
                }

                case 2: {
                    System.out.println("Node Transfer Request");

                    byte[] bufferBody = serialization.getByteData(buffer);

                    node = serialization.deserializdeNode(bufferBody);

                    System.out.println(node);

                    break;
                }

                case ROUTING: {
                    System.out.println("Routing:");

                    Corner corner  = new Corner(0,1);
                    Corner corner1 = new Corner(1,1);
                    Corner corner2 = new Corner(0,0);
                    Corner corner3 = new Corner(1,0);

                    RoutHelper rh = server.getRoutHelper(buffer);

                    Zone zone = new Zone(corner,corner1,corner2,corner3);

                    Node oldNode = new Node(01l, 0.1, 0.1, "192.168.2.115", 2, zone);

                    Node nodeNew = oldNode.routing(rh.getIP(),rh.getX(),rh.getY(),rh.getID());

                    System.out.println(nodeNew.toString());

                }
            }

            ss.close();
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            try{
                if(ss != null)
                    ss.close();
                System.out.println("ServerSocket closed");
            } catch(IOException e){
                e.printStackTrace();
            }
        }
    }

}
