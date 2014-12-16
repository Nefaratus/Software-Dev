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
    TrafficLight FietsLight = new TrafficLight("placeHolder");
    
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
        Here the incoming message will be sorted and the amount that needs to be changed will be changed.
    */
    public void MessageHandler(char[] message)
    {        
        /*
        Fiets , if (message[from] == O && message[to] == W) // Dit is noordkant van Oost naar West.
        Fiets , if (message[from] == N && message[to] == Z //Dit is west kant
        Fiets , if message[from] == Z && message[t] == N // Dit is oost
        */
        
        //Trein
        if(message[type] == 'T')
        {
            lights.Trein.changeAmount(message[amount]);
        }
        
        //Bus
        if(message[type] == 'B')
        {
            System.out.println(message);
            lights.Bus.changeAmount(message[amount]);
        }    
        //Fiets
        else if(message[type] == 'F')
        {
            System.out.println(message);
           if(message[from] == 'O' && message[to] == 'W') // Dit is noordkant van Oost naar West.
                lights.FietsOW.changeAmount(message[amount]);
           
           if(message[from] == 'N' && message[to] == 'Z') //Dit is west kant
                lights.FietsNZ.changeAmount(message[amount]);
           
           if(message[from] == 'Z' && message[to] == 'N') // Dit is oost
                lights.FietsZN.changeAmount(message[amount]);
        }
        //Voetganger
        else if(message[type] == 'V')
        {
           if(message[from] == 'O' && message[to] == 'W')
                lights.FietsOW.changeAmount(message[amount]);
           
           if(message[from] == 'N' && message[to] == 'Z')
                lights.FietsNZ.changeAmount(message[amount]);
           
           if(message[from] == 'Z' && message[to] == 'N')
                lights.FietsZN.changeAmount(message[amount]);
        }
        
        //Auto
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
            if(lights.Bus.getAmount() != 0)
            {
                highestPrior = lights.Bus;
                lights.Bus.resetAmount();
            }
            else
            {
                for (int i = 0; i < lights.All_Car_Lights.length; i++) 
                {       
                    if((lights.All_Car_Lights[i].stoplight == "WN" || lights.All_Car_Lights[i].stoplight == "ZW") && lights.All_Car_Lights[i].getAmount() >= 2)
                    {
                        System.out.println("Been here with " +  lights.All_Car_Lights[i].stoplight );
                        lights.All_Car_Lights[i].changeAmount(1);
                    }
                    if(lights.All_Car_Lights[i].getAmount() > highestPrior.getAmount())
                        highestPrior = lights.All_Car_Lights[i];
                }
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
        System.out.println("This is possible with the First light : " + possibleLights[i].stoplight + possibleLights[i].getType());
            if(possibleLights[i].amount > SecondPrior.amount)
            {
                String temp = possibleLights[i].stoplight;
                if(!SecondPrior.stoplight.equals(temp) && possibleLights[i].amount > 0)
                    SecondPrior = possibleLights[i];
            }
        }        
        System.out.println("Second is: " + SecondPrior.stoplight + SecondPrior.getType()+ "\n --------------------------------------------------- \n");
        SecondPrior.active = true;
        SecondPrior.resetAmount(); 
        ThirdLight(lights.CheckPossibilities(SecondPrior),possibleLights);
    }
    
    /*
        In this method it will be decided which light is the third light that turns to green with the previous two.
    */
    public void ThirdLight(TrafficLight[] possibleLights, TrafficLight[] possibleInSecond)
    {
        System.out.println(possibleLights.length);
        for (int i = 0; i < possibleLights.length; i++) 
        {           
            for (int j = 0; j < possibleInSecond.length; j++) {
                if(possibleLights[i].stoplight == possibleInSecond[j].stoplight && possibleLights[i].getType() == possibleInSecond[j].getType())
                {
                    System.out.println("This is possible with the Second: " + possibleLights[i].stoplight + possibleLights[i].getType());                    
                    String temp = possibleLights[i].stoplight;
                     if(temp != ThirdPrior.stoplight && temp != highestPrior.stoplight && temp != SecondPrior.stoplight || possibleLights[i].amount > ThirdPrior.amount && possibleLights[i].amount > 0)
                            ThirdPrior = possibleLights[i];              
                }
            }
        }    
        System.out.println("Thirdprior is: " + ThirdPrior.stoplight + ThirdPrior.getType() + "\n --------------------------------------------------- \n");
        ThirdPrior.active = true;
        ThirdPrior.resetAmount();
        CheckFiets(lights.CheckPossibilities(ThirdPrior),possibleInSecond,possibleLights);
    }
    
    public void CheckFiets(TrafficLight[] possibleLights, TrafficLight[] possibleInSecond,TrafficLight[] possibleInThird)
    {
        for (int i = 0; i < possibleLights.length; i++) {
            for (int j = 0; j < possibleInSecond.length; j++) {
                for (int k = 0; k < possibleInThird.length; k++) {    
                    
                    if(possibleLights[i].getType() == 'F' && possibleInSecond[j].getType() == 'F' && possibleInThird[k].getType() == 'F')
                    {
                        if((possibleLights[i].stoplight == possibleInSecond[j].stoplight) && (possibleLights[i].stoplight == possibleInThird[k].stoplight) && possibleLights[i].getType() == 'F')
                        {
                          String temp = possibleLights[i].stoplight;
                          if(FietsLight.stoplight != temp)
                          {                              
                            System.out.println("\n ----------------------------------------------------------------- \n");
                            System.out.println("This is possible Bike Light: " + possibleLights[i].stoplight);  
                            FietsLight = possibleLights[i];
                            FietsLight.active = true;
                            FietsLight.resetAmount();
                            FietsLight.StartTimer(0);
                        }
                      }
                    }
               }
            }   
        }        
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
