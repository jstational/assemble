package ambl.logic;

import mindustry.logic.*;
import arc.scene.ui.*;
import arc.scene.ui.layout.*;
import arc.*;
import arc.scene.event.*;
import arc.input.*;
import ambl.logic.cer.*;
import arc.struct.*;

public class ACanvas extends Table { // this contains the context menus -> blocks -> jumps -> dot grid
    public ADragLayout world;
    public Table contextMenu; // only one shall be active at a time

    public ACanvas() {}

    public class ADragLayout extends WidgetGroup { // this contains the blocks -> jumps
        
    }

    public class ABlock extends Table {
        public ABlock() {
            Core.scene.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, KeyCode button) {
                    if(button == KeyCode.mouseLeft) {} // drag or link
                    if(button == KeyCode.mouseRight) {} // open context menu

                    return false;
                }
            });
        }
    }

    public void rebuild() {}

    public static boolean isCompact() {
        return Core.graphics.getWidth() < Scl.scl(900f) * 1.2f;
    }

    public String save() {
        return "";
    }
}