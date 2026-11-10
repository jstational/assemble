package ambl.graphics;

import arc.scene.ui.layout.*;
import arc.scene.ui.*;
import arc.struct.*;
import arc.func.*;
import arc.Core;

public class Popup extends Table {
    private Image image;
    private Label text;
    private Seq<Button> buttons;

    public Popup(Drawable logo, String msg, Cons<Popup> create, ObjectMap<String, Cons<Popup>> buttons) {}

    public Popup(String logo, String msg, Cons<Popup> create, ObjectMap<String, Cons<Popup>> buttons) {
        this(Image(Core.atlas.find(logo)), msg, create, buttons);
    }

    public Popup(Drawable logo, String msg, ObjectMap<String, Cons<Popup>> buttons) {
        this(logo, msg, null, buttons);
    }

    public Popup(String logo, String msg, ObjectMap<String, Cons<Popup>> buttons) {
        this(Image(Core.atlas.find(logo)), msg, buttons);
    }

    public Popup(Drawable logo, String msg, Cons<Popup> create) {
        this(logo, msg, create, null);
    }

    public Popup(String logo, String msg, Cons<Popup> create) {
        this(Image(Core.atlas.find(logo)), msg, create);
    }

    public Popup(Drawable logo, String msg) {
        this(logo, msg, null);
    }

    public Popup(String logo, String msg) {
        this(Image(Core.atlas.find(logo)), msg);
    }
}