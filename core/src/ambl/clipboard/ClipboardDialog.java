package ambl.clipboard;

import arc.scene.ui.*;

public class ClipboardDialog extends Dialog {
    static ClipboardDialog dialog;

    public ClipboardDialog() {
        super("clipboard");
        dialog = this;
    }
}
