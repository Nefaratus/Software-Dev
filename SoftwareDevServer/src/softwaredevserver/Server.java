/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package softwaredevserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jan
 */
public class Server extends Thread {
    
     
     ServerSocket serverSocket;
        
    public static final int SERVERPORT = 4443;
    private boolean running = false;
   // static ObjectOutputStream out;
    
    PrintWriter outw;
    BufferedReader inpr;
    
    private Server.OnMessageReceived messageListener;

    public Server(Server.OnMessageReceived messageListener) {
        this.messageListener = messageListener;
    }
 
    

   @Override
    public void run() {
        super.run();
 
        running = true;
 
        try {
            System.out.println("S: Connecting to Unity");
 
          

            //create a server socket. A server socket waits for requests to come in over the network.
            serverSocket = new ServerSocket(4443, 10);
 
            //create client socket... the method accept() listens for a connection to be made to this socket and accepts it.
            Socket socket = serverSocket.accept();
            System.out.println("S: A connection with Unity has been Made");
           
            try {
 
                //sends the message to the client
              //  os = socket.getOutputStream();
                outw =new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true);
                
                //read the message received from client                
                inpr =new BufferedReader(new InputStreamReader(socket.getInputStream()));

                //in this while we wait to receive messages from client (it's an infinite loop)
                //this while it's like a listener for messages
                while (running) {                    
                    
                    String message = inpr.readLine();
                   
                    if (message != null && messageListener != null) {
                        //call the method messageReceived from ServerBoard class
                        messageListener.messageReceived(message);
                        
                    }
                }
 
            } catch (Exception e) {
                System.out.println("S: Error");
                e.printStackTrace();
            } finally {
                socket.close();
                System.out.println("S: Done.");
            }
 
        } catch (Exception e) {
            System.out.println("S: Error");
            e.printStackTrace();
        }
    }
     //Declare the interface. The method messageReceived(String message) will must be implemented in the ServerBoard
    //class at on startServer button click
    public interface OnMessageReceived {
        public void messageReceived(String message);
    }
    
    public void sent(String sentString){

        try {
           outw.println(sentString);
           
         //  System.out.println(sentString);

        } catch (Exception ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);

        }
        }
    }


