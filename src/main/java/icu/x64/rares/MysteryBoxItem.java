package icu.x64.rares;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.*;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MysteryBoxItem extends Item {
    public static final String ITEM_ID = "mystery_box";
    private static DiskOfReturningItem itemInstance;
    private final Map<Item, Integer> itemWeights = new HashMap<>();
    public MysteryBoxItem(Settings settings) {
        super(settings);
        itemWeights.put(RedPartyCrackerItem.getItemInstance(), 1);
        itemWeights.put(Items.DIAMOND, 3);
    }
    public static void initialize() {
        Registry.register(Registry.ITEM, new Identifier(Rares.MOD_ID, ITEM_ID), getItemInstance());
    }

    public static DiskOfReturningItem getItemInstance() {
        if (itemInstance == null) {
            itemInstance = new DiskOfReturningItem(new FabricItemSettings().rarity(Rarity.EPIC));
        }
        return itemInstance;
    }

    private Item getRandomItem() {
        int totalWeight = itemWeights.values().stream().mapToInt(Integer::intValue).sum();
        int randomValue = new Random().nextInt(totalWeight);
        int accumulatedWeight = 0;
        for (Map.Entry<Item, Integer> entry : itemWeights.entrySet()) {
            accumulatedWeight += entry.getValue();
            if (randomValue < accumulatedWeight) {
                return entry.getKey();
            }
        }
        return Items.STONE; // default item if no item is selected
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient()) {
            world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.BLOCK_DEEPSLATE_BREAK, user.getSoundCategory(), 1.0F, 2.0F);

            user.getStackInHand(hand).decrement(1);

            Item selectedItem = getRandomItem();
            ItemStack itemStack = new ItemStack(selectedItem);

            double offsetX = -Math.sin(Math.toRadians(user.getYaw()));
            double offsetZ = Math.cos(Math.toRadians(user.getYaw()));
            ItemEntity itemEntity = new ItemEntity(world, user.getX() + offsetX, user.getY() + user.getEyeHeight(EntityPose.STANDING) - 0.5, user.getZ() + offsetZ, itemStack);
            itemEntity.setPickupDelay(10);
            world.spawnEntity(itemEntity);
        }

        return TypedActionResult.success(user.getStackInHand(hand));
    }
}
