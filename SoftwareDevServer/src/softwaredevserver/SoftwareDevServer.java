/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package softwaredevserver;

import javax.swing.JFrame;

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
        //sys.start();
        /*
       String test = "1NWA";
       String test2 = "1ZWA";
       String test3 = "1NWA";
       String test4 = "1NWA";
       String test5 = "1ZWA";
       String test6 = "1NWA";
       sys.addToList(test);
       sys.addToList(test2);
       sys.addToList(test3);
       sys.addToList(test4);
       sys.addToList(test5);       
       sys.addToList(test6);
                */
       
 


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
  

