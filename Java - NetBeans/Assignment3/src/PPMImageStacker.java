package ce325.hw3;
import java.io.*;
import java.util.ArrayList;


public class PPMImageStacker{
    ArrayList<File> fileList;
    // Final, stacked image
    PPMImage img;
    
    public PPMImageStacker(File dir){
        // Create a new image stacker by saving the files in a list
        try{
            if (!dir.isDirectory()) {
               System.err.println("[ERROR] "+ dir.getName()+" is not a directory!");
               return;
            }
            
            if  (!dir.exists()) {
                System.err.println("[ERROR] Directory "+ dir.getName()+" does not exist!");
                return;
            }
        }
        catch(Exception e){
            System.err.println(e);
        }
        
        this.fileList=new ArrayList<File>();
        for (int i=0; i<dir.listFiles().length; i++) {
            this.fileList.add(dir.listFiles()[i]);
        }
    }
    
    public void stack() throws FileNotFoundException, UnsupportedFileFormatException{
        ArrayList<PPMImage> imageList = new ArrayList<PPMImage>();
        System.out.println(fileList.size());
        short[][] avgRed, avgGreen, avgBlue;
        if(fileList.size() > 0){
            this.img = new PPMImage(this.fileList.get(0));
            avgRed = new short[this.img.getHeight()][this.img.getWidth()];
            avgGreen = new short[this.img.getHeight()][this.img.getWidth()];
            avgBlue = new short[this.img.getHeight()][this.img.getWidth()];
        }else{
            throw new FileNotFoundException();
        }
        try{
            for(int i=1;i < fileList.size();i++){
            PPMImage tempImage = new PPMImage(fileList.get(i));
                for(int k = 0; k < tempImage.height; k++){
                    for(int l =0; l < tempImage.width; l++){
                        avgRed[k][l] += (short)(tempImage.pixelArray[k][l].getRed()/fileList.size());
                        avgGreen[k][l] += (short)(tempImage.pixelArray[k][l].getGreen()/fileList.size());
                        avgBlue[k][l] += (short)(tempImage.pixelArray[k][l].getBlue()/fileList.size());
                    }
                }            
            }
        }
        catch(FileNotFoundException e){
            System.err.println(e);
        }catch(UnsupportedFileFormatException e){
            System.err.println(e);
        }
        for(int k = 0; k < img.height; k++){
            for(int l =0; l < img.width; l++){
                img.pixelArray[k][l].setRed(avgRed[k][l]);
                img.pixelArray[k][l].setGreen(avgGreen[k][l]);
                img.pixelArray[k][l].setBlue(avgBlue[k][l]);
            }
        }
    }

    public PPMImage getStackedImage(){
        return img;
    }
}
