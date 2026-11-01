package ambl.data;

import arc.files.Fi;
import arc.util.serialization.*;
import ambl.AVars;

public class AConfig {
    private Fi config;

    public AConfig() {}

    public void init() {
        config = ADataDirs.config;
    }

    public Fi getConfigDir() {
        return config;
    }
}
