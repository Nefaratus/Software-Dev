/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package softwaredevserver;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author Jan
 */
public class ServerFrame extends JFrame implements ActionListener {
 
    final JFrame frame = new JFrame();
    public JButton send = new JButton("Send");
    public SoftwareDevServer soft;
    final Container north = new Container();
    public JTextArea TekstArea = new JTextArea();
    public JTextArea Chat = new JTextArea();
    
    public ServerFrame()
    {
     north.setLayout(new GridLayout(1, 3));
     north.add(send);
     north.add(Chat);
     send.addActionListener(this);
     soft = new SoftwareDevServer();
     this.add(north,BorderLayout.NORTH);     
     
     
     TekstArea.setEditable(false);
     JScrollPane scrollPane = new JScrollPane(TekstArea);
     this.add(scrollPane);
     
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource().equals(send)) {
            String message = Chat.getText();
           // soft.mServer.sent(message);
            TekstArea.append("Server: " + message + "\n");
            soft.sys.MessageHandler(message.toCharArray());
                       
            
        }
        }
    
}
