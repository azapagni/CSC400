import java.util.*;

/**
 * Infix Calculator - Evaluates arithmetic expressions in infix notation
 * Supports +, -, *, /, % operations with proper precedence and parentheses
 */
public class InfixCalculator {
    
    /**
     * Evaluates an infix expression and returns the result
     * @param expression The infix expression as a string
     * @return The calculated result as a double
     * @throws IllegalArgumentException for invalid expressions
     */
    public static double evaluate(String expression) throws IllegalArgumentException {
        if (expression == null || expression.trim().isEmpty()) {
            throw new IllegalArgumentException("Expression cannot be empty");
        }
        
        // Remove spaces and validate characters
        expression = expression.replaceAll("\\s+", "");
        if (!isValidExpression(expression)) {
            throw new IllegalArgumentException("Invalid characters in expression");
        }
        
        // Convert infix to postfix, then evaluate
        String postfix = infixToPostfix(expression);
        return evaluatePostfix(postfix);
    }
    
    /**
     * Converts infix expression to postfix notation using Shunting Yard algorithm
     */
    private static String infixToPostfix(String infix) {
        StringBuilder postfix = new StringBuilder();
        Stack<Character> operatorStack = new Stack<>();
        
        for (int i = 0; i < infix.length(); i++) {
            char c = infix.charAt(i);
            
            if (Character.isDigit(c)) {
                // Handle multi-digit numbers
                StringBuilder number = new StringBuilder();
                while (i < infix.length() && (Character.isDigit(infix.charAt(i)) || infix.charAt(i) == '.')) {
                    number.append(infix.charAt(i));
                    i++;
                }
                i--; // Back up one position
                postfix.append(number).append(" ");
            }
            else if (c == '(') {
                operatorStack.push(c);
            }
            else if (c == ')') {
                while (!operatorStack.isEmpty() && operatorStack.peek() != '(') {
                    postfix.append(operatorStack.pop()).append(" ");
                }
                if (operatorStack.isEmpty()) {
                    throw new IllegalArgumentException("Mismatched parentheses");
                }
                operatorStack.pop(); // Remove the '('
            }
            else if (isOperator(c)) {
                while (!operatorStack.isEmpty() && 
                       operatorStack.peek() != '(' && 
                       getPrecedence(operatorStack.peek()) >= getPrecedence(c)) {
                    postfix.append(operatorStack.pop()).append(" ");
                }
                operatorStack.push(c);
            }
        }
        
        while (!operatorStack.isEmpty()) {
            if (operatorStack.peek() == '(' || operatorStack.peek() == ')') {
                throw new IllegalArgumentException("Mismatched parentheses");
            }
            postfix.append(operatorStack.pop()).append(" ");
        }
        
        return postfix.toString().trim();
    }
    
    /**
     * Evaluates a postfix expression
     */
    private static double evaluatePostfix(String postfix) {
        Stack<Double> stack = new Stack<>();
        String[] tokens = postfix.split("\\s+");
        
        for (String token : tokens) {
            if (token.matches("-?\\d+(\\.\\d+)?")) { // Number (including negative)
                stack.push(Double.parseDouble(token));
            }
            else if (isOperator(token.charAt(0))) {
                if (stack.size() < 2) {
                    throw new IllegalArgumentException("Invalid expression: insufficient operands");
                }
                double b = stack.pop();
                double a = stack.pop();
                double result = performOperation(a, b, token.charAt(0));
                stack.push(result);
            }
        }
        
        if (stack.size() != 1) {
            throw new IllegalArgumentException("Invalid expression: too many operands");
        }
        
        return stack.pop();
    }
    
    /**
     * Performs arithmetic operation
     */
    private static double performOperation(double a, double b, char operator) {
        switch (operator) {
            case '+': return a + b;
            case '-': return a - b;
            case '*': return a * b;
            case '/': 
                if (b == 0) throw new IllegalArgumentException("Division by zero");
                return a / b;
            case '%': 
                if (b == 0) throw new IllegalArgumentException("Modulo by zero");
                return a % b;
            default: throw new IllegalArgumentException("Unknown operator: " + operator);
        }
    }
    
    /**
     * Returns operator precedence
     */
    private static int getPrecedence(char operator) {
        switch (operator) {
            case '+':
            case '-': return 1;
            case '*':
            case '/':
            case '%': return 2;
            default: return 0;
        }
    }
    
    /**
     * Checks if character is a valid operator
     */
    private static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '%';
    }
    
    /**
     * Validates if expression contains only valid characters
     */
    private static boolean isValidExpression(String expression) {
        for (char c : expression.toCharArray()) {
            if (!Character.isDigit(c) && !isOperator(c) && c != '(' && c != ')' && c != '.') {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Main method for testing the calculator
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== Infix Calculator ===");
        System.out.println("Supported operations: +, -, *, /, %");
        System.out.println("Features:");
        System.out.println("  - Single and multi-digit operands");
        System.out.println("  - Decimal numbers supported");
        System.out.println("  - Proper operator precedence");
        System.out.println("  - Parentheses for grouping");
        System.out.println("Enter 'quit' to exit");
        System.out.println();
        
        while (true) {
            System.out.print("Enter expression: ");
            String input = scanner.nextLine();
            
            if (input.equalsIgnoreCase("quit")) {
                System.out.println("Goodbye!");
                break;
            }
            
            try {
                double result = evaluate(input);
                System.out.println("Result: " + result);
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
            System.out.println();
        }
        
        scanner.close();
    }
}