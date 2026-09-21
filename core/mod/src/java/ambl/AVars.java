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
        if(isValidCategory(name)) categories.put(name, new Category(name, formalName, color));
    }

    public static void removeCategory(String name) {
        categories.remove(name);
    }

    public static void addStruct(String name, Color color) {}

    private static boolean isValidCategory(String name) {
        return name != null || !name.isEmpty() ||name.matches("[\\w$]");
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