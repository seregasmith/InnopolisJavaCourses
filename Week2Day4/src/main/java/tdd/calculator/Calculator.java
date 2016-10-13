package tdd.calculator;

/**
 * Created by Smith on 13.10.2016.
 */
public class Calculator {

    public static<T extends Number> Double add(T left, T right){
        return new Double(left.doubleValue() + right.doubleValue());
    }

    public static<T extends Number> Double sub(T left, T right){
        return new Double(left.doubleValue() - right.doubleValue());
    }

    public static<T extends Number> Double div(T left, T right){
        return new Double(left.doubleValue() / right.doubleValue());
    }

    public static<T extends Number> Double mult(T left, T right){
        return new Double(left.doubleValue() * right.doubleValue());
    }

    public static<T extends Number> Double toNeg(T val){
        return new Double(val.doubleValue() * -1);
    }
}
