package com.pibbium.alwaysdurable;

import com.pibbium.alwaysdurable.config.AlwaysDurableConfig;
import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.api.ClientModInitializer;

public class AlwaysDurable implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        MidnightConfig.init("alwaysdurable", AlwaysDurableConfig.class);
    }
}