/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softwaredevserver;

/**
 *
 * @author Jan
 */
public class TrafficLock{
    
    public boolean lock = true;    
    
    public void Lock()
    {
        if(lock == true)
            lock = false;
           
    }
    
    public void Release()
    {
        lock = true;
    }
    
}
