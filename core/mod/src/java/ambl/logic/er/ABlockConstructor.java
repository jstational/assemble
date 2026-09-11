package ambl.logic.er;

import arc.struct.*;
import arc.graphics.*;

public class ABlockConstructor {
    public String name; // the internal name of the instruction
    public int maxComps; // 0: No comps, -1: Inf comps
    public int args; // 0: No args, -1: Inf args
    public Color color;
    public String formalName; // the name of the instruction when displayed
    public ACategory category;

    public ABlockConstructor(String name, String formal, int cs, int as, Color col, ACategory cate) {
        this.name = name;
        maxComps = cs;
        args = as;
        color = col;
        this.formalName = formal;
        category = cate;
    }
}