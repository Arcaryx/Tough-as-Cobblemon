package com.arcaryx.toughascobblemon.event;

import com.arcaryx.toughascobblemon.ToughAsCobblemon;
import com.cobblemon.mod.common.api.types.ElementalTypes;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.*;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import toughasnails.api.item.TANItems;
import toughasnails.item.EmptyCanteenItem;
import toughasnails.item.FilledCanteenItem;

import java.util.Objects;

@Mod.EventBusSubscriber(modid = ToughAsCobblemon.MOD_ID)
public class PokemonRightClickEvent {
    @SubscribeEvent
    public static void onRightClickPokemon(PlayerInteractEvent.EntityInteract event) {
        if (!(event.getTarget() instanceof PokemonEntity pokemon))
            return;
        var player = event.getEntity();
        // Right click water type for water
        if (pokemon.isOwnedBy(player) &&
                (Objects.equals(pokemon.getPokemon().getPrimaryType(), ElementalTypes.INSTANCE.getWATER()) ||
                Objects.equals(pokemon.getPokemon().getSecondaryType(), ElementalTypes.INSTANCE.getWATER()))) {
            ItemStack itemstack = player.getItemInHand(event.getHand());
            /*if (itemstack.is(Items.BUCKET)) {
                player.playSound(SoundEvents.BUCKET_FILL, 1.0F, 1.0F);
                var filledBucket = ItemUtils.createFilledResult(itemstack, player, Items.WATER_BUCKET.getDefaultInstance());
                player.setItemInHand(event.getHand(), filledBucket);
            }*/
            if (itemstack.is(Items.GLASS_BOTTLE)) {
                player.playSound(SoundEvents.BOTTLE_FILL, 1.0F, 1.0F);
                var filledBottle = ItemUtils.createFilledResult(itemstack, player, TANItems.PURIFIED_WATER_BOTTLE.get().getDefaultInstance());
                player.setItemInHand(event.getHand(), filledBottle);
            }
            else if (itemstack.getItem() instanceof EmptyCanteenItem || itemstack.getItem() instanceof FilledCanteenItem) {
                player.playSound(SoundEvents.BOTTLE_FILL, 1.0F, 1.0F);
                var filledCanteen = ItemUtils.createFilledResult(itemstack, player, TANItems.PURIFIED_WATER_CANTEEN.get().getDefaultInstance());
                player.setItemInHand(event.getHand(), filledCanteen);
            }
            player.awardStat(Stats.ITEM_USED.get(itemstack.getItem()));
            event.setCancellationResult(InteractionResult.sidedSuccess(event.getSide().isClient()));
            return;
        }
    }
}
