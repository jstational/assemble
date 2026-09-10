package ambl.logic;

import mindustry.logic.*;
import arc.scene.ui.*;
import arc.scene.ui.layout.*;
import arc.*;
import arc.scene.event.*;
import arc.input.*;
import ambl.logic.cer.*;
import arc.struct.*;

public class ACanvas extends Table {
    public boolean clicked;
    public ABlock draggingBlock;
    public Seq<AJump> jumps;
    public Seq<ABlock> blocks;
    public float originX, originY;

    public ACanvas() {
        Core.scene.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent e, float x, float y, int p, KeyCode button) {
                if(button == KeyCode.mouseLeft) {
                    clicked = true;

                    block = clickBlock(x, y);

                    if(block != null) {
                        
                    }
                }

                return super.touchDown(e, x, y, p, button);
            }

            @Override
            public void touchUp(InputEvent e, float x, float y, int p, KeyCode button) {
                if(button == KeyCode.mouseLeft) {
                    dragging = false;

                    if(draggingBlock != null) {
                        blocks.add(draggingBlock);

                        draggingBlock = null;

                        rebuild();
                    }
                }
            }
        });

        rebuild();
    }

    public void rebuild() {}

    // remove the block at x, y and return it
    public ABlock dragBlock(float x, float y) {
        return new ABlock();
    }

    public ABlock clickBlock(float x, float y) {
        return new ABlock();
    }

    public boolean hasBlock(float x, float y) {
        return false;
    }

    public static class ABlock {
        public AInstruct instruct;
        public float x, y, width, height;

        public ABlock() {}
    }
}