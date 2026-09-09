package ambl.logic.blocks;

import arc.scene.ui.layout.*;
import arc.struct.*;
import mindustry.logic.*;
import ambl.logic.cer.*;

public class ABlock extends Table {
    public float x, y, width, height;

    public Seq<String> args;
    public IntSet comps; // components are for other kinds of instructions
    public int id;

    public AInstruct build() {
        r = new AInstruct();

        r.name = "block";
        r.args = args;
    }
}