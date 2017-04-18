package model;

import java.net.InetAddress;

public class Receiver {

    private InetAddress IP;
    
    private int port;
    
    private  String pseudo;
    
    public Receiver(InetAddress IP, String pseudo, int port){
   	 this.IP = IP;
   	 this.pseudo = pseudo;
   	 this.port = port;
    }

    public InetAddress getIP() {
   	 return IP;
    }

    public String getPseudo() {
   	 return pseudo;
    }
    
    public int getPort() {
      	 return port;
       }

}
