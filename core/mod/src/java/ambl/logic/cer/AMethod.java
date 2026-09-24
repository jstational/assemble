package ambl.logic.cer;

import arc.struct.*;

public class AMethod {
    public String name;
    public IntMap<AInstruct> main;
    public IntMap<AJump> mainj;

    public AMethod() {
        main = new IntMap<>();
        mainj = new IntMap<>();
    }

    public void addInstruct(AInstruct instruct) {
        main.put(instruct.id, instruct);
    }

    public void removeInstruct(int id) {
        main.remove(id);
    }

    public void addJump(int start, int end) {
        if(!main.containsKey(start) || !main.containsKey(end)) return;
        mainj.put(start, new AJump(start, end));
    }

    public void removeJump(int start) {
        mainj.remove(start);
    }
}