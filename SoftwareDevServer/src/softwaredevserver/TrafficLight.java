/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package softwaredevserver;

import java.util.Timer;
import java.util.TimerTask;

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
    public SoftwareDevServer soft;
    
    
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
    
    public void changeAmount(char amount)
    { 
        int n_amount = Character.getNumericValue(amount);
        if(n_amount >= this.amount)
        {
            this.amount = n_amount;
        }
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
                    active = false;
                    break;                
            case 2: stat = "G";    //Green    
                    StartTimer(6);
                    break;                
            case 3: stat = "O";   //Orange
                    StartTimer(4);
                    break;    
            default: stat = "Done";
        }
        return stat;
    }
   
    
    public void NextStatus()
    {
        if (status < 3)
            status ++;
        
        else
            status = 1;
    }
    
    
     public void StartTimer(final int time)
    {        
        final java.util.Timer timer = new java.util.Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            
            int i = time;
            public void run() {
                //System.out.println(i);
                i--;
                if (i < 0)
                {
                    timer.cancel();
                    if(active == true)
                    {
                        NextStatus();
                        soft.mServer.sent(stoplight+type+Status());
                        System.out.println(stoplight+type+Status());
                    }
                    else
                    {
                        status = 0;                        
                    }
                }
            }
        }, 0, 1000);

    }   
}
