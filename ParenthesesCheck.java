/*
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */ 
 
 /**
  * <p>A simple parser to validate an expression's nesting of parentheses.  
  *  
  * @author Robert Laniewski <depthn@gmail.com>
  * @version 1.0 
  */
public class ParenthesesCheck {

    public boolean validateExpression(final String expression) {        
        final class Parser {
            int i = 0;

            boolean parseExpression(Character closingParenthesis) {
                boolean validated = true;
                
                while (i < expression.length()) {
                    char c = expression.charAt(i++);
                    
                    if (closingParenthesis != null && c == closingParenthesis) {
                    	return validated;
                    } else if (c == ')' || c == ']' || c == '}') {
                    	validated = false; // unexpected token
                    } else if (c == '(') {
                        validated = validated & parseExpression(')');
                    } else if (c == '[') {
                        validated = validated & parseExpression(']');
                    } else if (c == '{') {
                        validated = validated & parseExpression('}');
                    }         
                }
                
                return closingParenthesis == null && validated;
            }                
        }

        return new Parser().parseExpression(null);
    }
    
    public static void main(String[] args) {
        ParenthesesCheck p = new ParenthesesCheck();
        
        for (int i = 0; i < args.length; i++) {
        	System.out.println(args[i]+": "+p.validateExpression(args[i]));
        }
        
        // Sample input / tests
        System.out.println(p.validateExpression("(a(b()([])-(()(xy()))))")); // true
        System.out.println(p.validateExpression("(a(b({)(})-(([])(xy())))")); // false
        System.out.println(p.validateExpression("{/")); // false
        System.out.println(p.validateExpression("a")); // true
        System.out.println(p.validateExpression("a()")); // true
        System.out.println(p.validateExpression("a(b)")); // true
        System.out.println(p.validateExpression("[{}]")); // true
        System.out.println(p.validateExpression("[(]")); // false
        System.out.println(p.validateExpression("z([{}-()]{a})")); // true
        System.out.println(p.validateExpression("[(])")); // false
        System.out.println(p.validateExpression("}{")); // false    
    }

}
