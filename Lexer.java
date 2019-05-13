import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.*;

/*
 * Lexical analyzer for Scheme-like minilanguage:
 * (define (foo x) (bar (baz x)))
 */
public class Lexer {
    public static enum Type {
        // This Scheme-like language has three token types:
        // open parens, close parens, and an "atom" type
         ADINTFIRE,NUMBER,ASSIGNMENT,OPERATION,KEYWORD,LOGICALOPERATION,PARANTHES,STRING,IOOPERATOR,INCLUDELIBIRARY;
    }

    public static class Token {
        public final Type t;
        public final String c; // contents mainly for atom tokens
        // could have column and line number fields too, for reporting errors later

        public Token(Type t, String c) {
            this.t = t;
            this.c = c;
        }

        public String toString() {

            return t.toString()+"<"+c+">";
        }
    }

    /*
     * Given a String, and an index, get the atom starting at that index
     */
    public static String getAtom(String s, int i) {
        int j = i;

        for (; j < s.length();) {
            if (Character.isLetter(s.charAt(j))||Character.isDigit(s.charAt(j))||s.charAt(j)=='_') {
                j++;
            } else {
                return s.substring(i, j);
            }
        }
        return s.substring(i, j);
    }
    public static String getNum(String s, int i) {
        int j = i;
        boolean b = true;
        for (; j < s.length();) {
            if ((Character.isDigit(s.charAt(j))||(s.charAt(j)=='.'&&b==true))) {
                j++;
            } else {
                return s.substring(i, j);
            }
            if (s.charAt(j-1)=='.'){b=false;}
        }
        return s.substring(i, j);
    }

    public static List<Token> lex(String input) {
        //System.out.println(input);
        List<Token> result = new ArrayList<Token>();
        List<String> keywords = new ArrayList<String>(Arrays.asList("int",
                "main","double","long","bool","class","for","if","return","while","auto", "break", "case", "char", "const", "continue", "default",
                "do", "else", "enum", "extern","goto",
                "if", "int", "long", "register", "return", "short", "signed",
                "sizeof", "static", "struct", "switch", "typedef", "union",
                "unsigned", "void", "volatile"
                ,"float","using","namespace","std","cout","cin"));

        // System.out.println(result);
        for (int i = 0; i < input.length();) {
            System.out.println(input.charAt(i));
            System.out.println(i);
            if (Character.isWhitespace(input.charAt(i))||input.charAt(i)=='\n'||input.charAt(i)==';'||input.charAt(i)==','||input.charAt(i)==':') {
                i++;
            }else if ((input.charAt(i)=='>'&&input.charAt(i+1)=='>')||(input.charAt(i)=='<'&&input.charAt(i+1)=='<')){

                result.add(new Token(Type.IOOPERATOR, "ioOperator ("+input.charAt(i)+input.charAt(i+1)+")"));
                i+=2;
            }else if (input.charAt(i)=='#'){
                String atom = new String();
                while (input.charAt(i)!='>'){
                    atom+=input.charAt(i);
                    i++;
                }
                atom+=input.charAt(i);
                i++;
                result.add(new Token(Type.INCLUDELIBIRARY, atom));
            }
            else if (input.charAt(i)=='^'||input.charAt(i)=='&'||input.charAt(i)=='|'||input.charAt(i)=='<'||input.charAt(i)=='>'||input.charAt(i)=='!'||(input.charAt(i)=='='&&input.charAt(i+1)=='=')){
                if (input.charAt(i+1)=='='){

                    result.add(new Token(Type.LOGICALOPERATION, "logical op ("+input.charAt(i)+"=)"));
                    i+=2;
                }else if (input.charAt(i+1)==input.charAt(i)){

                    result.add(new Token(Type.LOGICALOPERATION, "logical op ("+input.charAt(i)+input.charAt(i+1)+")"));
                    i+=2;
                }
                else{
                    result.add(new Token(Type.LOGICALOPERATION, "logical op ("+input.charAt(i)+")"));
                    i++;
                }
            }
            else if (input.charAt(i)=='('||input.charAt(i)==')'||input.charAt(i)=='['||input.charAt(i)==']'||input.charAt(i)=='{'||input.charAt(i)=='}'){
                result.add(new Token(Type.PARANTHES, "paranthes ("+input.charAt(i)+")"));
                i++;
            }
            else if (input.charAt(i)=='='){
                i++;
                result.add(new Token(Type.ASSIGNMENT, "assignment operator (=)"));
            }

            else if (input.charAt(i)=='+'||input.charAt(i)=='-'||input.charAt(i)=='*'||input.charAt(i)=='/'||input.charAt(i)=='%'){
                if (input.charAt(i+1)=='='){

                    result.add(new Token(Type.ASSIGNMENT, "assignment operator ("+input.charAt(i)+"=)"));
                    i+=2;
                }else{


                    result.add(new Token(Type.OPERATION, "assignment operator ("+input.charAt(i)+")"));
                    i++;
                }
            }
            else if (input.charAt(i)=='\''||input.charAt(i)=='\"'){
                String stt = new String();

                stt+=input.charAt(i);
                int j = i;
                j++;
                System.out.println(j);
                for (; j < input.length();) {
                    if (input.charAt(j)==input.charAt(i)) {

                        stt+=input.charAt(j);
                        j++;
                        break;
                    } else {

                        stt+=input.charAt(j);
                        j++;
                    }

                }
                i += stt.length();
                result.add(new Token(Type.STRING, stt));
            }
            else if (Character.isLetter(input.charAt(i))) {
                String atom = getAtom(input, i);
                i += atom.length();
                if (keywords.contains(atom)){result.add(new Token(Type.KEYWORD, atom));}
                else {result.add(new Token(Type.ADINTFIRE, atom));}
            } else {
                String atom = getNum(input, i);
                i += atom.length();
                result.add(new Token(Type.NUMBER, atom));
            }

        }
        return result;
    }

    public static void main(String[] args) {

        String s = new String();
        try{
            FileReader fr = new FileReader("/home/kerollos/Desktop/compilers project/Lexecal analyser/src/input.txt");
            BufferedReader br = new BufferedReader(fr);
            String str = new String();
            while ((str=br.readLine())!=null){
                System.out.println(str);
                s = s+str+"\n";
            }
        }catch (IOException e){
            System.out.println("File not found");
        }
        //System.out.println(s);
        List<Token> tokens = lex(s);
        for (Token t : tokens) {
            System.out.println(t);
        }
    }
}