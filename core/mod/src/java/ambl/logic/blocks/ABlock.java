package ambl.logic.blocks;

import arc.scene.ui.layout.*;
import arc.struct.*;
import mindustry.logic.*;
import java.util.function.Supplier;

import static mindustry.logic.LStatements.*;

public class ABlock extends Table {
    public float x, y, width, height;

    public String arg;
    public Seq<String> args;

    public ABlock() {}
}