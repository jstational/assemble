package ambl.logic;

import arc.struct.*;

// NOTE: WHEN COMPILING, START A NEW THREAD TO COMPILE
public class ACompiler {
    private StringBuilder compiled;
    private Seq<String> splitSemicolon;
    private Seq<String> tokenized;
    private final String src;

    private int srclen;

    public ACompiler(String src) {
        this.src = src;
        srclen = src.length();
        compiled = new StringBuilder();
        splitSemicolon = new Seq<>();
        tokenized = new Seq<>();
    }

    public String compile() {
        CompileSegmentResult csr = splits();
        if(!csr.success) return "ERROR" + csr.returnStatus;
        csr = verifySyntax();
        if(!csr.success) return "ERROR" + csr.returnStatus;
        csr = tokenize();
        if(!csr.success) return "ERROR" + csr.returnStatus;
        csr = simplify();
        if(!csr.success) return "ERROR" + csr.returnStatus;

        csr = null;
        return compiled.toString();
    }

    private CompileSegmentResult splits() {
        boolean inString = false;
        boolean inChar = false;
        boolean inEscape = false;
        int index = 0;
        StringBuilder curstr = new StringBuilder();

        while(index != srclen) {
            char curchar = charAt(index);
            if(curchar == '\"' && !inEscape) {
                inString = !inString;
            }
            if(curchar == '\'' && !inEscape) {
                inChar = !inChar;
            }
            if(curchar == '\\') {
                char nextchar = charAt(index + 1);
                if((nextchar != 'n' || nextchar != '\'' || nextchar != '\"') && !inEscape) return new CompileSegmentResult("Invalid escape character");
                if((nextchar != '\s' || nextchar != '\n' || nextchar != '\t') && (!inString || !inChar)) return new CompileSegmentResult("Backslashes can only be used inside string literals");
                inEscape = !inEscape;
            }
            if(curchar == '\n') {
                if(inString) return new CompileSegmentResult("String literal interrupted by newline", false);
                if(inChar) return new CompileSegmentResult("Char literal interrupted by newline", false);
            }
            boolean inStrChar = inString || inChar;
            if(curchar == ';' && !inStrChar) {
                splitSemicolon.add(curstr.toString().trim());
                curstr = new StringBuilder();
            } else if(curchar == '{' && !inStrChar) {
                splitSemicolon.add(curstr.toString().trim());
                curstr = new StringBuilder();
                splitSemicolon.add('{');
            } else if(curchar == '}' && !inStrChar) {
                splitSemicolon.add(curstr.toString().trim());
                curstr = new StringBuilder();
                splitSemicolon.add('}');
            } else {
                curstr.append(curchar);
            }
            index++;
        }
        if(inString) return new CompileSegmentResult("String literal interrupted by end of code");
        if(inChar) return new CompileSegmentResult("Char literal interrupted by end of code");
        index = 0;

        return new CompileSegmentResult();
    }

    private CompileSegmentResult verifySyntax() {
        return new CompileSegmentResult();
    }

    private CompileSegmentResult tokenize() {
        splitSemicolon = null;
        return new CompileSegmentResult();
    }

    private CompileSegmentResult simplify() {
        return new CompileSegmentResult();
    }

    private CompileSegmentResult verify() {
        return new CompileSegmentResult();
    }

    private CompileSegmentResult toMLOG() {
        tokenized = null;
        return new CompileSegmentResult();
    }

    private CompileSegmentResult simplifyMLOG() {
        return new CompileSegmentResult();
    }

    private char charAt(int i) {
        return src.charAt(i);
    }

    private static class CompileSegmentResult {
        String returnStatus;
        boolean success;

        CompileSegmentResult(String rc, boolean s) {
            returnStatus = rc;
            success = s;
        }

        CompileSegmentResult() {
            this("", true);
        }

        CompileSegmentResult(String rc) {
            this(rc, false);
        }
    }
}