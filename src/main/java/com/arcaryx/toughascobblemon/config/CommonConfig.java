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
    public final ForgeConfigSpec.BooleanValue pokemonGiveWater;
    public final ForgeConfigSpec.BooleanValue requirePrimaryType;
    public final ForgeConfigSpec.BooleanValue fillBottle;
    public final ForgeConfigSpec.BooleanValue fillCanteen;
    public final ForgeConfigSpec.BooleanValue fillBucket;
    public final ForgeConfigSpec.IntValue minLevelForDirty;
    public final ForgeConfigSpec.IntValue minLevelForNormal;
    public final ForgeConfigSpec.IntValue minLevelForPurified;
    public final ForgeConfigSpec.IntValue minLevelForBucket;

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
        pokemonGiveWater = builder
                .comment("Should any Pokemon give the player water.")
                .define("pokemonGiveWater", true);
        requirePrimaryType = builder
                .comment("Should Pokemon need to have Water as their primary type to give water.")
                .define("requirePrimaryType", false);
        fillBottle = builder
                .comment("Can Pokemon fill bottles.")
                .define("fillBottle", true);
        fillCanteen = builder
                .comment("Can Pokemon fill canteens.")
                .define("fillCanteen", true);
        fillBucket = builder
                .comment("Can Pokemon fill buckets.")
                .define("fillBucket", true);
        builder.push("level");
        minLevelForDirty = builder
                .comment("Minimum level for dirty water.")
                .defineInRange("minLevelForDirty", 0, 0, 255);
        minLevelForNormal = builder
                .comment("Minimum level for normal water.")
                .defineInRange("minLevelForNormal", 25, 0, 255);
        minLevelForPurified = builder
                .comment("Minimum level for purified water.")
                .defineInRange("minLevelForPurified", 50, 0, 255);
        minLevelForBucket = builder
                .comment("Minimum level to fill a water bucket.")
                .defineInRange("minLevelForBucket", 75, 0, 255);
        builder.pop().pop();
    }
}
