package ce325.hw3;
import java.io.*;

public class Histogram {
    YUVImage img;
    int brightness[];
    
    public Histogram(YUVImage img){
        this.img=img;
        brightness=new int[256];
        for (int i=0; i<this.img.height; i++){
            for (int j=0; j<this.img.width; j++) {
                brightness[this.img.pixelArray[i][j].getY()]+=1;
            }
        }
    }
    
    public String toString(){
        String str="";
        for (int i=0; i<256; i++){
            str+= i;
            for (int j=0; j<this.brightness[i] && j<=80; j++){
                str+="*";
            }
            str+="\n";
        }
        return str;
    }
    
    public void toFile(File file){
      try {
          java.io.PrintWriter pfile = new java.io.PrintWriter(file);
          pfile.println(this.toString());
          pfile.close();
        }
        catch(Exception ex) {
            System.err.println(ex);
            System.err.println("Unable to write to file!!!");
        }  
    }
    
    public void equalize(){
        double[] probality=new double[256];
        double[] cumulative=new double[256];
        
        
        for (int i=1; i<256; i++){
          probality[i]= (1.0* brightness[i])/(this.img.width*this.img.height);
        }
        
        cumulative[0]=probality[0];
        for (int i=1; i<256; i++){
          cumulative[i]=cumulative[i-1] + probality[i];
        }
        
        for(int i =0;i <256; i++){
            cumulative[i] = cumulative[i]*235;
        } 
        
        for(int i =0;i < brightness.length; i++){
            brightness[i] = (int)cumulative[i];
        }  
    }
    
    public short getEqualizedLuminocity(int luminocity){
        return (short)brightness[luminocity];
    }
}
