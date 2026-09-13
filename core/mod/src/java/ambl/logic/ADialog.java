package ambl.logic;

import mindustry.logic.*;
import arc.input.*;
import arc.scene.*;
import arc.scene.ui.layout.*;
import arc.*;
import mindustry.graphics.*;
import ambl.logic.lists.*;
import mindustry.gen.*; // this is for the Sounds class
import mindustry.core.*; // for the GameState class which also has a State enum
import mindustry.*;
import arc.scene.ui.*;
import arc.scene.event.*;
import arc.util.*;

public class ADialog extends LogicDialog { // this contains the buttons -> lists -> canvas
    public AVarsList vars;
    public AClassList classes;
    public AFunctList functs;
    public AImportList imports;
    
    public ACanvas editor;

    public ADialog() {
        super();

        editor = new ACanvas();
        vars = new AVarsList();
        classes = new AClassList();
        functs = new AFunctList();
        imports = new AImportList();

        add(editor).grow().name("canvas"); // test
        add(vars).growY().name("vars"); // left
        add(classes).growY().name("classes"); // left
        add(functs).growX().name("functs"); // up
        add(imports).growY().name("imports"); // right

        row();

        add(buttons).growX().name("canvas");
    }

    private void setup() {
        buttons.clearChildren();
        buttons.defaults().size(140f, 40f);

        buttons.button("@back", Icon.left, () -> { // default behavior: compile with sugar 
            hide();
        }).name("back"); // hide() is inherited from Dialog, Icon is a generated class
    }
}