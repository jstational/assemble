package ambl;

import mindustry.mod.*;
import mindustry.*;
import arc.scene.ui.*;
import arc.struct.*;
import ambl.logic.*;

public class AssembleMod extends Mod {
    @Override
    public void init() {
        if(!(Vars.mobile || Vars.ios || Vars.android || Vars.testMobile)) {
            if(Vars.mods.getMod("LogicSugar") != null) {
                Vars.ui.logic = new ADialog();
            }
        }
    }
}