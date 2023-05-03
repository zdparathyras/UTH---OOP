package ce325.hw2;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Stack;

public class Tree {
    private Node root;
    private boolean valid;
     
    //Αυτή η μέθοδος δέχεται σαν όρισμα ένα χαρακτήρα και εξετάζει αν αυτός είναι ένας απο τους προκαθορισμένους αριθμητικούς τελεστές.
    //Αν είναι τότε επιστρέφει έναν ακέραιο (μεγαλύτερο του μηδενός) σύμφωνα με την προτεραιότητα του στους αριθμητικούς υπολογισμούς.
    //Αν δεν είναι τελεστής και είναι οποιοσδήποτε άλλος χαρακτήρας επιστρέφει τον ακέραιο μηδέν.
    private int getOperatorPriority(char str) {
        switch(str) {
            case '+': return 1;
            case '-': return 1;
            case 'x': return 2;
            case '*': return 2;
            case '/': return 2;
            case '^': return 3;
            default: return 0;    
        }
    }
   
    //Αυτή η μέθοδος δέχεται σαν όρισμα ένα String και ψάχνει μέσα σε αυτό να βρεί τον δεξιότερο τελεστή με την χαμηλότερη προτεραιότητα 
    //που βρίσκεται εκτός παρενθέσεων, καλώντας την παραπάνω μέθοδο (getOperatorPriority).
    //Αφού τον βρεί αποθηκεύει τον ίδιο καθώς και την θέση στην οποία βρίσκεται σε έναν πίνακα String δύο θέσεων τον οποίο και επιστρέφει.
    private String[] getFirstOp (String expression) {
        char current_op ='a';
        int index=0;
        int counter=0;
        int priority=100;
        
        for (int i=0; i<expression.length(); i++) {
            if (expression.charAt(i)=='(') {
                counter++;
            }
            if (expression.charAt(i)==')') {
                counter--;
            }
            
            //Όταν η τιμή της μεταβλητής counter είναι ίση μηδέν σημαίνει ότι όσες παρενθέσεις έχουν ανοίξει τόσες έχουν κλείσει 
            //δηλαδή βρισκόμαστε εκτός παρενθέσεων.
            if (counter==0){
                if (getOperatorPriority(expression.charAt(i))==0) continue;
                if (getOperatorPriority(expression.charAt(i))<=priority) {
                    current_op=expression.charAt(i);
                    index=i;
                    priority=getOperatorPriority(expression.charAt(i));
                }
            }
        }
        String[] pinakas= {Character.toString(current_op), Integer.toString(index)};
        return pinakas;
    }
    
    //Αυτή η μέθοδος είναι αναδρομική και δημιουργεί ένα δένδρο χρησιμοποιώντας ως όρισμα ένα αντικείμενο της κλάσσης Node (δηλαδή ένα κόμβο δένδρου) απο τον οποίο 
    //δημιουργεί τα παιδιά του.
    //Αρχικά τσεκάρει αν υπάρχει παρένθεση που ανοίγει στην αρχή και κλείνει στο τέλος της έκφρασης της οποίας τα σύμβολα αφερεί.
    private void generateTree(Node n) {
        if (n.value.length() > 1 && n.value.charAt(0)=='(' && n.value.charAt(n.value.length()-1)==')') {
            boolean flag = false;
            int counter = 0;
            for(int i =0; i <n.value.length(); i++){
                if(n.value.charAt(i) == '('){
                    counter++;
                }else if(n.value.charAt(i) == ')'){
                    counter--;
                }else if(counter == 0 && getOperatorPriority(n.value.charAt(i)) > 0){
                    flag = true;
                }
            }
            if(!flag){
                n.value=n.value.substring(1,n.value.length()-1);
            }
        }
        String op[] = getFirstOp(n.value);
        
        if (!op[0].equals("a")) {
            n.left= new Node(n,n.value.substring(0,Integer.parseInt(op[1])));
            n.right= new Node(n,n.value.substring(Integer.parseInt(op[1])+1));
            n.value = op[0];
            
            generateTree(n.left);
            generateTree(n.right);
        }
        else {
            return;
        }
    }
    
