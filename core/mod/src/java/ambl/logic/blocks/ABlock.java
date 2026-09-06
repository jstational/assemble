package ambl.logic.blocks;

import arc.scene.ui.layout.*;
import arc.struct.*;

public class ABlock extends Table {
    public Seq<AJumpLine> jumps;
    public AJumpLine jump;

    public float x, y, width, height;

    public ABlock() {}
}