/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ce325.hw1;

/**
 *
 * @author dam
 */
public class ComplexMatrix {
    private int rows;
    private int cols;
    private ComplexNumber elements[][];

    public ComplexMatrix (int rows, int cols) {
        this.rows=rows;
        this.cols=cols;
        this.elements= new ComplexNumber [rows][cols];
    }

    public ComplexMatrix(ComplexMatrix original) {
        ComplexMatrix result= new ComplexMatrix(original.rows, original.cols);
        for (int i=0; i<original.rows; i++) {
            for (int j=0; j<original.cols; j++) {
                result.elements[i][j]=new ComplexNumber(0,0).add(original.elements[i][j]);
            }
        }
    }

    public ComplexMatrix (int rows, int cols, RandomGenerator rg) {
        this.rows=rows;
        this.cols=cols;
        this.elements= new ComplexNumber [rows][cols];
        for (int i=0; i<rows; i++) {
            for (int j=0; j<cols; j++) {
                this.elements[i][j]=new ComplexNumber(rg.getDouble(),rg.getDouble());
            }
        }
    }

    public String toString() {
        String str="[";
        if(this.rows == 0 || this.cols == 0){
            return null;
        }
        for (int i=0; i<rows; i++) {
            for (int j=0; j<cols; j++) {
                try {
                    str += this.elements[i][j].toString();
                }
                  catch(Exception e) {
                    System.out.println(e);
                }
                if (j!=cols-1) {
                    str=str +", ";
                }
            }
            if (i!=rows-1) {
                str=str +";\n";
            }
            else if (i==rows-1) {
                str=str + ";";
            }
        }
        str=str + "]\n";
        return str;
    }

    public ComplexMatrix add(ComplexMatrix matrix){
        if (matrix.rows!=this.rows || matrix.cols!=this.cols) {
            return null;
        }

        ComplexMatrix sum = new ComplexMatrix(this.rows,this.cols);
        for (int i=0; i<rows; i++) {
            for (int j=0; j<cols; j++) {
                sum.elements[i][j]=this.elements[i][j].add(matrix.elements[i][j]);
            }
        }
        return sum;

    }

    public ComplexMatrix subtract(ComplexMatrix matrix) {
        if (matrix.rows!=this.rows || matrix.cols!=this.cols) {
            return null;
        }

        ComplexMatrix sum = new ComplexMatrix(this.rows,this.cols);
        for (int i=0; i<rows; i++) {
            for (int j=0; j<cols; j++) {
                sum.elements[i][j]=this.elements[i][j].subtract(matrix.elements[i][j]);
            }
        }
        return sum;
    }

    public ComplexMatrix multiply(ComplexMatrix matrix) {
        if (this.cols!=matrix.rows) {
            return null;
        }
        else {
            ComplexMatrix output= new ComplexMatrix(this.rows, matrix.cols);
            for (int k=0; k < this.rows; k++) {
                for (int l=0; l < matrix.cols; l++) {
                    ComplexNumber temp = new ComplexNumber(0, 0);
                    for (int i=0; i< matrix.rows; i++) {
                        temp = temp.add(this.elements[k][i].multiply(matrix.elements[i][l]));
                    }
                    output.elements[k][l]=temp;
                }
            }
            return output;
         }
    }

    public void assign(ComplexMatrix matrix) {
        this.rows=matrix.rows;
        this.cols=matrix.cols;
        this.elements= new ComplexNumber [this.rows][this.cols];
        
        for (int i=0; i<this.rows; i++) {
            for (int j=0; j<this.cols; j++) {
                ComplexNumber temp= new ComplexNumber(0,0);
                this.elements[i][j]=temp.add(matrix.elements[i][j]);
            }
        }
    }

    public ComplexMatrix subMatrix(int delRow, int delCol){
        if (delRow>this.rows || delCol>this.cols || delCol<0 || delRow<0) {
            return null;
        }
        ComplexMatrix result = new ComplexMatrix(this.rows-1, this.cols-1);
        for (int i = 0; i<this.rows; i++){
            if (i == delRow){
                continue;
            }
            
            for(int j =0; j<this.cols; j++){
                if(j == delCol)
                    continue;
            result.elements[i - (i >= delRow? 1:0)][j - (j >= delCol?1:0)] = new ComplexNumber(0,0).add(this.elements[i][j]);
        }}
        return result;
    }

    public ComplexNumber determinant() {
        return this.determinant(this);
    }

    private ComplexNumber determinant(ComplexMatrix tempMatrix){
        ComplexNumber result = new ComplexNumber(0,0);
        if(tempMatrix.rows != tempMatrix.cols){
            return null;
        }
        if(tempMatrix.rows == 1){
            return tempMatrix.elements[0][0];
        }
        if(tempMatrix.rows == 2){
            return tempMatrix.elements[0][0].multiply(tempMatrix.elements[1][1]).subtract(tempMatrix.elements[1][0].multiply(tempMatrix.elements[0][1]));
        }
        else {
            for(int i = 0; i< tempMatrix.rows; i++){
                if(i % 2 == 0){
                    result = result.add(tempMatrix.elements[0][i].multiply(tempMatrix.determinant(tempMatrix.subMatrix(0,i))));
                }else{
                    result = result.subtract(tempMatrix.elements[0][i].multiply(tempMatrix.determinant(tempMatrix.subMatrix(0,i))));
                }
            }
            return result;
        }
    }
    
}
