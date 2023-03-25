import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MathLex {
    private static class Token {
        private String lexeme;
        private TokenTypes tokenType;

        public enum TokenTypes {
            ADD_OP, SUB_OP, MUL_OP,
            DIV_OP, MOD_OP, LEFT_PAREN,
            RIGHT_PAREN, ASSIGN_OP, EQUALS_OP,
            LESS_OP, LESS_EQUALS_OP, GREATER_OP,
            GREATER_EQUALS_OP, AND_OP, OR_OP,
            IDENT, INT_LIT, FLOAT_LIT
        }

        public Token (String ch, TokenTypes tokenType){
            this.lexeme = ch;
            this.tokenType = tokenType;
        }

        @Override
        public String toString(){
            return String.format("( %s , %s )",lexeme, tokenType);
        }
    }

    public static void lex(String ch, List<Token> tokens) {
            if (ch.matches("[0-9]*[A-Za-z]+")) {
                tokens.add(new Token(ch, Token.TokenTypes.IDENT));
                return;
            }
            if (ch.matches("[0-9]+")){
                tokens.add(new Token(ch, Token.TokenTypes.INT_LIT));
                return;
            }
            if (ch.matches("[0-9]*[.][0-9]+f?")) {
                tokens.add(new Token(ch, Token.TokenTypes.FLOAT_LIT));
                return;
            }
            switch (ch) {
                case "+":
                    tokens.add(new Token(ch, Token.TokenTypes.ADD_OP));
                    break;
                case "-":
                    tokens.add(new Token(ch, Token.TokenTypes.SUB_OP));
                    break;
                case "*":
                    tokens.add(new Token(ch, Token.TokenTypes.MUL_OP));
                    break;
                case "/":
                    tokens.add(new Token(ch, Token.TokenTypes.DIV_OP));
                    break;
                case "%":
                    tokens.add(new Token(ch, Token.TokenTypes.MOD_OP));
                    break;
                case "(":
                    tokens.add(new Token(ch, Token.TokenTypes.LEFT_PAREN));
                    break;
                case ")":
                    tokens.add(new Token(ch, Token.TokenTypes.RIGHT_PAREN));
                    break;
                case "=":
                    tokens.add(new Token(ch, Token.TokenTypes.ASSIGN_OP));
                    break;
                case "==":
                    tokens.add(new Token(ch, Token.TokenTypes.EQUALS_OP));
                    break;
                case "<":
                    tokens.add(new Token(ch, Token.TokenTypes.LESS_OP));
                    break;
                case "<=":
                    tokens.add(new Token(ch, Token.TokenTypes.LESS_EQUALS_OP));
                    break;
                case ">":
                    tokens.add(new Token(ch, Token.TokenTypes.GREATER_OP));
                    break;
                case ">=":
                    tokens.add(new Token(ch, Token.TokenTypes.GREATER_EQUALS_OP));
                    break;
                case "&&":
                    tokens.add(new Token(ch, Token.TokenTypes.AND_OP));
                    break;
                case "||":
                    tokens.add(new Token(ch, Token.TokenTypes.OR_OP));
                    break;
                default:
                    System.out.println("Lexical error: " + ch + " is not a recognized <Token>");
                    System.exit(1);
            }
    }

    public static void main(String[] args) throws FileNotFoundException {
        if(args.length != 1) {
            System.err.println("Invalid command line, exactly one argument required");
            System.exit(1);
        }

        File file = new File(args[0]);
        Scanner scanner = new Scanner(file);
        scanner.useDelimiter(" +");

        List<Token> tokens = new ArrayList<>();

        while (scanner.hasNext()){
            String ch = scanner.next();
            if (ch.length() >= 2 && !ch.matches("==|>=|<=|&&|[|]{2}")){
                for (String el : ch.split("(?<=[^A-Za-z0-9.])|(?=[^A-Za-z0-9.])")) lex(el, tokens);
            }
            else lex(ch, tokens);
        }
        scanner.close();

        for (Token token : tokens) {
            System.out.println(token.toString());
        }

        System.exit(0);
    }
}
