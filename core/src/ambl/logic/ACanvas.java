package ambl.logic;

import mindustry.logic.*;
import arc.scene.ui.layout.*;
import arc.*;
import arc.input.*;
import arc.scene.event.*;

public class ACanvas extends LCanvas {
    public ATextEditor editor;

    public ACanvas() {
        editor = new ATextEditor();

        add(editor).grow().name("editor");
    }

    @Override
    public void rebuild() {}

    @Override
    public void load(String code) {}

    public class ATextEditor extends Table {
        private boolean isRaw;
        private String rawText;

        public ATextEditor() {
            isRaw = false;

            rawText = "";

            addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, KeyCode button) {
                    return false;
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, KeyCode button) {}
            });
        }

        public void load(String code) {
            isRaw = false;
        }

        public void loadRaw(String code) {
            isRaw = true;
        }

        public String getText() {
            return rawText;
        }
    }
}