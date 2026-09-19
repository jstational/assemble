package ambl.logic.cer;

import arc.struct.*;
import arc.graphics.*;

public class AStruct {
    public ObjectMap<String, AMethod> methods;
    public Color color;

    public AStruct(Color col) {
        this.color = col;
    }

    public void addMethod(String name, AMethod method) {
        methods.put(name, method);
    }

    public void removeMethod(String name) {
        methods.remove(name);
    }
}