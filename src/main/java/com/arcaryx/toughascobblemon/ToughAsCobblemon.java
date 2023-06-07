package com.arcaryx.toughascobblemon;

import com.arcaryx.toughascobblemon.config.CommonConfig;
import com.arcaryx.toughascobblemon.temperature.modifier.PokemonTemperatureModifier;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import toughasnails.api.temperature.TemperatureHelper;

@Mod(ToughAsCobblemon.MOD_ID)
public class ToughAsCobblemon {
    public static final String MOD_ID = "toughascobblemon";
    public static final Logger LOGGER = LogManager.getLogger();
    public static final CommonConfig COMMON;
    public static final ForgeConfigSpec commonSpec;
    public static ToughAsCobblemon INSTANCE;

    static {
        final Pair<CommonConfig, ForgeConfigSpec> common = new ForgeConfigSpec.Builder().configure(CommonConfig::new);
        COMMON = common.getLeft();
        commonSpec = common.getRight();
    }

    public ToughAsCobblemon() {
        INSTANCE = this;

        MinecraftForge.EVENT_BUS.register(this);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, commonSpec);
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        TemperatureHelper.registerPlayerTemperatureModifier(new PokemonTemperatureModifier());
    }


}