package icu.x64.rares;

import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.CraftingScreenHandler;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.function.Predicate;

public class Rares implements ModInitializer {
    public static final String MOD_ID = "rares";
    private static final String[] COLORS = {
            "aqua", "black", "blue", "dark_aqua", "dark_blue",
            "dark_gray", "dark_green", "dark_purple", "dark_red",
            "gold", "gray", "green", "light_purple", "red",
            "yellow", "white"
    };
    private final PartyCrackerItem PARTY_CRACKER_ITEM = new PartyCrackerItem(false);
    private final PartyCrackerItem TEMPORARY_PARTY_CRACKER_ITEM = new PartyCrackerItem(true);
    private void registerPartyHat(String color, boolean temporary) {
        String prefix = temporary ? "temporary_" : "";
        Identifier id = new Identifier(MOD_ID, prefix + color + "_partyhat");
        Item item = new PartyHatItem(temporary);
        Registry.register(Registry.ITEM, id, item);
    }
    private Predicate<ItemStack> isTemporaryItem() {
        return stack -> {
            Identifier id = Registry.ITEM.getId(stack.getItem());
            return id != null && id.getNamespace().equals(MOD_ID) && id.getPath().startsWith("temporary_");
        };
    }
    private int clearTemporaryItems(CommandContext<ServerCommandSource> context) {
        for (ServerPlayerEntity player : context.getSource().getServer().getPlayerManager().getPlayerList()) {
            CraftingInventory dummyCraftingInventory = new CraftingInventory(new CraftingScreenHandler(0, player.getInventory()), 2, 2); // A 2x2 dummy crafting inventory
            player.getInventory().remove(isTemporaryItem(), Integer.MAX_VALUE, dummyCraftingInventory);
        }
        context.getSource().sendMessage(Text.literal("Removed temporary items!"));
        return 1;
    }
    @Override
    public void onInitialize() {
        for (String color : COLORS) {
            registerPartyHat(color, false); // Registering normal hats
            registerPartyHat(color, true);  // Registering temporary hats
        }

        Registry.register(Registry.ITEM, new Identifier("rares", "party_cracker"), PARTY_CRACKER_ITEM);
        Registry.register(Registry.ITEM, new Identifier("rares", "temporary_party_cracker"), TEMPORARY_PARTY_CRACKER_ITEM);

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(CommandManager.literal("cleartemporaryitems").requires(source -> source.hasPermissionLevel(2))
                    .executes(context -> clearTemporaryItems(context))
            );
        });
    }
}
