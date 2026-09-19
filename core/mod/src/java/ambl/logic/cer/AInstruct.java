package ambl.logic.cer;

import arc.struct.*;
import ambl.logic.er.*;

public class AInstruct {
    public String name;
    public Seq<String> args;
    public int id;
    public IntSet comps;

    public ABlockConstructor type;
    
    public AInstruct() {}
}