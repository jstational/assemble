package ambl;

import arc.struct.*;
import ambl.logic.cer.*;
import ambl.logic.er.*;
import arc.graphics.*;
import ambl.graphics.*;

public class AVars {
    private static Seq<ABlockConstructor> blocks = new Seq<>();
    private static Seq<AStruct> structs = new Seq<>();
    private static ObjectMap<String, AMethod> functs = new ObjectMap<>();
    private static ObjectMap<String, Category> categories = new ObjectMap<>();

    public static void addCategory(String name, String formalName, Color color) {
        if(name.isEmpty() || name == null) return;
        categories.put(name, new Category(name, formalName, color));
    }

    public static void addCategory(String name, String formalName) {
        if(name.isEmpty() || name == null) return;
        addCategory(name, formalName, APal.unknown);
    }

    public static void addCategory(String name) {
        if(name.isEmpty() || name == null) return;
        addCategory(name, name.substring(0, 1).toUpperCase() + name.substring(1));
    }

    public static class Category {
        public String name, formalName;
        public Color color;

        public Category(String name, String formalName, Color color) {
            this.name = name;
            this.formalName = formalName;
            this.color = color;
        }
    }
}