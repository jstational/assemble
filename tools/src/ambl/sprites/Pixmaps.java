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
        for(Pixmap cur : maps) {
            if(cur.width > width) width = cur.width;
            height = height = cur.height;
        }
    }

    public int getTotalWidth(Pixmap[] maps) {
        int w = 0;
        for(Pixmap cur : maps) {
            w = w + cur.width;
        }
        return w;
    }

    public int getTotalHeight(Pixmap[] maps) {
        int h = 0;
        for(Pixmap cur : maps) {
            h = h + cur.height;
        }
        return h;
    }

    public int getGreatestWidth(Pixmap[] maps) {
        int h = 0;
        for(Pixmap cur : maps) {
            if(cur.width > h) h = cur;
        }
        return h;
    }

    public int getGreatestHeight(Pixmap[] maps) {
        int h = 0;
        for(Pixmap cur : maps) {
            if(cur.height > h) h = cur;
        }
        return h;
    }
}
