/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package softwaredevserver;

/**
 *
 * @author Nefaratus
 */
public class TrafficLights {
   
        //North
    TrafficLight NW = new TrafficLight("NW");
    TrafficLight NO = new TrafficLight("NO");
    TrafficLight NZ = new TrafficLight("NZ");

    //East
    TrafficLight ON = new TrafficLight("ON");
    TrafficLight OW = new TrafficLight("OW");

    //West
    TrafficLight WN = new TrafficLight("WN"); 
    TrafficLight WZ = new TrafficLight("WZ");
    TrafficLight WO = new TrafficLight("WO");

    //South
    TrafficLight ZW = new TrafficLight("ZW");
    TrafficLight ZN = new TrafficLight("ZN");
   
    TrafficLight Trein = new TrafficLight("T");
    TrafficLight Bus = new TrafficLight("OW");
    TrafficLight FietsNZ = new TrafficLight("NZ");
    TrafficLight FietsZN = new TrafficLight("ZN");
    TrafficLight FietsOW = new TrafficLight("OW");
    
   
    TrafficLight[] All_Car_Lights = new TrafficLight[]{NW,NO,NZ,ON,OW,WZ,WO,ZW,ZN};
    
    public TrafficLights()
    {        
        InitLights();
    }
    
     public void InitLights()
    {
        //Auto's    
        NW.setType('A');
        NO.setType('A');
        NZ.setType('A');

        ON.setType('A');
        OW.setType('A');

        WN.setType('A');
        WZ.setType('A');
        WO.setType('A');

        ZW.setType('A');
        ZN.setType('A');

        // Trein
        Trein.setType('T');

        //Bus
        Bus.setType('B');

        //Fietsers
        FietsNZ.setType('F');
        FietsZN.setType('F');
        FietsOW.setType('F');
    }
     
        
    public TrafficLight[] CheckPossibilities(TrafficLight c_Light)
    {
        TrafficLight[] possibleLights;
        /*
        Mogelijke opties
            NW -> NO NZ ON OZ OW ZNO WN WO WZ
            NZ -> NO NW ON NZ OZ 
            NO -> NZ NW ON WZ
        
            ZNO -> NZ NW ZW WZ
            ZW -> ZNO ON WZ
            
            ON -> NO NZ BW OW ZW WO WZ
            OW -> ON WO WZ
        
            WN -> NW WO WZ
            WO -> ON NW OW WN WZ
            WZ -> NO NW ON OW ZNO ZW WN WO
        */
        if(c_Light.getType() == 'B')
        {
            /* Bus */ 
            if(c_Light.stoplight == "OW" && c_Light.getType() == 'B')
            {
                possibleLights = new TrafficLight[]{WO,WZ};
                return possibleLights;
            }
        }
        
       if(c_Light.getType() == 'A')
          {
            /* Car Part */ 
            if(c_Light.stoplight == "NW")
            {
              possibleLights = new TrafficLight[]{NO,NZ,ON,ZN,WN,WO,WZ};                     
              return possibleLights;
            }
            if(c_Light.stoplight == "NZ")
            {
              possibleLights = new TrafficLight[]{NO,NW,ON,ZN};                     
              return possibleLights;
            }
            if(c_Light.stoplight == "NO")
            {
              possibleLights = new TrafficLight[]{NZ, NW, ON, WZ};                     
              return possibleLights;
            }
            if(c_Light.stoplight == "ZN")
            {
              possibleLights = new TrafficLight[]{NZ, NW, ZW, WZ};                    
              return possibleLights; 
            }
            if(c_Light.stoplight == "ZW")
            {
              possibleLights = new TrafficLight[]{ZN, ON, WZ};                     
              return possibleLights;
            }
            if(c_Light.stoplight == "ON")
            {
              possibleLights = new TrafficLight[]{NO, NZ, NW, OW, ZW, WO, WZ};                    
              return possibleLights;
            }
            if(c_Light.stoplight == "OW")
            {
              possibleLights = new TrafficLight[]{ON, WO, WZ};                     
              return possibleLights;
            }
            if(c_Light.stoplight == "WN")
            {
              possibleLights = new TrafficLight[]{NW, WO, WZ};                     
              return possibleLights;
            }
            if(c_Light.stoplight == "WO")
            {
              possibleLights = new TrafficLight[]{ON, NW, OW, WN, WZ};                    
              return possibleLights;
            }
            if(c_Light.stoplight == "WZ")
            {
              possibleLights = new TrafficLight[]{NO, NW, ON, OW, ZN, ZW, WN, WO};                    
              return possibleLights;
            }
        }
        
        /* Bike Part */ 
                
        if(c_Light.stoplight == "NZ" && c_Light.getType() == 'F')
        {
            possibleLights = new TrafficLight[]{NO,NZ,ZN,ON,FietsZN,FietsOW};
            return possibleLights;
        }
        
        if(c_Light.stoplight == "ZN" && c_Light.getType() == 'F')
        {
            possibleLights = new TrafficLight[]{NW,NZ,ZN,WN,WZ,ZW,FietsOW,FietsNZ};
            return possibleLights;
        }
        
        if(c_Light.stoplight == "OW" && c_Light.getType() == 'F')
        {
            possibleLights = new TrafficLight[]{OW,WO,WZ,ZW,FietsNZ,FietsZN};
            return possibleLights;            
        }
        return null;
    }
    
}
