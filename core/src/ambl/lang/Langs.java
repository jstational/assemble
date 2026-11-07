package ambl.lang;

public final class Langs {
    public static boolean isLogicSugar(String src) {
        return false;
    }

    public static boolean isMlog(String src) {
        return false;
    }

    public static boolean isAssertions(String src) {
        return false;
    }

    public static boolean isAssemble(String src) {
        return src.startsWith("#amblv");
    }

    public static boolean isUnknown(String src) {
        return false;
    }
}