package ambl.graphics.elem;

import arc.scene.ui.layout.*;
import arc.scene.ui.*;
import arc.struct.*;
import arc.func.*;

public class APopup extends Table {
    private Image image;
    private Label text;

    public APopup(Drawable drawable, String rt) {
        image = new Image(drawable);
        text = new Label(rt);
    }
}