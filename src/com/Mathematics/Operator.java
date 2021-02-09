package com.Mathematics;

public enum Operator {

    DIVISION(1,'/'),
    MULTIPLICATION(2,'*'),
    ADDITION(3,'+'),
    SUBTRACTION(4,'-')
    ;

    private Operator(int id,char symbol) {
        this.id = id;
        this.symbol = symbol;
    }

    public int id;
    public char symbol;

}
