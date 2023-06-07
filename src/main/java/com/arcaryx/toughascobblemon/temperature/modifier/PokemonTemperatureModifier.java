package com.arcaryx.toughascobblemon.temperature.modifier;

import com.arcaryx.toughascobblemon.ToughAsCobblemon;
import com.cobblemon.mod.common.api.pokemon.PokemonSpecies;
import com.cobblemon.mod.common.api.pokemon.effect.ShoulderEffect;
import com.cobblemon.mod.common.api.types.ElementalTypes;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.FormData;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Parrot;
import net.minecraft.world.entity.player.Player;
import toughasnails.api.temperature.IPlayerTemperatureModifier;
import toughasnails.api.temperature.TemperatureLevel;

import java.util.*;
import java.util.stream.Collectors;

public class PokemonTemperatureModifier implements IPlayerTemperatureModifier {

    @Override
    public TemperatureLevel modify(Player player, TemperatureLevel temperatureLevel) {
        var nearbyPokemon = player.getLevel().getEntitiesOfClass(PokemonEntity.class, player.getBoundingBox().inflate(5.0));
        nearbyPokemon.sort((Comparator.comparingDouble((PokemonEntity pokemon) -> pokemon.isOwnedBy(player) ? Double.MAX_VALUE : player.distanceToSqr(pokemon))));
        var nearbyForms = new ArrayList<>(nearbyPokemon.stream().map(x -> new Tuple<>(x.isOwnedBy(player), x.getForm())).toList());

        if (!player.getShoulderEntityLeft().isEmpty() && player.getShoulderEntityLeft().contains("Pokemon")) {
            var pokemonTag = player.getShoulderEntityLeft().getCompound("Pokemon");
            GetShoulderEntityForm(nearbyForms, pokemonTag);
        }

        if (!player.getShoulderEntityRight().isEmpty() && player.getShoulderEntityRight().contains("Pokemon")) {
            var pokemonTag = player.getShoulderEntityRight().getCompound("Pokemon");
            GetShoulderEntityForm(nearbyForms, pokemonTag);
        }

        var newTemp = temperatureLevel.ordinal() - 2;
        for (var pokemon : nearbyForms) {

            var temp = 0;
            if (Objects.equals(pokemon.getB().getPrimaryType(), ElementalTypes.INSTANCE.getFIRE()))
                temp += 2;
            else if (Objects.equals(pokemon.getB().getSecondaryType(), ElementalTypes.INSTANCE.getFIRE()))
                temp += 1;
            if (Objects.equals(pokemon.getB().getPrimaryType(), ElementalTypes.INSTANCE.getICE()))
                temp -= 2;
            else if (Objects.equals(pokemon.getB().getSecondaryType(), ElementalTypes.INSTANCE.getICE()))
                temp -= 1;

            if (pokemon.getA()) {
                if ((newTemp == 2 && temp > 0) || (newTemp == -2 && temp < 0))
                    temp = 0;
                else
                    temp = Mth.clamp(temp, Math.max(-1 - newTemp, -2), Math.min(1 - newTemp, 2));

                newTemp = newTemp + temp;
            }
            else
                newTemp = Mth.clamp(newTemp + temp, -2, 2);
        }

        return TemperatureLevel.values()[newTemp + 2];
    }

    private void GetShoulderEntityForm(ArrayList<Tuple<Boolean, FormData>> nearbyForms, CompoundTag pokemonTag) {
        var species = new ResourceLocation(pokemonTag.getString("Species"));
        var formTag = pokemonTag.getString("FormId");
        var formData = Objects.requireNonNull(PokemonSpecies.INSTANCE.getByIdentifier(species)).getForm(Collections.singleton(formTag));
        nearbyForms.add(new Tuple<>(Boolean.TRUE, formData));
    }
}
