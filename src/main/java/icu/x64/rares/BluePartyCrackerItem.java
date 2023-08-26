package icu.x64.rares;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.*;
import net.minecraft.world.World;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class BluePartyCrackerItem extends Item {
    public static final String ITEM_ID = "blue_party_cracker";
    private static BluePartyCrackerItem itemInstance;
    private final Map<Item, AbstractMap.SimpleEntry<Integer, Integer>> itemWeights = new HashMap<>();

    public BluePartyCrackerItem(Settings settings) {
        super(settings);
        itemWeights.put(DiskOfReturningItem.getItemInstance(), new AbstractMap.SimpleEntry<>(2, 1));
        itemWeights.put(Items.NETHERITE_SCRAP, new AbstractMap.SimpleEntry<>(5, 1));
        itemWeights.put(Items.HEART_OF_THE_SEA, new AbstractMap.SimpleEntry<>(5, 1));
        itemWeights.put(Items.ENCHANTED_GOLDEN_APPLE, new AbstractMap.SimpleEntry<>(10, 1));
        itemWeights.put(Items.TOTEM_OF_UNDYING, new AbstractMap.SimpleEntry<>(15, 1));
        itemWeights.put(RubyItem.getItemInstance(), new AbstractMap.SimpleEntry<>(50, 3));
        itemWeights.put(Items.DIAMOND, new AbstractMap.SimpleEntry<>(100, 3));
        itemWeights.put(Items.GOLD_INGOT, new AbstractMap.SimpleEntry<>(150, 5));
    }

    public static void initialize() {
        Registry.register(Registries.ITEM, new Identifier(Rares.MOD_ID, ITEM_ID), getItemInstance());
    }

    public static BluePartyCrackerItem getItemInstance() {
        if (itemInstance == null) {
            itemInstance = new BluePartyCrackerItem(new FabricItemSettings().rarity(Rarity.RARE));
        }
        return itemInstance;
    }

    private AbstractMap.SimpleEntry<Item, Integer> getRandomItem() {
        int totalWeight = itemWeights.values().stream().mapToInt(AbstractMap.SimpleEntry::getKey).sum();
        int randomValue = new Random().nextInt(totalWeight);
        int accumulatedWeight = 0;
        for (Map.Entry<Item, AbstractMap.SimpleEntry<Integer, Integer>> entry : itemWeights.entrySet()) {
            accumulatedWeight += entry.getValue().getKey();
            if (randomValue < accumulatedWeight) {
                return new AbstractMap.SimpleEntry<>(entry.getKey(), entry.getValue().getValue());
            }
        }
        return new AbstractMap.SimpleEntry<>(Items.STONE, 1); // default item if no item is selected
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient()) {
            world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.BLOCK_DEEPSLATE_BREAK, user.getSoundCategory(), 1.0F, 2.0F);

            user.getStackInHand(hand).decrement(1);

            AbstractMap.SimpleEntry<Item, Integer> selectedItemPair = getRandomItem();
            Item selectedItem = selectedItemPair.getKey();
            int quantity = selectedItemPair.getValue();
            ItemStack itemStack = new ItemStack(selectedItem, quantity);

            double offsetX = -Math.sin(Math.toRadians(user.getYaw()));
            double offsetZ = Math.cos(Math.toRadians(user.getYaw()));
            ItemEntity itemEntity = new ItemEntity(world, user.getX() + offsetX, user.getY() + user.getEyeHeight(EntityPose.STANDING) - 0.5, user.getZ() + offsetZ, itemStack);
            itemEntity.setPickupDelay(10);
            world.spawnEntity(itemEntity);
        }

        return TypedActionResult.success(user.getStackInHand(hand));
    }
}