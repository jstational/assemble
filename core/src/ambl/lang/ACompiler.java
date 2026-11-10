package ambl.logic;

import arc.struct.*;
import arc.func.Cons;

import ambl.AssembleMod;

public class ACompiler {
    public ACompiler() {}

    public String compile(String src) {
        int srclen = src.length();
        Seq<String> splitStatement = new Seq<>();

        int index = 0;

        while(index < srclen) {
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
                        switch(curchar) {
                            case '\\' -> {
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
                            }
                            case '"' -> {
                                splitStatement.add(curstr);
                                splitStatement.add("\"");
                                endStringSubroutine = true;
                            } 
                            case '\n' -> return "ERRORString literal interrupted by newline";
                            default -> curstr.append(curchar);
                        }
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

        index = 0;
        String curStatement;

        while(index != splitStatement.size) {
            curStatement = splitStatement.get(index);
            if(curStatement.endsWith(".")) return "ERRORMember access interrupted";
            if(curStatement.equals("#")) return "ERRORMalformed macro declaration";
            if(curStatement.equals("@")) return "ERRORMalformed annotation";
            if(curStatement.equals("return")) {
                index++;
                String next = splitStatement.get(index);
                if(!next.equals("(")) return "ERRORMalformed return statement";
            }
            if(curStatement.equals("if")) {
                index++;
                String next = splitStatement.get(index);
                if(!next.equals("(")) return "ERRORMalformed if statement";
            }

            index++;
        }

        StringBuilder compiled = new StringBuilder();

        return compiled.toString();
    }
}