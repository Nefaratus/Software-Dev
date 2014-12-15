/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package softwaredevserver;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.Timer;

/**
 *
 * @author Jan
 */

public class SoftwareDevServer {

    /**
     * @param args the command line arguments
     */
    
    
 
    static boolean active = true;
    static ServerFrame frame = new ServerFrame();
    static TrafficSystem sys;
    static String prevMessage;

    public static void main(String[] args){
        
        sys = new TrafficSystem();
        
        Frame();
        mServer.start(); 
        sys.start();
     
        String test = "1NWA1";
       String test2 = "1ZWA9";
       String test3 = "1OWB1";
       String test4 = "1NZF8";
      sys.MessageHandler(test.toCharArray());
       sys.MessageHandler(test2.toCharArray());
       sys.MessageHandler(test3.toCharArray());
       sys.MessageHandler(test4.toCharArray());

    }
    
    public void Response()
    {
        mServer.sent("");
    }
  
     public static Server mServer = new Server(new Server.OnMessageReceived() {
        public void messageReceived(String message) {
          //  System.out.println(message);
           
       // frame.TekstArea.append("Client: " + message + "\n");
            try{
                             
                //System.out.println(message);                  
                sys.MessageHandler(message.toCharArray());
                // mServer.sent(x.response);
                prevMessage = message;
                
            }catch(Exception e)
            {
                //System.out.println(e);
            }
         }
    });
          
     
    public static void Frame() {
        //opens the window where the messages will be received and sent
        frame = new ServerFrame();
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    
    

}
  

