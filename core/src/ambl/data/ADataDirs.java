package ambl.data;

import mindustry.*;

import arc.files.*;

import ambl.graphics.APopupManager;
import ambl.*;

public final class ADataDirs {
    private Fi dataDirectory, libraries, palettes, config;

    private ADataDirs() {}

    public void init() {
        boolean failed = false, isNew = false;

        dataDirectory = Vars.dataDirectory.child("assemble");
        libraries = dataDirectory.child("libs");
        palettes = dataDirectory.child("pals");
        config = dataDirectory.child("config")

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
            if(!config.exists()) libraries.mkdirs() else if(!libraries.isDirectory()) {
                APopupManager.error("Config directory was found as file", 1);
                AssembleMod.disabled = true;
                return;
            }
        }
    }

    public void clearAssembleData() {
        if(dataDirectory.exists() || !dataDirectory.isDirectory()) dataDirectory.emptyDirectory();
    }
}