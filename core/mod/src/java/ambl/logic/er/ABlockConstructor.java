package ambl.logic.er;

import arc.struct.*;
import arc.graphics.*;
import ambl.logic.cer.*;
import ambl.logic.*;
import ambl.logic.ACanvas.*;

public class ABlockConstructor {
    public String name; // the internal name of the instruction
    public int maxComps;
    public String compCategory; // components must be this category
    public int args;
    public Color color;
    public String formalName; // the name of the instruction when displayed
    public String category;

    public ABlockConstructor(String name, String formal, int cs, int as, Color col, String cate) {
        this.name = name;
        maxComps = cs;
        args = as;
        color = col;
        this.formalName = formal;
        category = cate;
    }
}