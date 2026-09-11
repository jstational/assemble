package ambl.logic.er.*;

import arc.graphics.*;
import arc.struct.*;

public class ACategory {
    public Color col;
    public String name, formalName;
    public Seq<ASubcategory> sub;

    public ACategory(String na, String form, Color c) {
        name = na;
        formalName = form;
        col = c;
        sub = new Seq<>();
    }

    public void addSub(String i, String f) {
        sub.add(new ASubcategory(i, f));
    }

    public static class ASubcategory {
        public String name, formalName;

        public ASubcategory(String n, String fn) {
            this.name = n;
            this.formalName = fn;
        }
    }
}