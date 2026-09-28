package ambl.logic;

import arc.struct.*;

public class ACompiler {
    private static int index, nextid, nextcid, line;

    private static char[] buffer;
    private static int[] tokenized;

    private static IntMap<String> strings;

    private static boolean atEnd;

    private static final String exception = "@EXCEPTION_";

    public static String compile(String code, boolean keepMetadata) {
        reset(code);

        while(buffer.length > index) {
            int shiftsize = token();

            switch(shiftsize) {
                case 0 -> {return exception + "SEMICOLON_" + line;}
                case -1 -> {return exception + "UNKNOWN";}
            }

            index += shiftsize;
        }

        return "";
    }

    private static int token() {
        char curchar = buffer[index];

        switch(curchar) {
            case ' ' -> {return 1;}
            case '\n' -> {line++; return 1;}
            case '\t' -> {return 1;}
        }
        if(curchar == '.') {
            return 1;
            tokenized.add(dot_);
        }

        if(atEnd && curchar != ';') return 0;

        return -1;
    }

    private static final int 
    set_=0, // vid exp
    import_=11, // sid vid

    // MODIFIERS
    final_=5,
    static_=6,
    private_=7,
    public_=8,
    default_=9,

    // DECLARATIONS
    vdec_=1, // vid cid
    fdec_=2, // fid cid vdec... end code... end
    cdec_=3, // cid code... end vdec... end fdec... end

    return_=4, // exp
    end_=10,
    exp_=12, // expparts... end
    dot_=14,

    // EXPRESSION PARTS
    start_=13; // expparts... end

    private static final int object_Ext_cid_ = -1;

    private static void reset(String code) {
        index = 0; nextid = 0; nextcid = 0; line = 1;
        buffer = code.toCharArray();
        tokenized = new int[100];
        strings = new IntMap<>();
        atEnd = false;
    }
}