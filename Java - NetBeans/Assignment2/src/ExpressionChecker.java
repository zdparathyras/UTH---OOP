package ce325.hw2;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpressionChecker {
    private String expression;
    
    //Αυτός είναι ο κατασκευαστής της κλάσσης και καλεί την μέθοδο fix.
    public ExpressionChecker(String expression) {
        this.expression=expression;
        fix();
    }
    
    //Αυτή η μέθοδος καλεί όλες τις μεθόδους ελέγχου της έκφρασης και επιστρέφει true μόνο όταν και όλες οι μέθοδοι που καλεί επιστρέψουν true.
    public boolean checkAll() {
        if (!checkParentheses()){
            return false;
        }
        if (!checkOther()){
            return false;
        }
        
        if (!checkOperators()){
            return false;
        }
        
        if (isEmpty()) {
            return false;
        }
        
        return true;
    }
    
    //Αυτή η μέθοδος τσεκάρει αν όλες οι παρενθέσεις που έχει η έκφραση κλείνουν.
    private boolean checkParentheses(){
        int count=0;
        Pattern r = Pattern.compile("[()]");
        Matcher m = r.matcher(this.expression);
        while(m.find()){
            if (expression.charAt(m.start())=='(') {
                count++;
            }
            else {
                count--;
            }
            if (count<0){
                return false;
                
            }
        }
        return count==0;
        
    }
    
    //Αυτή η μέθοδος τσεκάρει αν υπάρχει χαρακτήρας που δεν είναι αριθμός είτε τελεία είτε τελεστής.
    private boolean checkOther(){
        Pattern r = Pattern.compile("[^\\s\\d.+*\\-()^\\/x]");
        Matcher m = r.matcher(this.expression);
        if(m.find()){
            return false;
        }
        return true;     
    }
    
    private boolean isEmpty(){
        return expression.equals("");
    }
    
    private boolean checkOperators(){
        //Εδώ τσεκάρουμε αν υπάρχουν δύο τελεστές στη σειρά.
        Pattern r = Pattern.compile("[+\\-x*^\\/]{2,}");
        Matcher m = r.matcher(this.expression);
        if(m.find()){
            return false;
        }
        
        //Εδώ τσεκάρουμε αν υπάρχει τελεστής χωρίς να υπάρχει αριθμός πρίν απο αυτόν
        // ή αν υπάρχει τελεστής χωρίς να υπάρχει αριθμός μετά απο αυτόν.
        r = Pattern.compile("(^[+\\-*x\\/^]|[+\\-*x\\/^]$)");
        m = r.matcher(this.expression);
        if(m.find()){
            return false;
        }
        
        //Εδώ τσεκάρουμε αν υπάρχει αριθμός χωρίς τελεστή πρίν ή μετά απο μια παρένθεση.
        r = Pattern.compile("(\\d\\(|\\)\\d)");
        m = r.matcher(this.expression);
        if(m.find()){
            return false;
        }
        
        
        //Εδώ τσεκάρουμε αν υπάρχει αριθμός με τελεία αλλα όχι δεκαδικό μέρος (πχ 5. +3).
        r = Pattern.compile("(\\d\\.[\\(\\)+\\-*x^\\/]|[\\(\\)+\\-*x^\\/]\\.\\d)");
        m = r.matcher(this.expression);
        if(m.find()){
            return false;
        }
        
        //Εδώ τσεκάρουμε αν υπάρχει τελεία στην αρχή ή στο τέλος της έκφρασης (πχ .3+4 ή 4+6.)
        r = Pattern.compile("(^\\.|\\.$)");
        m = r.matcher(this.expression);
        if(m.find()){
            return false;
        }
        return true;
    }
    
    //Αυτή η μέθοδος αφαιρεί απο την δοθείσα έκφραση όλους τους χαρακτήρες whitespace.
    private void fix(){
        Pattern r = Pattern.compile("\\s");
        Matcher m = r.matcher(this.expression);
        this.expression=m.replaceAll("");
    }
    
    //Αυτή η μέθοδος επιστρέφει την διορθωμένη έκφραση.
    public String GetFixedExp(){
        return this.expression;
    }
    
}
