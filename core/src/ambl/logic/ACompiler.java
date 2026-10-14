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
    private boolean canReturn;

    public ACompiler(String src) {
        this.src = src;
        srclen = src.length();
        code = new StringBuilder();
        tokenized = new Seq<>();
        srcindex = 0;
        if(srclen > 0) {
            updatecurchar();
        }
        canReturn = false;
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
                    return "EXCEPTION_UNKNOWN_TOKEN@" + srcline;
                }
                case 3 -> {
                    return "EXCEPTION_STRING_INTERRUPTED_BY_NEWLINE@" + srcline;
                }
                case 4 -> {
                    return "EXCEPTION_SEMICOLON@" + srcline;
                }
                case 5 -> {
                    return "EXCEPTION_CANNOT_RETURN@" + srcline;
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

        if(curchar == '\t') {
            srcline++;
            if(!nextchar()) return -1;
            return 0;
        }

        if(curchar == ' ') {
            srcindex++;
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
        if(keyword("return", return_)) {
            if(!canReturn) {
                return 5;
            }
            return handleExpression();
        }
        if(keywordNoSpace("if", if_)) return handleExpression();

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
        token(expr_);
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
        if(startsWithAtIndex(str + " ")) {
            token(add);
            srcindex += str.length() + 1;
            return true;
        }
        return false;
    }

    public boolean keywordNoSpace(String str, String add) {
        if(startsWithAtIndex(str)) {
            token(add);
            srcindex += str.length() + 1;
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
    num_ = "NUMBER",
    char_ = "CHARACTER",

    private_ = "PRIVATE",
    public_ = "PUBLIC",
    pointer_ = "POINTER",
    readonly_ = "READ_ONLY",
    static_ = "STATIC",
    final_ = "FINAL",

    if_ = "IF",
    expr_ = "EXPRESSION",
    return_ = "RETURN",
    type_ = "TYPE",
    generictype_ = "GENERIC_TYPE",
    generictype_end_ = "GENERIC_TYPE_END",
    returntype_ = "RETURNTYPE",

    functdec_ = "FUNCTION_DEC",
    functbody_ = "FUNCTION_BODY",
    functbody_end_ = "FUNCTION_BODY_END",

    paramdec_ = "PARAMETER_DEC",
    paramdec_end = "PARAMETER_DEC_END",

    classdec_ = "CLASS_DEC",
    classbody_ = "CLASS_BODY",
    classbody_end_ = "CLASS_BODY_END",

    genericdec_ = "GENERIC_DEC",
    genericdec_end_ = "GENERIC_DEC_END",

    vardec_ = "VARIABLE_DEC",

    packagedec_ = "PACKAGE_DEC",
    packagebody_ = "PACKAGE_BODY",
    packagebody_end_ = "PACKAGE_BODY_END",

    interfacedec_ = "INTERFACE_DEC",
    interfacebody_ = "INTERFACE_BODY",
    interfacebody_end_ = "INTERFACE_BODY_END",
    
    lang_int_ = "int",
    lang_dec_ = "dec",
    lang_bool_ = "bool",
    lang_null_ = "null",
    lang_constarray = "ConstArray",
    lang_conststring = "ConstString",
    lang_color = "Color",
    std_char = "$std.Char",
    std_id = "$std.ID",
    std_content = "$std.Content";
}