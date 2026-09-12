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

public class ADialog extends LogicDialog { // this contains the buttons -> lists -> canvas
    public AVarsList vars;
    public AClassList classes;
    public AFunctList functs;
    public AImportList imports;
    
    public ACanvas editor;

    public ADialog() {
        style = Core.scene.getStyle(DialogStyle.class);

        this.touchable = Touchable.enabled;
        setClip(true);
        this.title = new Label("logic", new Label.LabelStyle(style.titleFont, style.titleFontColor));
        this.title.setEllipsis(true);
        titleTable = new Table();
        titleTable.add(this.title).expandX().fillX().minWidth(0);
        add(titleTable).growX().row();
        setStyle(style);
        setWidth(150);
        setHeight(150);

        addCaptureListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, KeyCode button){
                toFront();
                return false;
            }
        });

        addListener(new InputListener(){
            float startX, startY, lastX, lastY;

            private void updateEdge(float x, float y){
                float border = resizeBorder / 2f;
                float width = getWidth(), height = getHeight();
                float padTop = getMarginTop(), padRight = getMarginRight();
                float right = width - padRight;
                edge = 0;
                if(isResizable && x >= getMarginLeft() - border && x <= right + border && y >= getMarginBottom() - border){
                    if(x < getMarginLeft() + border) edge |= Align.left;
                    if(x > right - border) edge |= Align.right;
                    if(y < getMarginBottom() + border) edge |= Align.bottom;
                    if(edge != 0) border += 25;
                    if(x < getMarginLeft() + border) edge |= Align.left;
                    if(x > right - border) edge |= Align.right;
                    if(y < getMarginBottom() + border) edge |= Align.bottom;
                }
                if(isMovable && edge == 0 && y <= height && y >= height - padTop && x >= getMarginLeft() && x <= right)
                    edge = MOVE;
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, KeyCode button){
                if(button == KeyCode.mouseLeft){
                    updateEdge(x, y);
                    dragging = edge != 0;
                    startX = x;
                    startY = y;
                    lastX = x - getWidth();
                    lastY = y - getHeight();
                }
                return edge != 0 || isModal;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, KeyCode button){
                dragging = false;
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer){
                if(!dragging) return;
                float width = getWidth(), height = getHeight();
                float windowX = Dialog.this.x, windowY = Dialog.this.y;

                float minWidth = getMinWidth();
                float minHeight = getMinHeight();
                Scene stage = getScene();
                boolean clampPosition = keepWithinStage && parent == stage.root;

                if((edge & MOVE) != 0){
                    float amountX = x - startX, amountY = y - startY;
                    windowX += amountX;
                    windowY += amountY;
                }
                if((edge & Align.left) != 0){
                    float amountX = x - startX;
                    if(width - amountX < minWidth) amountX = -(minWidth - width);
                    if(clampPosition && windowX + amountX < 0) amountX = -windowX;
                    width -= amountX;
                    windowX += amountX;
                }
                if((edge & Align.bottom) != 0){
                    float amountY = y - startY;
                    if(height - amountY < minHeight) amountY = -(minHeight - height);
                    if(clampPosition && windowY + amountY < 0) amountY = -windowY;
                    height -= amountY;
                    windowY += amountY;
                }
                if((edge & Align.right) != 0){
                    float amountX = x - lastX - width;
                    if(width + amountX < minWidth) amountX = minWidth - width;
                    if(clampPosition && windowX + width + amountX > stage.getWidth())
                        amountX = stage.getWidth() - windowX - width;
                    width += amountX;
                }
                if((edge & Align.top) != 0){
                    float amountY = y - lastY - height;
                    if(height + amountY < minHeight) amountY = minHeight - height;
                    if(clampPosition && windowY + height + amountY > stage.getHeight())
                        amountY = stage.getHeight() - windowY - height;
                    height += amountY;
                }
                setBounds(Math.round(windowX), Math.round(windowY), Math.round(width), Math.round(height));
            }

            @Override
            public boolean mouseMoved(InputEvent event, float x, float y){
                updateEdge(x, y);
                return isModal;
            }

            @Override
            public boolean scrolled(InputEvent event, float x, float y, float amountX, float amountY){
                return isModal;
            }

            @Override
            public boolean keyDown(InputEvent event, KeyCode keycode){
                return isModal;
            }

            @Override
            public boolean keyUp(InputEvent event, KeyCode keycode){
                return isModal;
            }

            @Override
            public boolean keyTyped(InputEvent event, char character){
                return isModal;
            }
        });

        setOrigin(Align.center);
        defaults().pad(3);
        add(cont = new Table()).expand().fill();
        row();
        add(buttons = new Table()).fillX();

        cont.defaults().pad(3);
        buttons.defaults().pad(3);

        focusListener = new FocusListener(){
            @Override
            public void keyboardFocusChanged(FocusEvent event, Element actor, boolean focused){
                if(!focused) focusChanged(event);
            }

            @Override
            public void scrollFocusChanged(FocusEvent event, Element actor, boolean focused){
                if(!focused) focusChanged(event);
            }

            private void focusChanged(FocusEvent event){
                Scene stage = getScene();
                if(isModal && stage != null && stage.root.getChildren().size > 0
                && stage.root.getChildren().peek() == Dialog.this){ // Dialog is top most actor.
                    Element newFocusedActor = event.relatedActor;
                    if(newFocusedActor != null && !newFocusedActor.isDescendantOf(Dialog.this) &&
                    !(newFocusedActor.equals(previousKeyboardFocus) || newFocusedActor.equals(previousScrollFocus)))
                        event.cancel();
                }
            }
        };

        shown(this::updateScrollFocus);

        clearChildren();
        setFillParent(true);
        this.title.setAlignment(Align.center);
        titleTable.row();
        titleImage = titleTable.image(Tex.whiteui, Pal.accent).growX().height(3f).pad(4f).get();

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

        editor = new ACanvas();

        addCloseListener();

        shown(() -> {
            wasRows = ACanvas.isCompact();
            wasPortrait = Core.graphics.isPortrait();
        });
        hidden(() -> consumer.get(editor.save()));
        onResize(() -> {
            if(wasRows != ACanvas.isCompact() || wasPortrait != Core.graphics.isPortrait()){
                setup();
                editor.rebuild();
                wasPortrait = Core.graphics.isPortrait();
                wasRows = ACanvas.isCompact();
            }
        });

        add(editor).grow().name("editor");

        row();

        add(buttons).growX().name("editor");
    }

    public void setup() {
        buttons.clearChildren();
        buttons.defaults().size(140f, 40f);

        buttons.button("@back", Icon.left, () -> { // default behavior: compile with sugar 
            hide();
        }).name("back"); // hide() is inherited from Dialog, Icon is a generated class
    }
}