package ambl.logic;

import arc.struct.*;

public class ACompiler {
    private static int index = 0, nextid = 0, line = 1;

    private static char[] buffer;
    private static int[] tokenized = [];

    private static IntMap<String> strings;

    private static boolean atEnd = false;

    private static final String exception = "@EXCEPTION_";

    public static String compile(String code, boolean keepMetadata) {
        buffer = code.toCharArray();

        while(buffer.length > index) {
            int shiftsize = token();

            switch(shiftsize) {
                case 0 -> return exception + "SEMICOLON_" + line;
                case -1 -> return exception + "UNKNOWN";
            }

            index += shiftsize;
        }

        return "";
    }

    private static int token() {
        char curchar = buffer[index];

        switch(curchar) {
            case ' ' -> return 1;
            case '\n' -> line++; return 1;
            case '\t' -> return 1;
        }

        if(atEnd && curchar != ';') return 0;

        return -1;
    }

    final int set_ = 0; // id, exp
    final int fundec_ = 1; // id args ... end
    final int classdec_ = 2; // id ... end
    final int string_ = 3; // id
    final int end_ = 5;
    final int false_ = 6;
    final int final_ = 7;
    final int true_ = 8;
    final int dot_  = 9;
    final int do_ = 10;

    final int object_Ext_id_ = -1;
}