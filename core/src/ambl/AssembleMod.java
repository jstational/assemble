package ambl;

import mindustry.mod.*;
import mindustry.Vars;
import mindustry.ui.dialogs.*;

import arc.scene.ui.*;
import arc.struct.*;
import arc.scene.style.*;
import arc.*;
import arc.files.Fi;

import ambl.data.*;
import ambl.graphics.*;
import ambl.logic.*;

public final class AssembleMod extends Mod {
    public static final int version = 1;

    public static boolean
    disabled = false,
    libraries = true,
    palettes = true;

    private final Seq<String> incompatibleMods = Seq.with(
        "LogicSugar"
    );

    @Override
    public void init() {
        Vars.ui.settings.addCategory("Assemble", new TextureRegionDrawable(Core.atlas.find("assemble")), table -> {
            
        });

        if(Vars.mobile || Vars.ios || Vars.android || Vars.testMobile || Vars.headless) {
            APopupManager.error("Assemble could not initialize, you are playing on a mobile or headless device", 1);
            disabled = true;
            return;
        }

        for(String curmod : incompatibleMods) {
            if(Vars.mods.getMod(curmod) != null) {
                APopupManager.error("Assemble could not initialize, incompatible mods are active. See list of incompatible mods in settings", 1);
                disabled = true;
                return;
            }
        }

        Vars.ui.logic = new ADialog();
        ADataDirs.init();
        ALocalSaves.init();
    }
}