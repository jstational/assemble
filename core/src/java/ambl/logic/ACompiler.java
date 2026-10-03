package ambl.logic;

import arc.struct.*;

/**
 * NOTE: WHEN COMPILING, START A NEW THREAD TO COMPILE
 */
public class ACompiler {
    private StringBuilder code;
    private Seq<String> tokenized;
    private final String src;

    private int
    srclen,
    srcindex,
    srcline;

    private char curchar;

    public ACompiler(String src) {
        this.src = srcs;
        split = new Seq<>(srcs.split("\\s"));
        splitlen = split.length;
        srclen = src.length();
        code = new StringBuilder();
        tokenized = new Seq<>();
        index = 0;
    }

    public String compile() {
        while(srcindex < srclen) {
            int ex = handle();
            switch ex {
                case 1 -> return "EXCEPTION_STRING_INTERRUPTED_BY_END";
            }
        }

        return code.toString();
    }

    public int handle() {
        if(curchar == '\"') return handleString();
        updatecurchar();
    }

    // region HANDLERS

    public int handleString() {
        boolean inEscape = false;
        StringBuilder string = new StringBuilder();
        srcindex++;
        updatecurchar();
        while(!inEscape && charAtIndex() != '\"') {
            try {
                switch(curchar) {
                    case '\\' -> inEscape = true;
                    case 'n' -> {
                        if(inEscape) {
                            string.append('\n');
                        } else {
                            string.append('n');
                        }
                    };
                    default -> {
                        string.append(charAtIndex());
                        srcindex++;
                    }
                }
            } catch(OutOfBoundsException) {
                return 1;
            }
        }
    }

    // region UTILS

    public char charAtIndex() {
        return src.charAt(srcindex);
    }

    public void updatecurchar() {
        curchar = charAtIndex();
    }
}