package ambl;

import arc.struct.*;
import ambl.*;
import arc.graphics.*;

public class ACategories {
    public static ObjectMap<String, ACategory> cas;
    public static ObjectMap<String, String> subcas;

    public static void category(String name, String formalName, Color col) {
        cas.put(name, new ACategory(formalName, col));
    }

    public static void category(String name, String formalName, int r, int g, int b) {
        category(name, formalName, new Color(r, g, b));
    }

    public static void subcategory(String parent, String name) {
        subcas.put(parent, name);
    }

    public static class ACategory {
        public String formalName;
        public Color color;
        
        public ACategory(String name, int r, int g, int b) {
            this.formalName = name;
            this.color = new Color(r, g, b);
        }

        public ACategory(String name, Color col) {
            this.formalName = name;
            this.color = col;
        }

        public ACategory(String name) {
            this.formalName = name;
            this.color = APal.unknown;
        }
    }
}