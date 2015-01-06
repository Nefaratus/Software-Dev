/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package softwaredevserver;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.TimerTask;
import javax.swing.Timer;

/**
 *
 * @author Jan
 */
public class TrafficLight {
    
    public int status = 1;
    public String stat, stoplight;
    private char type;
    public boolean active;
    public int amount;
    private String c_Status;
    public SoftwareDevServer soft;
        
       static Timer timer;
        static int cnt = 0;
    
    public TrafficLight(String trafficLight)
    {
        soft = new SoftwareDevServer();
        active = true;
        this.stoplight = trafficLight;        
    }
    
    public void setType(char type)
    {
        this.type = type;
    }
    
    public char getType()
    {
        return type;
    }
        
    public void changeAmount(char amount)
    { 
        int n_amount = Character.getNumericValue(amount);
        if(n_amount >= this.amount)
        {
            this.amount = n_amount;
        }
    }
    
    public void changeAmount(int amount)
    {
       this.amount += amount;      
    }
    
    public boolean checkActive()
    {
        if(active == true)
            return true;
        else
            return false;
    }
    
    public void resetAmount()
    {
        amount = 0;
    }
    
    public int getAmount()
    {
        return amount;
    }
    
    public String Status()
    {        
        switch(status)
        {     
            case 1: stat = "R";    //Red        
                    StartTimer(2);
                    //Timer2(2);
                    active = false;
                    break;                
            case 2: stat = "G";    //Green    
                    if(amount > 7)
                        StartTimer(amount);
                    else   
                        StartTimer(6);
                    //Timer2(6);
                    break;                
            case 3: stat = "O";   //Orange
                    StartTimer(4);
                    //Timer2(4);
                    break;    
            default: stat = "Done";
        }
        return stat;
    }
   
    
    public void NextStatus()
    {
        if (status < 3)
        {
            status ++;      
        }
        else
        {
            status = 1;
        }
    }
    
     public void StartTimer(final float time)
    {        
        final java.util.Timer timer = new java.util.Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            
            float i = time;
            public void run() {
                
                    System.out.println(i);
                i--;
                if (i < 0)
                {
                    timer.cancel();
                    if(active == true && type != 'T')
                    {
                        NextStatus();
                        c_Status = Status();
                        soft.mServer.sent(stoplight+type+c_Status);
                        if(type == 'F')
                        {
                            soft.mServer.sent(stoplight+'V'+c_Status);
                        }
                    }
                    if(active == true && type == 'T')
                    {                        
                        if(i < 2)
                        {
                            soft.sys.lights.TreinInc = false;
                            System.out.println("Been here so TreinInc is false");
                            active = false;
                        }
                    }
                    else
                    {
                        timer.cancel();                       
                    }
                }
            }
        }, 0, 1000);

    } 
     
  }
