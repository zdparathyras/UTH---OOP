package ce325.hw3;
import java.io.*;

public class YUVImage {
    YUVPixel pixelArray[][];
    int width;
    int height;
    
    public YUVImage(int width, int height){
        this.width = width;
        this.height = height;
        this.pixelArray = new YUVPixel[height][width];
        for(int i = 0;i < height; i++){
            for(int j = 0; j < height; j++){
                this.pixelArray[i][j] = new YUVPixel((short)16,(short)128,(short)128);
            }
        }
    }
    
    public YUVImage(YUVImage copyImg){
        this.height = copyImg.height;
        this.width = copyImg.width;
        this.pixelArray = copyImg.pixelArray.clone();
    }
    
    public YUVImage(RGBImage RGBImg){
        this.height = RGBImg.height;
        this.width = RGBImg.width;
        this.pixelArray = new YUVPixel[height][width];
        for(int i = 0;i < height; i++){
            for(int j = 0; j < width; j++){
              this.pixelArray[i][j] = new YUVPixel(RGBImg.pixelArray[i][j]);
            }
        }
    }
    
    public YUVImage(java.io.File file) throws FileNotFoundException, UnsupportedFileFormatException{
        if(!file.exists()){
           throw new FileNotFoundException();
        }
     
        if(!this.getFileExtension(file.getName()).toLowerCase().equals("yuv")){
               throw new UnsupportedFileFormatException();
        }

        try {
            FileInputStream fis = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            fis.read(data);
            String [] fileData = new String(data).split("\\s");
            if (!fileData[0].equals("YUV3")) {throw new UnsupportedFileFormatException();}
            this.width = Integer.parseInt(fileData[1]);
            this.height = Integer.parseInt(fileData[2]);
            int widthCount = 0;
            int heightCount = 0;
            this.pixelArray = new YUVPixel[height][width];
            for(int i = 3; i< fileData.length; i = i + 3){
                pixelArray[heightCount][widthCount]= new YUVPixel(Short.parseShort(fileData[i]),Short.parseShort(fileData[i+1]),Short.parseShort(fileData[i+2]));
                if( widthCount == width-1){
                    heightCount++;
                    widthCount=0;
                }else{
                    widthCount++;
                }
            }
        }
        catch(Exception e) {
            System.out.println(e);
            System.out.println("sto catch");
        }
    }
    
    private String getFileExtension(String file){
        int i = file.lastIndexOf('.');
        return i > 0 ? file.substring(i + 1) : "";
    }
    
    public void toFile(File file){
        try {
            java.io.PrintWriter pfile = new java.io.PrintWriter(file);
            pfile.println("YUV3");
            pfile.println(this.width + " " + this.height);
            for(int iHeight = 0; iHeight < this.height; iHeight++ ){
                for(int iWidth = 0; iWidth < this.width; iWidth++){
                    pfile.println(this.pixelArray[iHeight][iWidth].getY() + " " + this.pixelArray[iHeight][iWidth].getU() + " " + this.pixelArray[iHeight][iWidth].getV());
                }
            }
            pfile.close();
        }
        catch(Exception ex) {
            System.err.println(ex);
            System.err.println("Unable to write to file!!!");
        }
    }
    
    public String toString(){
        String str = "YUV3\n";
        str += this.width + " " + this.height;
        for(int iHeight = 0; iHeight < this.height; iHeight++ )
        {
            for(int iWidth = 0; iWidth < this.width; iWidth++){
                str += pixelArray[iHeight][iWidth].getY() + " " + pixelArray[iHeight][iWidth].getU() + " " + pixelArray[iHeight][iWidth].getV() + "\n";
            }
        }
        return str;
    }
    
    public void equalize(){
        Histogram hist = new Histogram(this);
        hist.equalize();
        for(int i = 0;i < this.height; i++){
            for(int j = 0;j < this.width; j++){
                try {
                    this.pixelArray[i][j].setY(hist.getEqualizedLuminocity(this.pixelArray[i][j].getY()));

                } catch (Exception e) {
                    System.out.println(this.pixelArray[i][j].getY());
                }
            }
        }
    }
}
