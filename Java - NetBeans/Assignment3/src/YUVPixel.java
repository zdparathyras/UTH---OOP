package ce325.hw3;

public class YUVPixel {
    private int color;
    
    public YUVPixel(short Y, short U, short V){
        this.color = (Y<<16)+(U<<8)+V;
    }
    public YUVPixel(YUVPixel pixel){
        this.color = pixel.color;
    }
    public YUVPixel(RGBPixel pixel){
        short Y = (short)(( (  66 * pixel.getRed() + 129 * pixel.getGreen() +  25 * pixel.getBlue() + 128) >> 8) +  16);
        short U = (short)(( ( -38 * pixel.getRed() -  74 * pixel.getGreen() + 112 * pixel.getBlue() + 128) >> 8) + 128);
        short V = (short)(( ( 112 * pixel.getRed() -  94 * pixel.getGreen() -  18 * pixel.getBlue() + 128) >> 8) + 128);
        this.color = (Y<<16)+(U<<8)+V;
    }
    
    public short getY(){
        short returnColor = (short) ((color >> 16));
        return  (returnColor);
    }
    
    public short getU(){
        short returnColor = (short) (((color & 65280) >> 8));
        return (returnColor);
    }
    
    public short getV(){
        return (short) (color & 255);
    }
    
    public void setY(short Y){
        this.color= (~(255<<16))&this.color;
        this.color= this.color|(Y<<16);
    }
    
    public void setU(short U){
        this.color= (~(255<<8))&this.color;
        this.color= this.color|(U<<8);
    }
    
    public void setV(short V){
        this.color= (~(255))&this.color;
        this.color= this.color|(V);
    }
    
}
