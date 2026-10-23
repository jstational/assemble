package ambl;

import arc.*;
import arc.files.*;
import ambl.logic.*;

public class AssembleTest {
    public static void main() {
        Fi src = Core.files.internal("src");
        String srcstr = src.readString();
        ACompiler compiler = new ACompiler(srcstr);
        System.out.println(compiler.compile());
    }
}