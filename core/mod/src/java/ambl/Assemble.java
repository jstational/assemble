package ambl;

import mindustry.mod.*;
import mindustry.*;
import arc.scene.ui.*;
import ambl.logic.*;
import ambl.logic.cer.*;

public class Assemble extends Mod {
    @Override
    public void init() {
        /** assemble isnt compatible with mobile because im on pc, even if i tried, ui would be garbage! */
        if(!(Vars.mobile || Vars.ios || Vars.android || Vars.testMobile)) {
            Vars.ui.logic = new ADialog();
        }
    }
}