package unit1;

import java.util.Stack;


public class Fixer {
    
    private Stack<Character> postFix;
    private Stack<Character> postFixEval;
    private Stack<Character> preFix;

    private String exp;
    
    public Fixer(String s) {
        super();
        
        exp = s.replaceAll("\\s+", ""); //get rid of spaces
        postFix = new Stack<Character>();
        preFix = new Stack<Character>();
        convertToPostFix();
        System.out.println("Constructor: " + postFix);
        evaluatePostFix(true);
        createPreFix();
        System.out.println("Constructor 2: " + preFix);
        evaluatePreFix(false);
    }
    
    
    // Function to define operator precedence
    private int precedence(char operator) {
        switch (operator) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            case '^':
                return 3;
            default:
                return -1;
        }
    }

    private void convertToPostFix() {
        Stack <Character> temp = new Stack<Character>();
        String postExp = "";
        String operators = "+-*/^";
        
        for (char c : exp.toCharArray()) {
            if (c == '(')
                temp.push(c);
            else if (c == ')') {
                
                while ( !temp.isEmpty() && temp.peek() != '(') {
                    postExp += temp.pop();
                }
                if (!temp.isEmpty())
                    temp.pop();  // Pop '('
            }
            else if (operators.indexOf(c) == -1) {  // If the character is an operand
                postExp += c;
            } else {
                
                while (!temp.isEmpty() && precedence(c) <= precedence(temp.peek())) {
                    postExp += temp.pop();
                }
                temp.push(c);
            }
        }
        
        // Pop remaining operators from the stack
        while(!temp.isEmpty()) {
            postExp += temp.pop();
        }
        
        // Push the postfix string into the stack
        for (int i = postExp.length()-1; i>= 0; i--) {
			postFix.push(postExp.charAt(i));
		}
    }
    
    public int evaluatePostFix(boolean showDemo) {
        Stack<Integer> tempStack = new Stack<Integer>();
        String operators = "+-*/^";
        Stack<Character> locPostFix = (Stack<Character>) postFix.clone();
        while (!locPostFix.isEmpty()) {
            char el = locPostFix.pop();  // Pop element from the stack
            
            if (Character.isDigit(el))  {
                String num = el + "";
                tempStack.push(Integer.parseInt(num));
            } else {
                int index = operators.indexOf(el);
                if (!tempStack.isEmpty()) {
                	int num1 = tempStack.pop(); 
                	int num2 = tempStack.pop();
                	 if (index == 0) {
                         tempStack.push(num2 + num1);
                     } else if (index == 1) {
                         tempStack.push(num2 - num1);
                     } else if (index == 2) {
                         tempStack.push(num2 * num1);
                     } else if (index == 3) {
                         tempStack.push(num2 / num1);
                     } else if (index == 4) {
                         tempStack.push((int)(Math.pow(num2, num1)));
                     }
                }
                
               
            }
            if (showDemo) {
            	try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	System.out.println("Postfix: " + locPostFix);
                System.out.println("Temp Stack: " + tempStack);
                cls();
            }
        }
        System.out.println("Output: " + tempStack.peek());  // The final result of postfix evaluation
        return tempStack.peek();
    }
    
    private void createPreFix() {
        Stack<String> tempStack = new Stack<String>();
        Stack<Character> postFixCopy = (Stack<Character>) postFix.clone(); // Clone the stack to avoid emptying it
        
        // Read the postfix expression from left to right (postFix is filled from right to left, so reverse it)
        while (!postFixCopy.isEmpty()) {
            char c = postFixCopy.pop();
            
            if (precedence(c) == -1) {  // If the symbol is an operand
                tempStack.push(c + "");  // Push operand to stack
            } else {  // If the symbol is an operator
                // Pop two operands from the stack
                String op1 = tempStack.pop();
                String op2 = tempStack.pop();

                // Concatenate the operator before the two operands and push back to stack
                String prefixExp = c + op2 + op1;
                tempStack.push(prefixExp);
            }
        }

        // The stack will contain the final prefix expression
        System.out.println("Prefix Expression: " + tempStack);
        for (char c: tempStack.peek().toCharArray()) {
			preFix.push(c);
		}
    }
    
    public int evaluatePreFix(boolean showDemo) {
        Stack<Integer> tempStack = new Stack<Integer>();  // Stack to hold operands (numbers)
        String operators = "+-*/^";
        Stack<Character> preFixCopy = (Stack<Character>) preFix.clone(); // Clone the prefix stack to avoid emptying it

        // Process the expression from right to left (for prefix notation)
        while (!preFixCopy.isEmpty()) {
            char el = preFixCopy.pop();  // Pop element from the stack
            
            if (Character.isDigit(el))  {
                String num = el + "";
                tempStack.push(Integer.parseInt(num));
            } else {
                int index = operators.indexOf(el);
                if (!tempStack.isEmpty()) {
                	int num1 = tempStack.pop(); 
                	int num2 = tempStack.pop();
                	 if (index == 0) {
                         tempStack.push(num2 + num1);
                     } else if (index == 1) {
                         tempStack.push(num2 - num1);
                     } else if (index == 2) {
                         tempStack.push(num2 * num1);
                     } else if (index == 3) {
                         tempStack.push(num2 / num1);
                     } else if (index == 4) {
                         tempStack.push((int)(Math.pow(num2, num1)));
                     }
                }
                
               
            }
            if (showDemo) {
            	try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	System.out.println("Prefix: " + preFixCopy);
                System.out.println("Temp Stack: " + tempStack);
                cls();
            }
        }

        // The final result will be the only element left in the operandStack
        int result = tempStack.peek();
        System.out.println("Prefix Evaluation Output: " + result);
        return result;
    }

    
    // clear screen
    private void cls() {
    	for (int i = 0; i < 5; i++) {
    		System.out.println();
    	}
    }
    
//    private String printPostFix() {
//    	String out = "[";
//    	for (int i = postFix.size() - 1; i >= 0; i--) {
//    		if (i != 0) {
//    			
//    		}
//    	}
//    }
}
