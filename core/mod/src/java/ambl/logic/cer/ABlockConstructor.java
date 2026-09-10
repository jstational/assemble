package ambl.logic.blocks;

import arc.struct.*;
import arc.graphics.*;

public class ABlockConstructor {
    public String name;
    public int maxComps; // 0: No comps, -1: Inf comps
    public int args; // 0: No args, -1: Inf args
    public Color color;

    public ABlockConstructor(String name, int cs, int as, int col) {
        this.name = name;
        maxComps = cs;
        args = as;
        color = Color(col);
    }
}