package ce325.hw2;

public class Node {
    public Node parent;
    public Node left;
    public Node right;
    public String value;
    
    public Node(Node parent, String value) {
       this.parent=parent;
       this.value=value;
    }
    
    public boolean isOperator(){
        return this.left != null && this.right != null;
    }
}
