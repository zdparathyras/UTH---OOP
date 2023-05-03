package ce325.hw3;

import java.lang.reflect.Array;

public class RGBImage implements Image{
    static int MAX_COLORDEPTH = 255;
    RGBPixel pixelArray[][];
    int width;
    int height;
    int colorDepth;
    
    public void setPixelArray(RGBPixel[][] pixelArray) {
        this.pixelArray = pixelArray;
    }
    
    @Override
    public void grayscale() {
        for (int i=0; i<pixelArray.length; i++) {
            for (int j=0; j<pixelArray[i].length; j++) {
                short newValue = (short)((pixelArray[i][j].getRed()*0.3) + (pixelArray[i][j].getGreen()*0.59) +  (pixelArray[i][j].getBlue()*0.11));
                pixelArray[i][j].setRGB(newValue,newValue,newValue);
            }
        }
        
    }

    @Override
    public void doublesize() {
        RGBPixel newArray[][]=new RGBPixel[2*height][2*width];
        
        for (int i=0; i<pixelArray.length; i++) {
            for (int j=0; j<pixelArray[i].length; j++) {
                newArray[2*i][2*j]=pixelArray[i][j];
                newArray[2*i+1][2*j]=pixelArray[i][j];
                newArray[2*i][2*j+1]=pixelArray[i][j];
                newArray[2*i+1][2*j+1]=pixelArray[i][j];
            }
        }
        
        this.pixelArray=newArray;
        this.height=2*height;
        this.width=2*width;
    }

    @Override
    public void halfsize() {
        RGBPixel newArray[][]=new RGBPixel[height/2][width/2];
        short tempRed=0, tempGreen=0, tempBlue=0;
        
        for (int i=0; i<newArray.length; i++) {
            for (int j=0; j<newArray[i].length; j++) {
                tempRed=0;
                tempGreen=0;
                tempBlue=0;
                
                if (2*i<=height && 2*j<=width) {
                    tempRed+=pixelArray[2*i][2*j].getRed();
                    tempGreen+=pixelArray[2*i][2*j].getGreen();
                    tempBlue+=pixelArray[2*i][2*j].getBlue();
                }
                
                if (2*i+1<=height && 2*j<=width) {
                    tempRed+=pixelArray[2*i+1][2*j].getRed();
                    tempGreen+=pixelArray[2*i+1][2*j].getGreen();
                    tempBlue+=pixelArray[2*i+1][2*j].getBlue();
                }
                
                if (2*i<=height && 2*j+1<=width) {
                    tempRed+=pixelArray[2*i][2*j+1].getRed();
                    tempGreen+=pixelArray[2*i][2*j+1].getGreen();
                    tempBlue+=pixelArray[2*i][2*j+1].getBlue();
                }
                
                if (2*i+1<=height && 2*j+1<=width) {
                    tempRed+=pixelArray[2*i+1][2*j+1].getRed();
                    tempGreen+=pixelArray[2*i+1][2*j+1].getGreen();
                    tempBlue+=pixelArray[2*i+1][2*j+1].getBlue();
                }
                
                newArray[i][j]=new RGBPixel( (short)(tempRed/4),(short)(tempGreen/4), (short)(tempBlue/4));
            }
        }
        
        this.pixelArray=newArray;
        this.height=height/2;
        this.width=width/2;
    }

    @Override
    public void rotateClockwise() {
        
        RGBPixel newArray[][]=new RGBPixel[width][height];
        
        for (int j=0; j<width; j++) {
            for (int i=0; i<height; i++) {
                newArray[j][i]=pixelArray[height - i - 1][j];
            }
        }
        
        int tempWidth=width;
        this.pixelArray=newArray;
        this.width=height;
        this.height=tempWidth;
    }
    
    public RGBImage(int width, int height, int colordepth){
        this.width=width;
        this.height=height;
        this.colorDepth = colordepth;
        this.pixelArray=new RGBPixel[height][width];
    }
    
    public RGBImage(RGBImage copyImg) {
        this.height=copyImg.height;
        this.width=copyImg.width;
        this.colorDepth = copyImg.colorDepth;
        this.pixelArray=copyImg.pixelArray.clone();
    }
    public int getWidth(){
        return this.width;
    }
    
    public RGBImage(YUVImage copyImg){
        this.width = copyImg.width;
        this.height = copyImg.height;
        this.pixelArray = new RGBPixel[height][width];
        this.colorDepth = 255;
         for(int i = 0;i < height; i++){
            for(int j = 0; j < width; j++){
              this.pixelArray[i][j] = new RGBPixel(copyImg.pixelArray[i][j]);
            }
        }
    }
    
    public int getHeight(){
        return this.height;
    }
    
    public int getColorDepth(){
        return this.colorDepth;
    }
    protected void resize(int newWidth, int newHeight){
        this.width=newWidth;
        this.height=newHeight;
        this.pixelArray = new RGBPixel[newHeight][newWidth];
    }
    public RGBPixel getPixel(int row, int col){
        return pixelArray[row][col];
    }
    public void setPixel(int row, int col, RGBPixel pixel){
        this.pixelArray[row][col] = pixel;
    }
}
