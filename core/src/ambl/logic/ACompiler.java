package ambl.logic;

import arc.struct.*;

/***
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
        this.src = src;
        srclen = src.length();
        code = new StringBuilder();
        tokenized = new Seq<>();
        srcindex = 0;
        if(srclen > 0) {
            updatecurchar();
        }
    }

    public String compile() {
        if(srclen == 0) {
            return "";
        }

        int ex = 0;

        while(ex != -1) {
            ex = handle();
            switch(ex) {
                case 1 -> {
                    return "EXCEPTION_STRING_INTERRUPTED_BY_END@" + srcline;
                }
                case 2 -> {
                    return "EXCEPTION_UNKNOWN_TOKEN";
                }
                case 3 -> {
                    return "EXCEPTION_STRING_INTERRUPTED_BY_NEWLINE@" + srcline;
                }
                case 4 -> {
                    "EXCEPTION_SEMICOLON@" + srcline;
                }
            }
        }

        return code.toString();
    }

    public int handle() {
        if(curchar == '\"') {
            if(!nextchar()) return 1;
            token(str_);
            return handleString();
        }

        if(curchar == '\n') {
            srcline++;
            if(!nextchar()) return -1;
            return 0;
        }

        if(curchar == ' ' || curchar == '\t') {
            srcline++;
            if(!nextchar()) return -1;
            return 0;
        }

        if(Character.isDigit(curchar)) {
            token(num_);
            return handleNumber();
        }

        if(keyword("class", class_)) return 0;
        if(keyword("public", pub_)) return 0;
        if(keyword("private", priv_)) return 0;
        if(keyword("final", final_)) return 0;
        if(keyword("interface", interface_)) return 0;
        if(keyword("static", static_)) return 0;

        return 2;
    }

    // region HANDLERS

    public int handleString() {
        StringBuilder string = new StringBuilder();

        while(curchar != '\"') { // this handles the case where the string is just empty
            if(curchar == '\n') return 3;

            if(curchar == '\\') {
                string.append('\\');
                string.append(curchar);
            }

            if(!nextchar()) return 1;
        }

        token(string);
        return 0;
    }

    public int handleNumber() {
        while(Character.isDigit(curchar)) {}
        return 0;
    }

    public int handleExpression() {
        return 0;
    }

    // region UTILS

    public boolean updatecurchar() {
        char c;
        try {
            c = src.charAt(srcindex);
        } catch(StringIndexOutOfBoundsException e) {
        return false;
        }
        curchar = c;
        return true;
    }

    public boolean nextchar() {
        srcindex++;
        return updatecurchar();
    }

    public boolean startsWith(String str, int offset) {
        return src.startsWith(str, offset);
    }

    public boolean startsWithAtIndex(String str) {
        return src.startsWith(str, srcindex);
    }

    public boolean keyword(String str, String add) {
        if(startsWithAtIndex(str)) {
            token(add);
            srcindex += str.length();
            return true;
        }
        return false;
    }

    public void token(String str) {
        tokenized.add(str);
    }

    public void token(StringBuilder str) {
        tokenized.add(str.toString());
    }

    // region KEYWORDS

    private final String
    str_ = "STRING",
    num_ = "NUMBER"
    class_ = "CLASS",
    interface_ = "INTERFACE",
    priv_ = "PRIVATE",
    pub_ = "PUBLIC",
    static_ = "STATIC",
    final_ = "FINAL";
}