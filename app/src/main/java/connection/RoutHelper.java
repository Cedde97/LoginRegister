package connection;

public class RoutHelper implements java.io.Serializable {

    protected String ip;
    protected double x,y;
    protected int id;


    public RoutHelper(String ip, double x, double y, int id){
        this.ip = ip;
        this.x  = x;
        this.y  = y;
        this.id = id;
    }

    public String toString(){
        return ("IP : " + ip + "\n" +
                "X  : " + x  + "\n" +
                "Y  : " + y  + "\n" +
                "id : " + id);
    }

    public String getIP(){
        return ip;
    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    public int getID(){
        return id;
    }
}
