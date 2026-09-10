package ambl.logic.cer;

import arc.struct.*;
import ambl.logic.blocks.*;

public class ACompiler {
    public Seq<AInstruct> main;
    public Seq<AJump> mainj;

    public static Seq<ABlockConstructor> types;

    public ACompiler() {}
}