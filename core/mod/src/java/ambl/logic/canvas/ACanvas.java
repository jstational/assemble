package ambl.logic.canvas;

import mindustry.logic.*;
import arc.scene.ui.*;
import arc.*;
import ambl.logic.*;
import arc.scene.event.*;
import arc.input.*;
import ambl.logic.blocks.*;

public class ACanvas extends Table {
    public boolean dragging;
    public ABlock draggingBlock;
    public WidgetGroup editorLayout;

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
                }
            }
        });

        rebuild();
    }

    @Override
    public void rebuild() {}
}