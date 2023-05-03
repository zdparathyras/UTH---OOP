package ce325.hw3;

public class RGBPixel {
   private int color;
    
    public short getRed() {
        short returnColor = (short) ((color >> 16));
        return  (returnColor);
    }
    
    public short getGreen() {
        short returnColor = (short) (((color & 65280) >> 8));
        return  (returnColor);
    }
    
    public short getBlue() {
        return (short) (color & 255);

    }
    
    public void setRed(short red) {
        this.color= (~(255<<16))&this.color;
        this.color=this.color|(red<<16);
    }
    
    public void setGreen(short green) {
        this.color= (~(255<<8))&this.color;
        this.color=this.color|(green<<8);
    }
    
    public void setBlue(short blue) {
        this.color=(~(255))&this.color;
        this.color=this.color|(blue);
    }
    
    public RGBPixel(short red, short green, short blue) {
        this.color = (red<<16) + (green<<8) + blue;
    }
    
    public RGBPixel(RGBPixel pixel) {
        this.color=pixel.color; 
    }
    
    public RGBPixel(YUVPixel pixel){
        short C = (short)(pixel.getY() - 16);
        short D = (short)(pixel.getU() - 128);
        short E = (short)(pixel.getV() - 128);
        short red = (short)(clip(( 298 * C + 409 * E + 128) >> 8));
        short green = (short)(clip(( 298 * C - 100 * D - 208 * E + 128) >> 8));
        short blue = (short)(clip(clip(( 298 * C + 516 * D + 128) >> 8)));
        this.setRGB(red, green, blue);
    }
    
    public String toString(){
        return "(" + this.getRed() + "," + this.getGreen() + "," + this.getBlue() + ")";
    }
    
    public int getRGB(){
        return this.color;
    }
    
    public void setRGB(int value){
        this.color = value;
    }
    
    final void setRGB(short red, short green, short blue){
        this.setRed(red);
        this.setGreen(green);
        this.setBlue(blue);
    }
    
    int clip(int num){
        if(num > 255){
            return 255;
        }
        if(num < 0){
            return 0;
        }
        return num;
    }
    
}
