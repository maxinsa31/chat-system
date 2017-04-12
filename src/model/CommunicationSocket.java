package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;

public class CommunicationSocket {

    private Socket socket;
    
    private InetAddress remoteIpAddress;
    
    private BufferedReader buffRead;
    
    private BufferedWriter buffWrite;
    
    public CommunicationSocket(Socket socket){
        this.socket = socket;
        this.remoteIpAddress = socket.getInetAddress();
        try {
            InputStreamReader input = new InputStreamReader(socket.getInputStream());
            OutputStreamWriter output = new OutputStreamWriter(socket.getOutputStream());
            buffRead = new BufferedReader(input);
            buffWrite = new BufferedWriter(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
}