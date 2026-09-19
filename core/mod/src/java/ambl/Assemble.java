package ambl;

import mindustry.mod.*;
import mindustry.*;
import arc.scene.ui.*;
import ambl.logic.*;
import ambl.logic.cer.*;

public class Assemble extends Mod {
    @Override
    public void init() {
        if(!(Vars.mobile || Vars.ios || Vars.android || Vars.testMobile)) {
            Vars.ui.logic = new ADialog();
        }
    }
}