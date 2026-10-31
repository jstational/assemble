package ambl.logic;

import java.nio.charset.StandardCharsets;

import arc.struct.*;
import arc.func.Cons;

import ambl.*;

public final class ACompiler {
    private StringBuilder compiled;
    private Seq<String> splitStatement;
    private Seq<String> tokenized;
    private final String src;
    private int srclen;

    public ACompiler(String src) {
        this.src = src;
        srclen = src.length();
        compiled = new StringBuilder("#amblv" + AssembleMod.version);
        splitStatement = new Seq<>();
        tokenized = new Seq<>();
    }

    public String compile() {
        boolean inString = false;
        boolean inChar = false;
        boolean inEscape = false;
        int index = 0;
        StringBuilder curstr = new StringBuilder();

        while(index != srclen) {
            char curchar = charAt(index);
            boolean inStrChar = inString || inChar;
            if(curchar == '\"' && !inEscape) {
                inString = !inString;
            }
            if(curchar == '\'' && !inEscape) {
                inChar = !inChar;
            }
            if(curchar == '\\') {
                if(!inStrChar) return "ERRORBackslahses can only be used in Char or String literals";
                if(index + 1 == srclen) return "ERROREscape interrupted by end of code";
                char nextchar = charAt(index + 1);
                if((nextchar != 'n' && nextchar != '\'' && nextchar != '\"') && inEscape) return "ERRORInvalid escape character";
                inEscape = !inEscape;
            }
            if(curchar == '\n') {
                if(inString) return "ERRORString literal interrupted by newline";
                if(inChar) return "ERRORChar literal interrupted by newline";
            }
            if(!inStrChar) {
                switch(curchar) {
                    case ';' -> {
                        splitStatement.add(curstr.toString().trim());
                        curstr = new StringBuilder();
                    }

                    case '{' -> splitStatement.add("{");
                    case '}' -> splitStatement.add("}");
                    case '(' -> splitStatement.add("(");
                    case ')' -> splitStatement.add(")");

                    default -> curstr.append(curchar);
                }
            } else curstr.append(curchar == '\n' ? ' ' : curchar);
            index++;
        }
        if(inString) return "ERRORString literal interrupted by end of code";
        if(inChar) return "ERRORChar literal interrupted by end of code";

        int index = 0;
        String curStatement;

        while(index != splitStatement.size) {
            curStatement = splitStatement.get(index);
            if(curStatement.endsWith(".")) return "ERRORMember access interrupted";
            if(curStatement.equals("#")) return "ERRORMalformed macro declaration";
            if(curStatement.equals("@")) return "ERRORMalformed annotation";
            if(curStatement.startsWith("return")) {
                String curCopy = curStatement.substring("return".length()).trim();
                if(!curCopy.startsWith("(") || !curCopy.endsWith(")")) return "ERRORMalformed return statement";
            }
            if(curStatement.startsWith("if"))

            index++;
        }

        return compiled.toString();
    }
    
    private void addStatement(String st) {
        compiled.append(st + "\n");
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