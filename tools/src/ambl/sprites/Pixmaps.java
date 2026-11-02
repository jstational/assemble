package ambl.sprites;

public class Pixmaps {
    private Pixmaps() {}

    public void packx(Pixmap t, Pixmap[] maps) {
        int width = 0, height = 0;
        for(Pixmap cur : maps) {
            if(cur.height > height) height = cur.height;
            width = width + cur.width;
        }
        t.width = width;
        t.height = height;
        t.pixels = new int[width * height];
        t.alpha = true;

    }

    public void packy(Pixmap t, Pixmap[] maps) {
        int width = 0, height = 0;
        for(Pixmap cur : maps)
    }
}
