package ambl.graphics;

import arc.*;
import ambl.graphics.elem.*;

public class APopupManager {
    private static APopup current;

    public static void warning(String text, float time) {
        closeCurrent();
    }
    public static void error(String text, float time) {
        closeCurrent();
    }
    public static void info(String text, float time) {
        closeCurrent();
    }

    public static void closeCurrent() {
        if(current == null) return;
    }
}