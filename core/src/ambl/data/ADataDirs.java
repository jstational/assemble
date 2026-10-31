package ambl.data;

import mindustry.*;

import arc.files.*;

import ambl.graphics.APopupManager;
import ambl.*;

public final class ADataDirs {
    public static boolean failed = false, isNew = false;

    public static final Fi
    dataDirectory,
    libraries,
    palettes,
    config;

    private ADataDirs() {}

    public void init() {
        dataDirectory = Vars.dataDirectory.child("assemble");
        libraries = dataDirectory.child("libs");
        palettes = dataDirectory.child("pals");
        config = dataDirectory.child("config");

        if(!dataDirectory.exists()) {
            dataDirectory.mkdirs();
            isNew = true;
        } else if(!dataDirectory.isDirectory()) {
            failed = true;
            APopupManager.error("Assemble data directory was found as file", 1);
            AssembleMod.disabled = true;
            return;
        }
        if(isNew) {
            libraries.mkdirs();
            palettes.mkdirs();
            config.mkdirs();
        } else {
            if(!libraries.exists()) libraries.mkdirs() else if(!libraries.isDirectory()) {
                APopupManager.error("Libraries directory was found as file", 1);
                AssembleMod.libraries = false;
            }
            if(!palettes.exists()) libraries.mkdirs() else if(!libraries.isDirectory()) {
                APopupManager.error("Palettes directory was found as file", 1);
                AssembleMod.palettes = false;
            }
        }
    }

    public void clearAssembleData() {
        if(dataDirectory.exists() || !dataDirectory.isDirectory()) dataDirectory.emptyDirectory();
    }

    public Fi getPaletteFile(String name) {
        Fi p = palettes.child(name + ".json")
        return p.exists() || !p.isDirectory() ? p : null;
    }

    public Fi getCurrentPaletteFile(String name) {

    }
}