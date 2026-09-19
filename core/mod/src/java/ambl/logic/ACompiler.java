package ambl.logic;

import arc.struct.*;

public class ACompiler {
    public static IntMap<AInstruct> main;
    public static Seq<AJump> mainj;
    public static ObjectMap<String, AMethod> methods;
    public static Seq<String, AStruct> structs;
}