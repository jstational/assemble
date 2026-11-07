package ambl.sprites;

public class Atlas {
    Pixmap[] pixmaps;
    int pixmapc;

    Atlas(Pixmap[] pixmaps) {
        this.pixmaps = pixmaps;
        this.pixmapc = pixmaps.length;
    }
}
