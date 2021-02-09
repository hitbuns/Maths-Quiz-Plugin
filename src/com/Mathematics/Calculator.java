package com.Mathematics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Calculator {

    String expression;

    public Calculator(String expr)
    {
        expression = expr.replaceAll("\\s+", "");
    }

    class Operation
    {
        public char   operator;
        public String operand;
    }

    public double evalExpr()
    {
        // -----------------
        // Initialize result
        // -----------------
        double result;
        result = 0;

        // -------------------------------
        // Split expression into its parts
        // -------------------------------
        List<Operation> operations;
        operations = splitExpression(expression);

        // -------------------
        // Evaluate operations
        // -------------------
        Iterator<Operation> it;
        Operation           operation;
        it = operations.iterator();
        while (it.hasNext())
        {
            operation = it.next();
            switch (operation.operator)
            {
                case '+':
                    result += eval(operation.operand);
                    break;
                case '-':
                    result -= eval(operation.operand);
                    break;
                default: // This is the first element in the list
                    result = eval(operation.operand);
                    break;
            }
        }

        // ----
        // Done
        // ----
        return (result);

    } // evalExpr

    /**
     * This method splits the specified expression.
     * The result is a list of operators and operands.
     * <br>The first element will have an empty operator.
     **/
    private List<Operation> splitExpression(String expression)
    {
        // -----------------
        // Initialize result
        // -----------------
        List<Operation> result;
        result = new ArrayList<Operation>();

        // -------------------------------
        // Split expression into its parts
        // -------------------------------
        String[] parts;
        parts = expression.split("[\\+\\-]");

        // ----------------------------
        // Convert into internal format
        // ----------------------------
        int           counter;
        int           index_operator;
        Operation part;
        index_operator = 0;
        for (counter = 0; counter < parts.length; counter++)
        {
            // ----------------------------
            // Extract operand and operator
            // ----------------------------
            part = new Operation();
            part.operand = parts[counter];
            if (counter == 0)
                part.operator = '\0';
            else
            {
                part.operator = expression.charAt(index_operator);
                index_operator++;
            }

            // ------------------
            // We got another one
            // ------------------
            result.add(part);

            // ----------------------
            // Point to next operator
            // ----------------------
            index_operator += part.operand.length();

        } // for all parts

        // ----
        // Done
        // ----
        return (result);

    } // splitExpression

    private double eval(String input)
    {
        if (input.contains("*"))
        {
            String[] operands = input.split("\\*", 2);
            return eval(operands[0]) * eval(operands[1]);
        }
        else if (input.contains("/"))
        {
            String[] operands = input.split("\\/", 2);
            return eval(operands[0]) / eval(operands[1]);
        }
        else if (input.matches("[0-9]*"))
            return Integer.parseInt(input);
        else
            throw new RuntimeException();
    }

}
