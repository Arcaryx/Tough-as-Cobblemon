package com.arcaryx.toughascobblemon.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class CommonConfig {

    // Temperature
    public final ForgeConfigSpec.BooleanValue pokemonAffectsTemperature;
    public final ForgeConfigSpec.BooleanValue wildAffectsTemperature;
    public final ForgeConfigSpec.BooleanValue capturedAffectsTemperature;
    public final ForgeConfigSpec.BooleanValue wildCausesHarm;
    public final ForgeConfigSpec.BooleanValue capturedCausesHarm;
    public final ForgeConfigSpec.BooleanValue capturedCausesHarmOther;
    public final ForgeConfigSpec.DoubleValue temperatureRange;
    public final ForgeConfigSpec.DoubleValue temperatureRangePerLevel;
    public final ForgeConfigSpec.BooleanValue secondaryTypeHalfStrength;
    public final ForgeConfigSpec.DoubleValue temperatureStrength;
    public final ForgeConfigSpec.DoubleValue temperatureStrengthPerLevel;

    // Hydration


    public CommonConfig(ForgeConfigSpec.Builder builder){
        builder.push("temperature");
        pokemonAffectsTemperature = builder
                .comment("Should any Pokemon affect the player's temperature.")
                .define("pokemonAffectsTemperature", true);
        wildAffectsTemperature = builder
                .comment("Should wild Pokemon affect the player's temperature.")
                .define("wildAffectsTemperature", true);
        wildCausesHarm = builder
                .comment("Should wild Pokemon be able to put players into temperature extremes.")
                .define("wildCausesHarm", true);
        capturedAffectsTemperature = builder
                .comment("Should captured Pokemon affect the player's temperature.")
                .define("capturedAffectsTemperature", true);
        capturedCausesHarm = builder
                .comment("Should captured Pokemon be able to put their owner into temperature extremes.")
                .define("capturedCausesHarm", false);
        capturedCausesHarmOther = builder
                .comment("Should captured Pokemon be able to put other players into temperature extremes.")
                .define("capturedCausesHarmOther", false);
        builder.push("range");
        temperatureRange = builder
                .comment("Base range for Pokemon to affect the player's temperature.")
                .defineInRange("temperatureRange", 5.0, 0.0, 255.0);
        temperatureRangePerLevel = builder
                .comment("Range increase per level for Pokemon to affect the player's temperature.")
                .defineInRange("temperatureRangePerLevel", 0.0, 0.0, 255.0);
        builder.pop().push("strength");
        secondaryTypeHalfStrength = builder
                .comment("Should the Pokemon's secondary type have half-effectiveness.")
                .define("secondaryTypeHalfStrength", true);
        temperatureStrength = builder
                .comment("Base strength for Pokemon to affect the player's temperature.")
                .defineInRange("temperatureStrength", 2.0, 0.0, 4.0);
        temperatureStrengthPerLevel = builder
                .comment("Strength increase per level for Pokemon to affect the player's temperature.")
                .defineInRange("temperatureStrengthPerLevel", 0.0, 0.0, 1.0);
        builder.pop().pop().push("hydration");





    }
}
