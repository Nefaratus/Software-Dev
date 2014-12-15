/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package softwaredevserver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jan
 */
public class TrafficSystem extends Thread {
    

    
    TrafficLight highestPrior;
    TrafficLight prevLight;    
    TrafficLight SecondPrior = new TrafficLight("placeHolder");
    TrafficLight ThirdPrior = new TrafficLight("placeHolder");    
    TrafficLight FietsPrior = new TrafficLight("placeHolder");    
    
    public boolean next = true;
    
    private boolean running = true;
    
    String light;
    
    private int from = 1;
    private int to = 2;
    private int type = 3;
    private int amount = 4;
        
    TrafficLock Lock;
    TrafficLights lights = new TrafficLights();
    
    public TrafficSystem()
    {
        lights = new TrafficLights();

        highestPrior = new TrafficLight("PlaceHolder highest");
        prevLight = new TrafficLight("placeHolder prev");
        prevLight.active = false;
        ThirdPrior.active = false;
        SecondPrior.active = false;
        
    }
   
    /*
    
    */
    public void MessageHandler(char[] message)
    {        
        /*
        Fiets , if (message[from] == O && message[to] == W) // Dit is noordkant van Oost naar West.
        Fiets , if (message[from] == N && message[to] == Z //Dit is west kant
        Fiets , if message[from] == Z && message[t] == N // Dit is oost
        */
        
        if(message[type] == 'T')
        {
            lights.Trein.changeAmount(message[amount]);
            highestPrior = lights.Trein;
        }
        
        if(message[type] == 'B')
        {
            lights.Bus.changeAmount(message[amount]);
            highestPrior = lights.Bus;
        }        
        else if(message[type] == 'F')
        {
           if(message[from] == 'O' && message[to] == 'W') // Dit is noordkant van Oost naar West.
                lights.FietsOW.changeAmount(message[amount]);
           
           if(message[from] == 'N' && message[to] == 'Z') //Dit is west kant
                lights.FietsNZ.changeAmount(message[amount]);
           
           if(message[from] == 'Z' && message[to] == 'N') // Dit is oost
                lights.FietsZN.changeAmount(message[amount]);
        }
        
        else if(message[type] == 'V')
        {
           if(message[from] == 'O' && message[to] == 'W')
                lights.FietsOW.changeAmount(message[amount]);
           
           if(message[from] == 'N' && message[to] == 'Z')
                lights.FietsNZ.changeAmount(message[amount]);
           
           if(message[from] == 'Z' && message[to] == 'N')
                lights.FietsZN.changeAmount(message[amount]);
        }
        
        
        if(message[type] == 'A')
        {
            if(message[from] == 'N')
            {
                if(message[to] == 'W')
                {   
                    lights.NW.changeAmount(message[amount]);
                }
                if(message[to] == 'O')
                {
                    lights.NO.changeAmount(message[amount]);
                }
                if(message[to] == 'Z')
                {   
                    lights.NZ.changeAmount(message[amount]);
                }
            }

            if(message[from] == 'O')
            {
                if(message[to] == 'N')
                {   
                    lights.ON.changeAmount(message[amount]);
                }
                if(message[to] == 'W')
                {   
                    lights.OW.changeAmount(message[amount]);
                }
            }

            if(message[from] == 'W')
            {
                if(message[to] == 'N')
                {  
                    lights.WN.changeAmount(message[amount]);
                }
                if(message[to] == 'Z')
                {   
                    lights.WZ.changeAmount(message[amount]);
                }
                if(message[to] == 'O')
                {   
                    lights.WO.changeAmount(message[amount]);
                }
            }

            if(message[from] == 'Z')
            {
                if(message[to] == 'W')
                {                               
                    lights.ZW.changeAmount(message[amount]);
                }
                if(message[to] == 'N' || message[2] == 'O')
                {   
                    lights.ZN.changeAmount(message[amount]);
                }
            }
        }                
    }
   
    public void HighestPriorLight()
    {
        if(highestPrior != null);
        {
            for (int i = 0; i < lights.All_Car_Lights.length; i++) {
                if(lights.All_Car_Lights[i].getAmount() > highestPrior.getAmount())
                    highestPrior = lights.All_Car_Lights[i];
            }            
            System.out.println(highestPrior.stoplight + " amount : " + highestPrior.getAmount());
        }      
    }
    
    /*
        In this method it will be decided which light is the second that will be turned on based on what is possible.
    */
    public void SecondLight(TrafficLight[] possibleLights)
    {
        SecondPrior = possibleLights[1];
        for (int i = 0; i < possibleLights.length; i++) 
        {
        System.out.println("This is possible with the First light : " + possibleLights[i].stoplight);
            if(possibleLights[i].amount > SecondPrior.amount)
            {
                String temp = possibleLights[i].stoplight;
                if(!SecondPrior.stoplight.equals(temp) && possibleLights[i].amount > 0)
                    SecondPrior = possibleLights[i];
            }
        }        
        System.out.println("Second is: " + SecondPrior.stoplight + "\n --------------------------------------------------- \n");
        SecondPrior.active = true;
        SecondPrior.resetAmount(); 
        ThirdLight(lights.CheckPossibilities(SecondPrior),possibleLights);
    }
    
    /*
        In this method it will be decided which light is the third light that turns to green with the previous two.
    */
    public void ThirdLight(TrafficLight[] possibleLights, TrafficLight[] possibleFromSecond)
    {
        System.out.println(possibleLights.length);
        for (int i = 0; i < possibleLights.length; i++) 
        {           
            for (int j = 0; j < possibleFromSecond.length; j++) {
                if(possibleLights[i].stoplight == possibleFromSecond[j].stoplight)
                {
                    System.out.println("This is possible with the Second: " + possibleLights[i].stoplight);                    
                    String temp = possibleLights[i].stoplight;
                     if(temp != ThirdPrior.stoplight && temp != highestPrior.stoplight && temp != SecondPrior.stoplight || possibleLights[i].amount > ThirdPrior.amount && possibleLights[i].amount > 0)
                            ThirdPrior = possibleLights[i];              
                }
            }
            //if(possibleLights[i].stoplight != highestPrior.stoplight && possibleLights[i].stoplight != SecondPrior.stoplight )
              //  ThirdPrior = (possibleLights[i].amount > ThirdPrior.amount ? possibleLights[i] : ThirdPrior);
        }    
        System.out.println("Thirdprior is: " + ThirdPrior.stoplight + "\n --------------------------------------------------- \n");
        
        ThirdPrior.active = true;
        ThirdPrior.resetAmount();
        ActivateLights();
    }
    
    public void ActivateLights()
    {
        highestPrior.StartTimer(0);
        SecondPrior.StartTimer(0.5f);        
        ThirdPrior.StartTimer(1);
    }
    
    public void NextLight()
    {
        
        if(highestPrior.amount > 0)
        {
            System.out.println("Next Light : " + highestPrior.stoplight); 
            highestPrior.active = true;    
            //CheckPossibilities(highestPrior);
            SecondLight(lights.CheckPossibilities(highestPrior));
            prevLight = highestPrior;
            prevLight.resetAmount();
        }
    }
    
    
    public void run()
    {  
        while(running)
        {   
            if(prevLight.checkActive() == false && SecondPrior.checkActive() == false && ThirdPrior.checkActive() == false)
            {                             
                NextLight(); 
                HighestPriorLight(); 
            }
            else
            {                
                
            }
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            Logger.getLogger(TrafficSystem.class.getName()).log(Level.SEVERE, null, ex);
        }            
        }
    }
}
