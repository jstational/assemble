package ambl.logic;

import mindustry.logic.*;
import arc.input.*;
import arc.scene.*;
import arc.scene.ui.layout.*;
import arc.*;
import mindustry.graphics.*;
import mindustry.gen.*; // this is for the Sounds class
import mindustry.core.*; // for the GameState class which also has a State enum
import mindustry.*;
import arc.scene.ui.*;
import arc.scene.event.*;
import arc.util.*;
import ambl.logic.cer.*;
import ambl.logic.er.*;
import arc.struct.*;

public class ADialog extends LogicDialog { // this contains the buttons -> lists -> canvas
    public StringList vars; // this should also contain the links
    public StringList classes;
    public StringList functs;
    
    public ACanvas editor;

    public ADialog() {
        super();

        editor = new ACanvas();
        vars = new StringList();
        classes = new StringList();
        functs = new StringList();

        clearChildren();

        add(buttons).growX().name("canvas");

        shown(this::setup);
    }

    private void setup() {
        buttons.clearChildren();
        buttons.defaults().size(140f, 40f);

        buttons.button("@back", Icon.left, () -> { // default behavior: compile with sugar 
            hide();
        }).name("back"); // hide() is inherited from Dialog, Icon is a generated class
    }

    public class ACanvas extends Table {
        public ADragLayout world;
        public Table contextMenu; // only one shall be active at a time
        public boolean linking;

        public ACanvas() {}

        public void rebuild() {}

        public static boolean isCompact() {
            return Core.graphics.getWidth() < Scl.scl(900f) * 1.2f;
        }

        public String save() { // TODO
            return "";
        }

        public class ABlock extends Table {
            public ABlockConstructor type;

            public ABlock() {
                Core.scene.addListener(new InputListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, KeyCode button) {
                        if(button == KeyCode.mouseLeft) {
                            if(linking) {
                            
                            } else {

                            }
                        } // drag or link
                        if(button == KeyCode.mouseRight) {} // open context menu

                        return false;
                    }
                });
            }
        }

        public class ADragLayout extends WidgetGroup {}
    }

    public static class StringList extends Table {
        public StringList() {
            super();
        }

        public void addItem(String item, String formal) {} // items and subitems are parent$name

        public void removeItem(String item) {}
    }
}