package ambl.logic.cer;

import arc.struct.*;

public class AMethod {
    public String name;
    public IntMap<AInstruct> main;
    public Seq<AJump> mainj;

    public AMethod() {
        main = new Seq<>();
        mainj = new Seq<>();
    }

    public void addInstruct(AInstruct instruct) {
        main.put(instruct.id, instruct);
    }

    public void removeInstruct(int id) {
        main.remove(id);
    }

    public void addJump(int start, int end) {
        if(!main.containsKey(start) || !main.containsKey(end)) return;
        mainj.add(new AJump(start, end));
    }
}