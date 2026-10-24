package ambl.logic;

import mindustry.logic.*;
import arc.scene.ui.layout.*;
import arc.*;
import arc.input.*;

public class ACanvas extends LCanvas {
    public ATextEditor editor;

    public ACanvas() {
        canvas = this;

        editor = new ATextEditor();
    }

    public class ATextEditor extends Table {
        public ATextEditor() {
            Core.scene.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, KeyCode button) {
                    return false;
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, KeyCode button) {}
            });
        }
    }
}