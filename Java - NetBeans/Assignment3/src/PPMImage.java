package ce325.hw3;
import java.io.*;

public class PPMImage extends RGBImage {
  
    public PPMImage(File file) throws FileNotFoundException, UnsupportedFileFormatException{
        super(0,0,255);
        if(!file.exists()){
           throw new FileNotFoundException();
       }
       if(!this.getFileExtension(file.getName()).toLowerCase().equals("ppm")){
           throw new UnsupportedFileFormatException();
       }
              
        try {
            FileInputStream fis = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            fis.read(data);
            String [] fileData = new String(data).split("\\s");
            if (!fileData[0].equals("P3")) {throw new UnsupportedFileFormatException();}
            super.resize(Integer.parseInt(fileData[1]), Integer.parseInt(fileData[2]));
            int widthCount = 0;
            int heightCount = 0;
            for(int i = 4; i< fileData.length; i = i + 3){
                pixelArray[heightCount][widthCount]= new RGBPixel(Short.parseShort(fileData[i]),Short.parseShort(fileData[i+1]),Short.parseShort(fileData[i+2]));
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
    
    public PPMImage(RGBImage img){
        super(img);
    }
    
    public PPMImage(YUVImage img){
        super(img);
    }
    private String getFileExtension(String file){
        int i = file.lastIndexOf('.');
        return i > 0 ? file.substring(i + 1) : "";
    }
    
    public String toString(){
        String str = "P3\n";
        str += this.width + " " + this.height + "\n255\n";
        for(int iHeight = 0; iHeight < this.height; iHeight++ )
        {
            for(int iWidth = 0; iWidth < this.width; iWidth++){
                str += pixelArray[iHeight][iWidth].getRed() + " " + pixelArray[iHeight][iWidth].getGreen() + " " + pixelArray[iHeight][iWidth].getBlue() + "\n";
            }
        }
        return str;
    }
    
    public void toFile(File file){
        try {        
            java.io.PrintWriter pfile = new java.io.PrintWriter(file);
            pfile.println("P3");
            pfile.println(this.width + " " + this.height);
            pfile.println("255");
            for(int iHeight = 0; iHeight < this.height; iHeight++ ){
                for(int iWidth = 0; iWidth < this.width; iWidth++){
                    pfile.println(pixelArray[iHeight][iWidth].getRed() + " " + pixelArray[iHeight][iWidth].getGreen() + " " + pixelArray[iHeight][iWidth].getBlue());
                }
            }
            pfile.close();
            System.out.println("PRINT ppm FILE OK!");
        }
        catch(Exception ex) {
          System.err.println("Unable to write to file!!!");
          //ex.printStackTrace();
          System.exit(1);
        }
    }
}
