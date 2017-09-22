package connection;

/**
 * 
 * Klasse Routhelper, zur Erleichterung des Routings
 * 
 * Durch das Objekt werden IP-Adresse, X und Y Wert und die ID uebertragen
 * 
 * @author Cedric
 *
 */


public class RoutHelper implements java.io.Serializable {

	private static final long serialVersionUID = 2995651819741601498L;
	protected String ip;
    protected double x,y;
    protected int id;

    
/**
 * Standard Konstruktor des RoutHelper Objekts
 * 
 * @param ip			= die eigene IP-Adresse
 * @param x				= die X-Koordinate
 * @param y				= die Y-Koordinate
 * @param id			= die User-ID
 */
    public RoutHelper(String ip, double x, double y, int id){
        this.ip = ip;
        this.x  = x;
        this.y  = y;
        this.id = id;
    }

    
    /**
     * Getter und Setter
     */
    public void setIP(String ip){
    	this.ip = ip;
    }
    
    public void setX(double x){
    	this.x  = x;
    }
    
    public void setY(double y){
    	this.y  = y;
    }
    
    public void setID(int id){
    	this.id = id;
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
    
    /**
     * Methode, um das RoutHelper Objekt formatiert auszugeben
     */
    
    public String toString(){
        return ("IP : " + ip + "\n" +
                "X  : " + x  + "\n" +
                "Y  : " + y  + "\n" +
                "id : " + id);
    }
}