    //Αυτός είναι ο κατασκευαστής της κλάσσης Tree που καλέι την μέθοδο generateTree για την δημιουργία του δένδρου.
    public Tree(String expression) {
       ExpressionChecker check = new ExpressionChecker(expression);
        if(check.checkAll()){
            expression = check.GetFixedExp();
            root=new Node(null, expression);
            generateTree(root);
            this.valid=true;
       }
        else {
            System.out.println("Wrong expresssion!");
            this.valid=false;
        }
    }

    
    //Αυτή η μέθοδος κατασκευάζει ένα String το οποίο χρησιμοποιούμε για την έμφανιση του δένδρου στην εφαρμογή graphviz.
    //Χρησιμοποιεί ένα Stack στο οποίο προσθέτουμε τα στοιχεία του δένδρου που έχουμε κατασκευάσει πρώτα απο το αριστερότερο παιδί και μετά προς τα δεξιά.
    //Δηλαδή με την διάταξη InOrder που μάθαμε στο μάθημα Δομές Δεδομένων.
    //Μόλις βρούμε το αριστερότερο παιδί προσθέτουμε τις τιμές αυτού και όσων υπήρχαν πρίν απο αυτό σε ένα String και το αφαιρούμε απο το Stack, μετά
    //προσθέτουμε στο String και την τιμή του αδερφού του.
    public String toDotString(){
       if (!valid) {
           return null;
       }
        
       Stack<Node> InOrderStack = new Stack<Node>();
       Node current = this.root;
       String str= new String();
        str = str + "digraph ArithmeticExpressionTree {\n";
        str = str + "fontcolor=\"navy\";\n";
        str = str + "fontsize=20;\n";
        str = str + "labelloc=\"t\";\n";
        str=  str + "label=\"Arithmetic Expression\"\n";
        
        while (current!=null || !InOrderStack.empty()) {
          while (current!=null) {
            InOrderStack.push(current);
            str += current.hashCode() +  "[label=\" " + current.value + "\", shape=circle, color=black]\n";
            if (current.left !=null) {
                str += current.hashCode() + " -> " + current.left.hashCode() +"\n";
            }
            current = current.left;
          }
          current = InOrderStack.pop();
          str += current.hashCode() +  "[label=\" " + current.value + "\", shape=circle, color=black]\n";
          if (current.right !=null) {
                str += current.hashCode() + " -> " + current.right.hashCode() +"\n";
          }
          current=current.right;
        }
        str=str + "}";
        return str;
    }
    
    //Αυτή η μέθοδος παίρνει ώς όρισμα ένα αντικείμενο της κλάσσης Node (δηλαδή ένα κόμβο δένδρου) και έναν ακέραιο που συμβολίζει την προτεραιότητα των τελεστών.
    //Στη συνέχεια τσεκάρει αν το περιεχόμενο του κόμβου είναι αριθμητική έκφραση και αν είναι καλεί τον ευατό της αναδρομικά χωρίζοντας αυτή την έκφραση στο 
    //αριστερό και δεξιό μέρος απο τον δεξιότερο τελεστή με την χαμηλότερη προτεραιότητα που βρίσκεται εκτός παρενθέσεων.
    //Έτσι χωρίζει το περιεχόμενο του αρχικού κόμβου σε τρία κομμάτια και επιστρέφει ένα String με αυτά γραμμένα μέσα σε αυτό.
    //Τα τρία κομμάτια είναι η έκφραση μέχρι πρίν απο τον τελεστή που βρήκαμε, ένα ο τελεστής μόνος του και ένα η έκφραση μετά απο τον τελεστή.
    //Αν δεν είναι το περιεχόμενο του κόμβου μια έκφραση τότε απλά το επιστρέφει.
    private String pieces(Node n,String ParentOp) {
        int ParentPriority= ParentOp == null? 0 :getOperatorPriority(ParentOp.charAt(0));
        ParentOp = ParentOp == null ? "":ParentOp;
        
        if (n.isOperator()) {
            if ( (ParentPriority > getOperatorPriority(n.value.charAt(0)) ) || (ParentOp.equals("/") && n.parent.right == n)) {
                return "(" + pieces(n.left,n.value) + n.value + pieces(n.right,n.value) + ")";    
            }
            else {
                return pieces(n.left,n.value) + n.value + pieces(n.right,n.value) ;
            }
        }
        else {
            return n.value;
        }
        
    }
    
    
    //Αυτή η μέθοδος παίρνει ώς όρισμα έναν κόμβο και υπολογίζει και επιστρέφει το αποτέλεσμα της έκφρασης που σχηματίζει αυτός με τα δύο παιδιά του.
    //Χρησιμοποιεί ένα switch case και κάνει τις πράξεις ανάλογα με το ποιος είναι ο τελεστής.
    //Αν δεν είναι τελεστής (δηλαδή αναγκαστικά είναι αριθμός) τότε απλά επιστρέφει την τιμή του σε double μορφή.
    private double arithmeticAct(Node n){
         if (n.isOperator()) {
             switch (n.value.charAt(0)){
                case '+': return arithmeticAct(n.left) + arithmeticAct(n.right);
                case '-': return arithmeticAct(n.left) - arithmeticAct(n.right);
                case 'x': return arithmeticAct(n.left) * arithmeticAct(n.right);
                case '*': return arithmeticAct(n.left) * arithmeticAct(n.right);
                case '/': return arithmeticAct(n.left) / arithmeticAct(n.right);
                case '^': return Math.pow(arithmeticAct(n.left),arithmeticAct(n.right));
                default: return 0;
             }
        }
        else {
            return Double.valueOf(n.value);
        }
    }
    
    public String toString(){
       if (!valid) {
           return null;
       }
       String str= pieces(root,null);
       return str;
    }
    
    public double calculate(){
       if (!valid) {
           return 0;
       }
        return arithmeticAct(root);
    }
    
    public boolean isValid(){
        return valid;
    }
}
