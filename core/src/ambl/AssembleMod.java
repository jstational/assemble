package ambl;

import mindustry.mod.*;
import mindustry.*;
import arc.scene.ui.*;
import arc.struct.*;
import ambl.logic.*;
import arc.files.*;
import ambl.data.*;
import mindustry.ui.dialogs.*;
import ambl.graphics.*;
import arc.scene.style.*;
import arc.*;

public final class AssembleMod extends Mod {
    public static final int version = 1;

    private final Seq<String> incompatibleMods = Seq.with(
        "LogicSugar"
    );

    @Override
    public void init() {
        Vars.ui.settings.addCategory("Assemble", new TextureRegionDrawable(Core.atlas.find("assemble")), table -> {
            
        });

        if(Vars.mobile || Vars.ios || Vars.android || Vars.testMobile || Vars.headless) {
            APopupManager.error("Assemble could not initialize, you are playing on a mobile or headless device", 1);
            return;
        }

        for(String curmod : incompatibleMods) {
            if(Vars.mods.getMod(curmod) != null) {
                APopupManager.error("Assemble could not initialize, incompatible mods are active. See list of incompatible mods in settings", 1);
            }
        }

        Vars.ui.logic = new ADialog();
    }

    @Override
    public Fi getConfigFolder() {
        return ADataDirs.dataDirectory;
    }
}