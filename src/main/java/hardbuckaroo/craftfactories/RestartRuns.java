package hardbuckaroo.craftfactories;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import java.util.HashMap;

public class RestartRuns {
    private final CraftFactories plugin;
    public RestartRuns(CraftFactories plugin) {this.plugin = plugin;}
    
    public void restart() {
        FileConfiguration recipes = plugin.getRecipes();
        FileConfiguration factoryData = plugin.getfactoryData();

        for(String blockKey : factoryData.getKeys(false)) {
            if (factoryData.contains(blockKey + ".Recipe") && factoryData.contains(blockKey + ".Duration") && RecipeRun.getInstance().getRunFromBlockKey(blockKey) == null) {
                String type = factoryData.getString(blockKey+".Type");
                String recipe = factoryData.getString(blockKey+".Recipe");
                long duration = factoryData.getLong(blockKey+".Duration");
                Inventory inventory = ((Container) plugin.getBlockFromKey(blockKey).getState()).getInventory();
                ApplyEffect applyEffect = new ApplyEffect(plugin);
                ProductionLoop loop = new ProductionLoop(plugin);
                int id = Bukkit.getScheduler().runTaskLater(plugin, () -> {
                    HashMap<Integer, ItemStack> fails = new HashMap<>();
                    for (String output : recipes.getStringList(type + ".Recipes." + recipe + ".Output")) {
                        Material material = Material.valueOf(output.split(",")[0]);
                        int materialCount = Integer.parseInt(output.split(",")[1]);
                        ItemStack newItem = new ItemStack(material, materialCount);
                        if (newItem.getMaxStackSize() < 64 && plugin.getConfig().getBoolean("AllowIllegalStacks")) {
                            for (ItemStack stack : inventory.getContents()) {
                                if (stack != null && stack.getType() == material) {
                                    int amount = stack.getAmount();
                                    if (amount + materialCount > 64) {
                                        stack.setAmount(64);
                                        materialCount = (amount + materialCount) - 64;
                                    } else {
                                        stack.setAmount(amount + materialCount);
                                        materialCount = 0;
                                    }
                                }
                            }
                            if (materialCount > 0) {
                                fails = inventory.addItem(new ItemStack(material, materialCount));
                            }
                        } else {
                            fails = inventory.addItem(newItem);
                        }
                    }
                    applyEffect.applyEffect(blockKey, false);
                    if (fails.isEmpty()) {
                        loop.loop(inventory, blockKey);
                    } else {
                        Block block = plugin.getBlockFromKey(blockKey);
                        World world = block.getWorld();
                        for (ItemStack failStack : fails.values()) {
                            world.dropItemNaturally(block.getLocation(), failStack);
                        }
                    }
                }, duration).getTaskId();
                RecipeRun recipeRun = new RecipeRun(blockKey, type, id, duration, recipe);
                RecipeRun.getInstance().runningFactories.add(recipeRun);
                Bukkit.getScheduler().runTaskLater(plugin, () -> RecipeRun.getInstance().runningFactories.remove(recipeRun), duration);
                factoryData.set(blockKey+".Recipe",null);
                factoryData.set(blockKey+".Duration",null);
                plugin.saveFactoryData();
            }
        }
    }
}
