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
public class ComplexNumber {
    private double real;
    private double img;

    public ComplexNumber(double real, double img) {
        this.real=real;
        this.img=img;
    }

    public ComplexNumber add(ComplexNumber num) {
        ComplexNumber sum = new ComplexNumber(num.real + this.real,num.img + this.img);
        return sum;
    }

    public ComplexNumber subtract( ComplexNumber num) {
        return new ComplexNumber(this.real - num.real, this.img - num.img);
    }

    public ComplexNumber multiply(ComplexNumber num) {
        return new ComplexNumber(this.real * num.real - num.img * this.img, this.real * num.img + this.img * num.real);
    }

    public String toString() {
        if (this.img>=0) {
            return String.format("%.2f + %.2fj", this.real, this.img );
        }
        else {
            return String.format("%.2f - %.2fj", this.real, Math.abs(this.img));
        }
    }
}    

