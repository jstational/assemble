package ambl.logic;

import mindustry.logic.*;
import arc.scene.ui.*;
import arc.*;
import arc.scene.event.*;
import arc.input.*;
import ambl.logic.blocks.*;

public class ACanvas extends Table {
    public boolean dragging;
    public ABlock draggingBlock;
    public AJumpLine draggingJump;

    public ACanvas() {
        editorLayout = new ALayout();

        Core.scene.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent e, float x, float y, int p, KeyCode button) {
                if(button == KeyCode.mouseLeft) {
                    dragging = true;
                }

                return super.touchDown(e, x, y, p, button);
            }

            @Override
            public void touchUp(InputEvent e, float x, float y, int p, KeyCode button) {
                if(button == KeyCode.mouseLeft) {
                    dragging = false;

                    draggingBlock = null;
                }
            }
        });

        rebuild();
    }

    @Override
    public void rebuild() {}

    public void addBlock(ABlock block) {
        children.add(block);
    }

    public void removeBlock(ABlock block) {
        children.remove(block, false);
    }

    public void addJump(AJumpLine jump) {
        children.add(jump);
    }

    public void removeJump(AJumpLine jump) {
        children.remove(jump, false);
    }
}