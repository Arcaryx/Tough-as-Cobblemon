package com.arcaryx.toughascobblemon.event;

import com.arcaryx.toughascobblemon.ToughAsCobblemon;
import com.cobblemon.mod.common.api.types.ElementalTypes;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import toughasnails.api.item.TANItems;
import toughasnails.api.thirst.WaterType;
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
        if (ToughAsCobblemon.COMMON.pokemonGiveWater.get() && pokemon.isOwnedBy(player) &&
                (Objects.equals(pokemon.getPokemon().getPrimaryType(), ElementalTypes.INSTANCE.getWATER()) ||
                (!ToughAsCobblemon.COMMON.requirePrimaryType.get() &&
                Objects.equals(pokemon.getPokemon().getSecondaryType(), ElementalTypes.INSTANCE.getWATER())))) {
            ItemStack itemstack = player.getItemInHand(event.getHand());

            var level = pokemon.getPokemon().getLevel();
            WaterType water = null;

            if (level >= ToughAsCobblemon.COMMON.minLevelForPurified.get())
                water = WaterType.PURIFIED;
            else if (level >= ToughAsCobblemon.COMMON.minLevelForNormal.get())
                water = WaterType.NORMAL;
            else if (level >= ToughAsCobblemon.COMMON.minLevelForDirty.get())
                water = WaterType.DIRTY;

            if (ToughAsCobblemon.COMMON.fillBucket.get() && itemstack.is(Items.BUCKET) &&
                    level >= ToughAsCobblemon.COMMON.minLevelForBucket.get() ) {
                player.playSound(SoundEvents.BUCKET_FILL, 1.0F, 1.0F);
                var filledBucket = ItemUtils.createFilledResult(itemstack, player, Items.WATER_BUCKET.getDefaultInstance());
                player.setItemInHand(event.getHand(), filledBucket);
                player.awardStat(Stats.ITEM_USED.get(itemstack.getItem()));
                event.setCanceled(true);
                event.setCancellationResult(InteractionResultHolder.sidedSuccess(filledBucket, event.getSide().isClient()).getResult());
                return;
            }

            if (ToughAsCobblemon.COMMON.fillBottle.get() && water != null && itemstack.is(Items.GLASS_BOTTLE)) {
                player.playSound(SoundEvents.BOTTLE_FILL, 1.0F, 1.0F);
                ItemStack filledStack = switch (water) {
                    case PURIFIED -> new ItemStack(TANItems.PURIFIED_WATER_BOTTLE.get());
                    case DIRTY -> new ItemStack(TANItems.DIRTY_WATER_BOTTLE.get());
                    default -> PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.WATER);
                };
                fillItem(event, player, itemstack, filledStack);
                return;
            }

            if (ToughAsCobblemon.COMMON.fillCanteen.get() && water != null &&
                    (itemstack.getItem() instanceof EmptyCanteenItem || itemstack.getItem() instanceof FilledCanteenItem)) {
                player.playSound(SoundEvents.BOTTLE_FILL, 1.0F, 1.0F);
                ItemStack filledStack = switch (water) {
                    case PURIFIED -> new ItemStack(TANItems.PURIFIED_WATER_CANTEEN.get());
                    case DIRTY -> new ItemStack(TANItems.DIRTY_WATER_CANTEEN.get());
                    default -> PotionUtils.setPotion(new ItemStack(TANItems.WATER_CANTEEN.get()), Potions.WATER);
                };
                fillItem(event, player, itemstack, filledStack);
                return;
            }
        }
    }

    private static void fillItem(PlayerInteractEvent.EntityInteract event, Player player, ItemStack itemstack, ItemStack filledStack) {
        ItemStack replacementStack = ItemUtils.createFilledResult(itemstack, player, filledStack);
        if (itemstack != replacementStack) {
            player.setItemInHand(event.getHand(), replacementStack);
            if (replacementStack.isEmpty())
                ForgeEventFactory.onPlayerDestroyItem(player, itemstack, event.getHand());
        }
        player.awardStat(Stats.ITEM_USED.get(itemstack.getItem()));
        event.setCanceled(true);
        event.setCancellationResult(InteractionResultHolder.sidedSuccess(replacementStack, event.getSide().isClient()).getResult());
    }
}
