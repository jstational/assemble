package ambl.logic;

import arc.struct.*;

public class ACompiler {
    public String code = "";

    public String compile() {
        return "";
    }

    public String desugar() {
        return "";
    }

    public TokenTypes[] lex() {
        String ds = desugar();
        return new TokenTypes[5];
    }

    public enum TokenTypes {
        if
    }
}