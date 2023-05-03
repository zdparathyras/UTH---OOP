package ce325.hw2;

public class ArithmeticCalculator {
     
    //Αυτή η μέθοδος διαβάζει την είσοδο του χρήστη απο την κονσόλα ,την αποθηκεύει σε ένα String και την επιστρέφει.
    public static String getexpression() {
        java.util.Scanner sc = new java.util.Scanner(System.in);
        System.out.print("Enter math expression: ");
        String line = sc.nextLine();
        return line;
    }
    
    //Αυτή η μέθοδος είναι υπεύθυνη για την δημιουργία των αρχείων dot και png.
    public static void createDotAndPng(Tree myTree) {
        try {        
          java.io.PrintWriter pfile = new java.io.PrintWriter("ArithmeticExpression.dot");
          pfile.println(myTree.toDotString());
          pfile.close();
          System.out.println("PRINT DOT FILE OK!");

          Process p = Runtime.getRuntime().exec("dot -Tpng ArithmeticExpression.dot + -o ArithmeticExpression.png");
          p.waitFor();
          System.out.println("PRINT PNG FILE OK!");
        } 
        catch(Exception ex) {
          System.err.println("Unable to write dotString!!!");
          ex.printStackTrace();
          System.exit(1);
        }
    }
    
    public static void main(String []args){
       String expression= getexpression();
       
       Tree myTree= new Tree(expression);
       if (myTree.isValid()) {
        System.out.println("Equivalent expression: "+ myTree.toString());
        System.out.println("Result= "+ myTree.calculate());
        createDotAndPng(myTree);
       }
    }
}

