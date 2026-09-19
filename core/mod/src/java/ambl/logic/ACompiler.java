package ambl.logic;

import ambl.logic.cer.*;
import arc.struct.*;

public class ACompiler {
    public static IntMap<AInstruct> main;
    public static Seq<AJump> mainj;
    public static ObjectMap<String, AMethod> methods;
    public static ObjectMap<String, AStruct> structs;
}