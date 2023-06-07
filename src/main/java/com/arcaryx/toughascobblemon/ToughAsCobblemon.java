package com.arcaryx.toughascobblemon;

import com.arcaryx.toughascobblemon.temperature.modifier.PokemonTemperatureModifier;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import toughasnails.api.temperature.TemperatureHelper;

@Mod(ToughAsCobblemon.MOD_ID)
public class ToughAsCobblemon {
    public static final String MOD_ID = "toughascobblemon";
    public static final Logger LOGGER = LogManager.getLogger();
    public static ToughAsCobblemon INSTANCE;

    public ToughAsCobblemon() {
        INSTANCE = this;

        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        TemperatureHelper.registerPlayerTemperatureModifier(new PokemonTemperatureModifier());
    }


}