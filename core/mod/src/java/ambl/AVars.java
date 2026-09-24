package ambl;

import arc.struct.*;
import ambl.logic.cer.*;
import ambl.logic.er.*;
import arc.graphics.*;
import ambl.graphics.*;

public class AVars {
    private static Seq<ABlockConstructor> blocks = new Seq<>();
    private static ObjectMap<String, AStruct> structs = new ObjectMap<>();
    private static ObjectMap<String, Category> categories = new ObjectMap<>();

    public static void addCategory(String name, String formalName, Color color) { // category$subcategory
        if(isValidInternalName(name)) categories.put(name, new Category(name, formalName, color));
    }

    public static void removeCategory(String name) {
        categories.remove(name);
    }

    public static void addStruct(String name) {
        if(isValidInternalName(name)) structs.put(name, new AStruct(name));
    }

    public static void removeStruct(String name) {
        structs.remove(name);
    }

    public static void addMethodtoStruct(String struct, AMethod method) { // methodName$overload
        structs.get(struct).addMethod(method);
    }

    public static void removeMethodfromStruct(String name) {
        structs.get(name).removeMethod(name);
    }

    private static boolean isValidInternalName(String name) { // the user should not be able to make internal names with $ signs, maybe stop this when typing?
        return name != null || !name.isEmpty() || name.matches("[\\w$]");
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