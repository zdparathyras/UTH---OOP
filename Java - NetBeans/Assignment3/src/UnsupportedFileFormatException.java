package ce325.hw3;


public class UnsupportedFileFormatException extends Exception{
    private String msg;
    
    public UnsupportedFileFormatException(){
    this.msg = "";
    }
    public UnsupportedFileFormatException(String msg){
        this.msg = msg;
    }

    @Override
    public String toString(){
        return "UnsupportedFileFormatException: [" + msg + "]" ;
    }

    
}
