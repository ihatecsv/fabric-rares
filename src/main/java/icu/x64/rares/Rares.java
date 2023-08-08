package icu.x64.rares;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Rares implements ModInitializer {
    public static final String MOD_ID = "rares";

    private static final String[] COLORS = {
            "aqua", "black", "blue", "dark_aqua", "dark_blue",
            "dark_gray", "dark_green", "dark_purple", "dark_red",
            "gold", "gray", "green", "light_purple", "red",
            "yellow", "white"
    };

    private final PartyCrackerItem PARTY_CRACKER_ITEM = new PartyCrackerItem();

    private void registerPartyHat(String color) {
        Identifier id = new Identifier(MOD_ID, color + "_partyhat");
        Item item = new PartyHatItem();
        Registry.register(Registry.ITEM, id, item);
    }

    @Override
    public void onInitialize() {
        for (String color : COLORS) {
            registerPartyHat(color);
        }

        Registry.register(Registry.ITEM, new Identifier("rares", "party_cracker"), PARTY_CRACKER_ITEM);
    }
}
