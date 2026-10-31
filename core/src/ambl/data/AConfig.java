package ambl.data;

public final class AConfig {
    public static Fi configDir;

    private AConfig() {}

    public void init() {
        configDir = ADataDirs.config;
    }
}
