/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ce325.hw1;

/**
 *
 * @author dam
 */
public class RandomGenerator {
    private java.util.Random rand;
    
    public RandomGenerator(long seed) {
      if(seed!=0)
        seed = 12345678L;
      rand = new java.util.Random(seed);
    }    
      
    public double getDouble() {
      int value = (int)((rand.nextDouble()-0.5) * rand.nextInt(20) * 100);
      return value/100.0;
    }        
}
