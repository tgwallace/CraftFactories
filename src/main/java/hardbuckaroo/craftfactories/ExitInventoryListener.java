package hardbuckaroo.craftfactories;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ExitInventoryListener implements Listener {
    private final CraftFactories plugin;
    public ExitInventoryListener(CraftFactories plugin) {this.plugin = plugin;}

    @EventHandler
    public void exitInventoryListener(InventoryCloseEvent event) {
        Inventory inventory = event.getInventory();
        if(inventory.getLocation() != null) {
            Block block = inventory.getLocation().getBlock();
            String blockKey = plugin.getBlockKey(block);
            FileConfiguration factoryData = plugin.getfactoryData();
            if(factoryData.contains(blockKey) && RecipeRun.getInstance().getRunFromBlockKey(blockKey) == null) {
                ProductionLoop loop = new ProductionLoop(plugin);
                loop.loop(inventory,blockKey);
            }
        }
    }
}
