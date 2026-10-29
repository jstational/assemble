package ambl.data;

import arc.files.*;
import mindustry.*;

public final class ADataDirs {
    public static boolean shouldMakeDirs = true;

    public static final Fi
    dataDirectory = Vars.dataDirectory.child("assemble"),
    libraries = dataDirectory.child("libs"),
    palettes = dataDirectory.child("pals"),
    config = dataDirectory.child("config");

    private ADataDirs() {}

    public void init() {
        if(shouldMakeDirs) {
            dataDirectory.mkdirs();
            libraries.mkdirs();
            palettes.mkdirs();
            config.mkdirs();
        }
    }

    public void clearAssembleData() {
        dataDirectory.emptyDirectory();
    }

    public Fi getPaletteFileByName(String name) {
        return palettes.child(name + ".json");
    }
}