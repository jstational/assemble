package ambl;

import arc.struct.*;
import ambl.logic.cer.*;
import ambl.logic.er.*;
import arc.graphics.*;

public class AVars {
    private static Seq<ABlockConstructor> blocks = new Seq<>();
    private static Seq<AStruct> structs = new Seq<>();
    private static ObjectMap<String, AMethod> functs = new Seq<>();

    private static Seq<String> internalCategories = new Seq<>(); // subcategories will be parent$name instead
    private static ObjectMap<String, Color> categoryColors = new ObjectMap<>();
    private static ObjectMap<String, String> formalCategories = new ObjectMap<>();
}