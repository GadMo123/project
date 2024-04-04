package Utils;

import com.google.inject.Provider;

public class EnvVarProvider implements Provider<String> {
    private final String varName;

    public EnvVarProvider(String varName) {
        this.varName = varName;
    }

    @Override
    public String get() {
        return System.getProperty("env." + varName);
    }
}