package hardbuckaroo.craftfactories;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

public class ProductionLoop {
    private final CraftFactories plugin;
    public ProductionLoop(CraftFactories plugin) {this.plugin = plugin;}
    
    public void loop(Inventory inventory,String blockKey) {
        FileConfiguration recipes = plugin.getRecipes();
        if(inventory.getLocation() != null) {
            FileConfiguration factoryData = plugin.getfactoryData();
            if(factoryData.contains(blockKey)) {
                String type = factoryData.getString(blockKey+".Type");
                int fuel = factoryData.getInt(blockKey+".Fuel");
                for(String recipe : recipes.getConfigurationSection(type+".Recipes").getKeys(false)) {
                    boolean create = true;
                    for (String component : recipes.getStringList(type + ".Recipes." + recipe + ".Input")) {
                        Material materialNeeded = Material.valueOf(component.split(",")[0]);
                        int materialCountNeeded = Integer.parseInt(component.split(",")[1]);
                        int materialCount = 0;
                        for (ItemStack stack : inventory.getContents()) {
                            if (stack != null) {
                                Material material = stack.getType();
                                int count = stack.getAmount();
                                if (material == materialNeeded) {
                                    materialCount += count;
                                }
                            }
                        }
                        if (materialCount < materialCountNeeded) {
                            create = false;
                            break;
                        }
                    }
                    ArrayList<ItemStack> conditionRemovals = new ArrayList<>();
                    for (String condition : recipes.getStringList(type + ".Recipes." + recipe + ".Conditions")) {
                        ConditionHandler ch = new ConditionHandler();
                        String conditionName = condition.split(",")[0];
                        int materialCountNeeded = Integer.parseInt(condition.split(",")[1]);
                        int materialCount = 0;
                        for (ItemStack stack : inventory.getContents()) {
                            if (stack != null) {
                                Material material = stack.getType();
                                int count = stack.getAmount();
                                if (ch.meetsCondition(conditionName,material)) {
                                    materialCount += count;
                                    conditionRemovals.add(stack);
                                }
                            }
                        }
                        if (materialCount < materialCountNeeded) {
                            create = false;
                            break;
                        }
                    }
                    if (create) {
                        int fuelNeeded = recipes.getInt(type + ".Recipes." + recipe + ".Fuel");
                        if (fuel < fuelNeeded) {
                            for (ItemStack stack : inventory.getContents()) {
                                if (stack != null) {
                                    Material material = stack.getType();
                                    int count = stack.getAmount();
                                    if (material == Material.COAL || material == Material.CHARCOAL) {
                                        while (fuel < fuelNeeded && count >= 1) {
                                            inventory.removeItem(new ItemStack(material, 1));
                                            count--;
                                            fuel += 8;
                                        }
                                    }
                                }
                            }
                        }
                        if (fuel >= fuelNeeded) {
                            for (String component : recipes.getStringList(type + ".Recipes." + recipe + ".Input")) {
                                Material materialNeeded = Material.valueOf(component.split(",")[0]);
                                int materialCountNeeded = Integer.parseInt(component.split(",")[1]);
                                inventory.removeItem(new ItemStack(materialNeeded, materialCountNeeded));
                            }
                            for (String component : recipes.getStringList(type + ".Recipes." + recipe + ".Conditions")) {
                                int materialCountNeeded = Integer.parseInt(component.split(",")[1]);
                                for(ItemStack conditionItem : conditionRemovals) {
                                    int count = conditionItem.getAmount();
                                    if(count < materialCountNeeded) {
                                        materialCountNeeded-=count;
                                        inventory.removeItem(conditionItem);
                                    } else {
                                        inventory.removeItem(new ItemStack(conditionItem.getType(),materialCountNeeded));
                                        break;
                                    }
                                }
                            }
                            fuel = fuel - fuelNeeded;
                            factoryData.set(blockKey + ".Fuel", fuel);
                            ApplyEffect applyEffect = new ApplyEffect(plugin);
                            applyEffect.applyEffect(blockKey, true);
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
                                    loop(inventory, blockKey);
                                } else {
                                    Block block = plugin.getBlockFromKey(blockKey);
                                    World world = block.getWorld();
                                    for(ItemStack failStack : fails.values()) {
                                        world.dropItemNaturally(block.getLocation(),failStack);
                                    }
                                }
                            }, 200L * fuelNeeded).getTaskId();
                            RecipeRun recipeRun = new RecipeRun(blockKey,type,id,200L * fuelNeeded,recipe);
                            RecipeRun.getInstance().runningFactories.add(recipeRun);
                            Bukkit.getScheduler().runTaskLater(plugin, () -> RecipeRun.getInstance().runningFactories.remove(recipeRun),200L * fuelNeeded);
                            plugin.saveFactoryData();
                            break;
                        }
                    }
                }
            }
        }
    }
}
