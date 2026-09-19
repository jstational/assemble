package ambl.logic.cer;

import arc.struct.*;
import arc.graphics.*;

public class AStruct {
    public ObjectMap<String, AMethod> methods;

    public AStruct() {
        this.methods = new ObjectMap<>();
    }

    public void addMethod(AMethod method) {
        methods.put(method.name, method);
    }

    public void removeMethod(String name) {
        methods.remove(name);
    }
}