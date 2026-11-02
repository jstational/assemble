package ambl.logic;

import java.nio.charset.StandardCharsets;

import arc.struct.*;
import arc.func.Cons;

import ambl.*;

public class ACompiler {
    private final String src;
    private int srclen;

    private Seq<String> splitStatement, tokenized;
    private StringBuilder compiled;

    public ACompiler(String src) {
        this.src = src;
        srclen = src.length();
        compiled = new StringBuilder();
        splitStatement = new Seq<>();
        tokenized = new Seq<>();
    }

    public String compile() {
        int index = 0;

        while(index != srclen) {
            boolean afterSemicolon = true;
            char curchar = charAt(index);
            StringBuilder curstr = new StringBuilder();
            String curstrs = "";

            switch(curchar) {
                case '\"' -> {
                    curstrs = curstr.toString().trim();
                    if(!curstrs.isEmpty()) splitStatement.add(curstrs);
                    splitStatement.add("\"");

                    boolean endStringSubroutine = false;
                    index++;
                    if(index >= srclen) return "ERRORString literal interrupted by end of code";
                    while(!endStringSubroutine) {
                        curchar = src.charAt(index);
                        if(curchar == '\\') {
                            index++;
                            if(index >= srclen) return "ERRORString literal interrupted by end of code";
                            curchar = src.charAt(index);
                            switch(curchar) {
                                case 'n' -> curstr.append("\\n");
                                case '\\' -> curstr.append("\\\\");
                                case '"' -> curstr.append("\\\"");
                                case '\'' -> curstr.append("\\'");
                                default -> return "ERRORInvalid escape character";
                            }
                        } else if(curchar == '"') {
                            splitStatement.add(curstr);
                            splitStatement.add("\"");
                            endStringSubroutine = true;
                        } else if(curchar == '\n') return "ERRORString literal interrupted by newline" else curstr.append(curchar);
                        index++;
                        if(index >= srclen) return "ERRORString literal interrupted by end of code";
                    }
                }
                case '\'' -> {
                    curstrs = curstr.toString().trim();
                    if(!curstrs.isEmpty()) splitStatement.add(curstrs);
                    splitStatement.add("'");

                    index++;
                    if(index >= srclen) return "ERRORChar literal interrupted by end of code";
                    curchar = src.charAt(index);
                    if(curchar == '\\') {
                        index++;
                        if(index >= srclen) return "ERRORChar literal interrupted by end of code";
                        curchar = src.charAt(index);
                        switch(curchar) {
                            case 'n' -> curstr.append("\\n");
                            case '\\' -> curstr.append("\\\\");
                            case '"' -> curstr.append("\\\"");
                            case '\'' -> curstr.append("\\'");
                            default -> return "ERRORInvalid escape character";
                        }
                    } else if(curchar == '\'') return "ERRORInvalid char literal" else curstr.append(curchar);
                    index++;
                    if(index >= srclen) return "ERRORChar literal interrupted by end of code";
                    curchar = src.charAt(index);
                    if(curchar != '\'') return "ERRORInvalid char literal" else {
                        splitStatement.add(curstr);
                        splitStatement.add("'");
                    }
                }
                case '\\' -> return "ERRORBackslahses can only be used in Char or String literals";
                default -> if(!Character.isWhitespace(curchar)) curstr.append(curchar);
            }
            index++;
        }

        int index = 0;
        String curStatement;

        while(index != splitStatement.size) {
            curStatement = splitStatement.get(index);
            if(curStatement.endsWith(".")) return "ERRORMember access interrupted";
            if(curStatement.equals("#")) return "ERRORMalformed macro declaration";
            if(curStatement.equals("@")) return "ERRORMalformed annotation";
            if(curStatement.equals("return")) {
                index++;
                String next = splitStatement.get(index)
                if(!next.equals("(")) return "ERRORMalformed return statement";
            }
            if(curStatement.equals("if")) {
                index++;
                String next = splitStatement.get(index)
                if(!next.equals("(")) return "ERRORMalformed if statement";
            }

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
}