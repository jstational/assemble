package ambl.logic;

import arc.struct.*;

/***
 * NOTE: WHEN COMPILING, START A NEW THREAD TO COMPILE
 */
public class ACompiler {
    private StringBuilder code;
    private IntMap<Seq<String>> splits;
    private final String src;

    private int srclen;

    public ACompiler(String src) {
        this.src = src;
        srclen = src.length();
        code = new StringBuilder();
        splits = new IntMap<>();
    }

    public String compile() {
        CompileSegmentResult ss = splits();
        if(!ss.success) return "ERROR" + ss.returnStatus;
        // once you reach back to the dialog, check if startsWith("ERROR")

        CompileSegmentResult scb = splitcb();

        return "";
    }

    private CompileSegmentResult splits() {
        boolean inString = false;
        boolean inChar = false;
        boolean inEscape = false;
        int index = 0;
        StringBuilder curstr = new StringBuilder();
        int atLine = 0;
        while(index != srclen) {
            char curchar = charAt(index);
            if(curchar == '\"' && !inEscape) {
                inString = !inString;
            }
            if(curchar == '\'' && !inEscape) {
                inChar = !inChar;
            }
            if(curchar == '\\') {
                inEscape = !inEscape;
            }
            if(curchar == '\n') {
                if(inString) {
                    return new CompileSegmentResult("" + atLine + "String interrupted by newline", false);
                }
                if(inChar) {
                    return new CompileSegmentResult("" + atLine + "Char interrupted by newline", false);
                }
                atLine++;
            }
            if(curchar == ';' && (inString || inChar)) {
                splits.get(atLine).add(curstr.toString().trim());
                curstr = new StringBuilder();
            } else {
                curstr.append(curchar);
            }
            index++;
        }
        return new CompileSegmentResult("success", true);
    }

    private CompileSegmentResult splitcb() {
        return new CompileSegmentResult("success", true);
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
    }
}