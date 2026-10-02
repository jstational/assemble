package ambl.logic;

import arc.struct.*;

public class ACompiler {
    private static int
    index,
    nextvid, nextcid, nextfid, nextsid;

    private static char[] buffer;
    private static IntSeq tokenized = new IntSeq();

    private static IntMap<String> strings;

    private static boolean atEnd, inString, inEscape;

    private static StringBuilder currentString;

    public static String compile(String code, boolean keepMetadata) {
        reset(code.replaceAll("[\\t\\r\\n]", ""));

        while(buffer.length > index) {
            int shiftsize = token();

            switch(shiftsize) {
                case 0 -> {
                    return exception + "MISSING_SEMICOLON";
                }
                case -1 -> {
                    return exception + "PROCESSED_NONE";
                }
                case -2 -> {
                    return exception + "STRING_INTERRUPTED";
                }
            }

            index += shiftsize;
        }

        return "";
    }

    private static int token() {
        char curchar = buffer[index];

        switch(curchar) {
            case '.' -> {
                tokenized.add(dot_);
                return 1;
            }
            case '\"' -> {
                inString = true;

                while(inString) {
                    if(inEscape) {
                        switch(curchar) {
                            case '\"' -> {
                                currentString.append('\"');
                                inEscape = false;
                            }
                            case '\\' -> {
                                currentString.append('\\');
                                inEscape = false;
                            }
                            case 'n' -> {
                                currentString.append('\n');
                                inEscape = false;
                            }
                            default -> {
                                currentString.append('\\');
                                currentString.append(curchar);
                                inEscape = false;
                            }
                        }
                    } else {
                        switch(curchar) {
                            case '\"' -> {
                                inString = false;
                            }
                            case '\\' -> {
                                inEscape = true;
                            }
                            default -> {
                                currentString.append(curchar);
                            }
                        }
                    }
                    if(index >= buffer.length) {
                        return -2;
                    }
                    curchar = buffer[++index];
                }
            }
        }

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
        index = 0;
        nextvid = 0;
        nextfid = 0;
        nextsid = 0;
        nextcid = 0;
        buffer = code.toCharArray();
        tokenized = new IntSeq();
        strings = new IntMap<>();
        atEnd = false;
        inString = false;
        inEscape = false;
        currentString = new StringBuilder();
    }

    private static final String exception = "@EXCEPTION_";
}