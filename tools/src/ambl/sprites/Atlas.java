package ambl.sprites;

import java.util.HashMap;

public class Atlas {
    HashMap<String, Pixmap> regions;
    int regionc;

    public Atlas() {
        regions = new HashMap<>();
        regionc = 0;
    }

    public void put(String name, Pixmap map) {
        if(name == null || map == null) return;
        if(regions.put(name, map) != null) {
            regionc++;
        }
    }

    public void remove(String name) {
        if(name == null) return;
        if(regions.remove(name) != null) {
            regionc--;
        }
    }
}
