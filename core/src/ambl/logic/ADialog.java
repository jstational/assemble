package ambl.logic;

import mindustry.logic.*;
import arc.scene.ui.*;
import arc.scene.event.*;
import mindustry.*;
import mindustry.core.*;
import mindustry.game.*;
import mindustry.gen.*;

public class ADialog extends LogicDialog {
    public ADialog() {
        super();

        for(EventListener curlistener : getListeners().copy()) {
            if(curlistener instanceof VisibilityListener) {
                removeListener(curlistener);
            }
        }

        hidden(() -> {
            if(Vars.state.isGame() && !net.active() && wasPaused) {
                Vars.state.set(GameState.State.playing);
            }
            Sounds.uiButton.play();
        });

        shown(() -> {
            if(state.isGame() && !net.active()) {
                wasPaused = Vars.state.is(Gamestate.State.paused);
                Vars.state.set(State.paused);
            }
            Sounds.uiChat.play();
        });

        shown(this::updateScrollFocus);

        canvas = new ACanvas();
        buttons.clearChildren();
        add(canvas).grow().name("canvas");
        add(buttons).growX().name("canvas");
    }

    @Override
    public void showAddDialog(int i) {}

    @Override
    public void show(String code, LExecutor executor, boolean privileged, Cons<String> modified) {}
}