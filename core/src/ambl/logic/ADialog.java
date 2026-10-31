package ambl.logic;

import mindustry.logic.*;
import mindustry.*;
import mindustry.core.*;
import mindustry.game.*;
import mindustry.gen.*;

import arc.scene.ui.*;
import arc.scene.event.*;
import arc.func.Cons;

public class ADialog extends LogicDialog {
    public Cons<String> consumerf = str -> {};
    public boolean forceRecompile;

    public ADialog() {
        super();

        for(EventListener curlistener : getListeners().copy()) {
            if(curlistener instanceof VisibilityListener) {
                removeListener(curlistener);
            }
        }

        hidden(() -> {
            if(Vars.state.isGame() && !Vars.net.active() && wasPaused) {
                Vars.state.set(GameState.State.playing);
            }
            Sounds.uiButton.play();
        });

        shown(() -> {
            if(Vars.state.isGame() && !Vars.net.active()) {
                wasPaused = Vars.state.is(GameState.State.paused);
                Vars.state.set(GameState.State.paused);
            }
            Sounds.uiChat.play();
        });

        shown(this::updateScrollFocus);

        canvas = new ACanvas();
        buttons.clearChildren();
        add(canvas).grow().name("canvas");
        add(buttons).growX().name("canvas");

        forceRecompile = false;
    }

    @Override
    public void showAddDialog(int i) {}

    @Override
    public void show(String code, LExecutor executor, boolean privileged, Cons<String> modified) {
        consumerf = compiled -> {
            if(forceRecompile || !compiled.equals(code)) {
                modified.get(compiled);
            }
        };
        try {
            canvas.editor.load(code);
        } catch(Throwable ex) {
            canvas.editor.loadRaw(code);
        }

        show();
    }

    @Override
    public void hide() {
        if(canvas != null && canvas.editor != null) {
            consumerf.get(new ACompiler(canvas.editor.getText()).compile());
        }

        super.hide();
    }
}