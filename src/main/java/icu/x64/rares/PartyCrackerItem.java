package icu.x64.rares;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class PartyCrackerItem extends Item {
    private final Map<String, Integer> hatWeights = new HashMap<>();
    private boolean temporary;
    public PartyCrackerItem(boolean temporary) {
        super(new FabricItemSettings().rarity(Rarity.EPIC));
        this.temporary = temporary;
        hatWeights.put("gold", 1);
        hatWeights.put("black", 2);
        hatWeights.put("dark_gray", 2);
        hatWeights.put("gray", 2);
        hatWeights.put("blue", 10);
        hatWeights.put("dark_blue", 10);
        hatWeights.put("white", 20);
        hatWeights.put("red", 33);
        hatWeights.put("dark_red", 33);
        hatWeights.put("green", 50);
        hatWeights.put("dark_green", 50);
        hatWeights.put("yellow", 100);
        hatWeights.put("light_purple", 200);
        hatWeights.put("dark_purple", 200);
        hatWeights.put("aqua", 200);
        hatWeights.put("dark_aqua", 200);
    }

    private String getRandomHat() {
        int totalWeight = hatWeights.values().stream().mapToInt(Integer::intValue).sum();
        int randomValue = new Random().nextInt(totalWeight);
        int accumulatedWeight = 0;
        for (Map.Entry<String, Integer> entry : hatWeights.entrySet()) {
            accumulatedWeight += entry.getValue();
            if (randomValue < accumulatedWeight) {
                return entry.getKey();
            }
        }
        return "dark_aqua";
    }
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient()) {
            world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.BLOCK_DEEPSLATE_BREAK, user.getSoundCategory(), 1.0F, 2.0F);

            user.getStackInHand(hand).decrement(1);

            String selectedHatColor = getRandomHat();
            String identifierPrefix = this.temporary ? "temporary_" : "";
            Identifier id = new Identifier(Rares.MOD_ID, identifierPrefix + selectedHatColor + "_partyhat");
            Item partyHat = Registries.ITEM.get(id);

            ItemStack hatStack = new ItemStack(partyHat);

            double offsetX = -Math.sin(Math.toRadians(user.getYaw()));
            double offsetZ = Math.cos(Math.toRadians(user.getYaw()));
            ItemEntity hatEntity = new ItemEntity(world, user.getX() + offsetX, user.getY() + user.getEyeHeight(EntityPose.STANDING) - 0.5, user.getZ() + offsetZ, hatStack);
            hatEntity.setPickupDelay(10);
            world.spawnEntity(hatEntity);
        }

        return TypedActionResult.success(user.getStackInHand(hand));
    }
    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        if (temporary) {
            tooltip.add(Text.translatable("desc.rares.temporary").formatted(Formatting.GRAY));
        }
    }
}
