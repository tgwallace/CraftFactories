package hardbuckaroo.craftfactories;

import org.bukkit.Material;

public class ConditionHandler {
    public boolean meetsCondition(String condition, Material material) {
        if (condition.equalsIgnoreCase("compostable")) {
            switch (material) {
                case DRIED_KELP:
                case GLOW_BERRIES:
                case SHORT_GRASS:
                case GRASS_BLOCK:
                case HANGING_ROOTS:
                case KELP:
                case MELON_SEEDS:
                case MOSS_CARPET:
                case PUMPKIN_SEEDS:
                case SEAGRASS:
                case SMALL_DRIPLEAF:
                case SWEET_BERRIES:
                case WHEAT_SEEDS:
                case CACTUS:
                case DRIED_KELP_BLOCK:
                case FLOWERING_AZALEA_LEAVES:
                case GLOW_LICHEN:
                case MELON_SLICE:
                case NETHER_SPROUTS:
                case SUGAR_CANE:
                case TALL_GRASS:
                case WEEPING_VINES:
                case TWISTING_VINES:
                case APPLE:
                case AZALEA:
                case BEETROOT:
                case BEETROOT_SEEDS:
                case BIG_DRIPLEAF:
                case CARROT:
                case COCOA_BEANS:
                case LILY_PAD:
                case MELON:
                case MOSS_BLOCK:
                case BROWN_MUSHROOM:
                case RED_MUSHROOM:
                case MUSHROOM_STEM:
                case NETHER_WART:
                case POTATO:
                case SEA_PICKLE:
                case SHROOMLIGHT:
                case SPORE_BLOSSOM:
                case WHEAT:
                case CRIMSON_FUNGUS:
                case WARPED_FUNGUS:
                case CRIMSON_ROOTS:
                case WARPED_ROOTS:
                case BAKED_POTATO:
                case BREAD:
                case COOKIE:
                case FLOWERING_AZALEA:
                case NETHER_WART_BLOCK:
                case WARPED_WART_BLOCK:
                case CAKE:
                case PUMPKIN_PIE:
                case OAK_LEAVES:
                case SPRUCE_LEAVES:
                case BIRCH_LEAVES:
                case JUNGLE_LEAVES:
                case ACACIA_LEAVES:
                case DARK_OAK_LEAVES:
                case AZALEA_LEAVES:
                case OAK_SAPLING:
                case SPRUCE_SAPLING:
                case BIRCH_SAPLING:
                case JUNGLE_SAPLING:
                case ACACIA_SAPLING:
                case DARK_OAK_SAPLING:
                case VINE:
                case FERN:
                case LARGE_FERN:
                case DANDELION:
                case POPPY:
                case BLUE_ORCHID:
                case ALLIUM:
                case AZURE_BLUET:
                case RED_TULIP:
                case ORANGE_TULIP:
                case WHITE_TULIP:
                case PINK_TULIP:
                case OXEYE_DAISY:
                case CORNFLOWER:
                case LILY_OF_THE_VALLEY:
                case WITHER_ROSE:
                case SUNFLOWER:
                case LILAC:
                case ROSE_BUSH:
                case PEONY:
                case PUMPKIN:
                case CARVED_PUMPKIN:
                case HAY_BLOCK:
                case BROWN_MUSHROOM_BLOCK:
                case RED_MUSHROOM_BLOCK:
                    return true;

                default:
                    return false;
            }
        } else if(condition.equalsIgnoreCase("mushroom")) {
            return material == Material.RED_MUSHROOM || material == Material.BROWN_MUSHROOM;
        } else if(condition.equalsIgnoreCase("wool")) {
            return material.toString().contains("WOOL");
        } else if(condition.equalsIgnoreCase("planks")) {
            return material.toString().contains("PLANKS");
        }
        return false;
    }
}
