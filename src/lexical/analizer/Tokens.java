
package lexical.analizer;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *"""
 * @author Thiago Luiz Nunes
 */
public class Tokens {
    
    protected ArrayList<String> keyWords = new ArrayList<>(Arrays.asList("program", "var", "integer", "real", "boolean", "procedure", "begin",
                    "end", "if", "then", "else", "while", "do", "not")); 
    protected ArrayList<String> operators = new ArrayList<>(Arrays.asList("=","<",">","<=",">=","<>","+","-","or","*","/","and"));
    protected ArrayList<String> keyword = new ArrayList<> (Arrays.asList("if","then","else","endif","while","do","endwhile","print","newline","read"));
    protected ArrayList<String> semicolon = new ArrayList<> (Arrays.asList("()",";",".",":","(",")",","));
    protected ArrayList<String> booleanDigits = new ArrayList<>(Arrays.asList("true", "false"));
    
    
    protected String attributtion = ":=";
    protected String identifiers = "([a-z]|[A-Z])([0-9]|[a-z]+|[A-Z]|_)*";
    protected String intDigits = "[0-9]+";
    protected String floatDigits = "[0-9]+[.][0-9]+";
    
    protected String stringA = "/*+([a-z])+*/"; 
    
    protected ArrayList<String> myProgram = null;

    public Tokens(ArrayList<String> myArrayList) {
        this.myProgram = myArrayList;
    }
    
    public int lexicAnalizer() {
        
        for(int i = 0; i < this.myProgram.size(); i++) {
            String line = discardComments(this.myProgram.get(i));
            String[] splited = line.split("\\s+");
            
            //Verify line by line of the program
            for (int j = 0; j < splited.length; j++) {
                String token = splited[j];
                String result = checkToken(token);
                if(!result.equals("unknown")) {
//                    System.out.println(result + " : " + token);
                    System.out.println(result + ": " + token);
                }
                else {
                    String subToken = token.substring(0, token.length() - 1);
                    result = checkToken(subToken);
                    if(!result.equals("unknown")) {
//                        System.out.println(result + " : " +subToken);
                        System.out.println(subToken + " | "+ result + " |" + i);
                        
                        String lastToken = token.substring(token.length() - 1, token.length());
                        result = checkToken(lastToken);
                        if(!result.equals("unknown")) {
//                            System.out.println(result + " : " +lastToken);
                            System.out.println(lastToken + " | "+ result + " |" + i);
                        }
                        else {
                            System.err.println("Error: Does not belong to language!");
                            System.err.println("Line: " + i + ", Status: " + result + " -> " + subToken);
                            return 0;
                        }
                    }
                    else {
                        System.err.println("Error: Does not belong to language!");
                        System.err.println("Line: " + i + ", Status: " + result + " -> " + subToken);
                        return 0;
                    }
                }
            }
        }
        return 1;
    }
    
    private String discardComments(String line) {
        return line.replaceAll("\\{(?s).*?}", "");
    }
    
    private String checkToken(String split) {

        if (this.keyWords.contains(split)) {
            return "Keyword";
        } else if (this.operators.contains((split))) {
            return "Operator";
        } else if (this.semicolon.contains((split))) {
            return "Semicolon";
        } else if (this.keyword.contains((split))) {
            return "Keywords";
        } else if (split.matches(this.intDigits)){
            return "Integer Digit";
        } else if (split.matches(this.floatDigits)) {
            return "Floating Point Digit";
   
        } else if (split.matches(this.identifiers)) {
            if (this.booleanDigits.contains(split)) {
                return "String";
            }
            return "new identifier";
            
      
        } else if (split.matches("\\{(?s).*?")) {
            return "unknown";
        }
        return "unknown";
    }    
}
