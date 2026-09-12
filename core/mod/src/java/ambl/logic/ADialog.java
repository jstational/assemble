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
import arc.scene.event.*;

public class ADialog extends LogicDialog { // this contains the buttons -> lists -> canvas
    public AVarsList vars;
    public AClassList classes;
    public AFunctList functs;
    public AImportList imports;
    
    public ACanvas editor;

    public ADialog() {
        editor = new ACanvas();

        clearChildren();

        hidden(() -> {
            if(Vars.state.isGame() && !Vars.net.active() && !wasPaused){
                Vars.state.set(GameState.State.playing);
            }
            Sounds.uiBack.play(); // Sounds is a generated class
        });

        shown(this::setup); // void shown(Runnable) in Dialog
        shown(() -> {
            if(Vars.state.isGame() && !Vars.net.active()){
                wasPaused = Vars.state.is(GameState.State.paused); // wasPaused is from BaseDialog
                Vars.state.set(GameState.State.paused);
            }
        });

        Core.scene.addListener(new InputListener() {});
    }

    public void setup() {
        buttons.clearChildren();
        buttons.defaults().size(140f, 40f);

        buttons.button("@back", Icon.left, () -> { // default behavior: compile with sugar 
            hide();
        }).name("back"); // hide() is inherited from Dialog, Icon is a generated class
    }
}