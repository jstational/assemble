package ambl.logic;

import mindustry.logic.*;
import arc.input.*;
import arc.scene.ui.layout.*;
import mindustry.graphics.*;
import ambl.logic.lists.*;
import mindustry.gen.*; // this is for the Sounds class
import mindustry.core.*; // for the GameState class which also has a State enum
import mindustry.*;

public class ADialog extends LogicDialog {
    public AVarsList vars;
    public AClassList classes;
    public WidgetGroup editor;

    public ADialog() {
        editor = new ACanvas();

        clearChildren();

        hidden(() -> {
            if(Vars.state.isGame() && !Vars.net.active() && !wasPaused){
                Vars.state.set(State.playing);
            }
            Sounds.uiBack.play(); // Sounds is a generated class
        });

        shown(this::setup); // void shown(Runnable) in Dialog
        shown(() -> {
            if(Vars.state.isGame() && !Vars.net.active()){
                wasPaused = Vars.state.is(State.paused); // wasPaused is from BaseDialog
                Vars.state.set(State.paused);
            }
        });
    }

    @Override
    public void setup() {
        buttons.clearChildren();
        buttons.defaults().size(140f, 40f);

        buttons.button("@back", Icon.left, () -> { // default behavior: compile with sugar 
            hide();
        }).name("back"); // hide() is inherited from Dialog, Icon is a generated class
    }
}