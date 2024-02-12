package hardbuckaroo.craftfactories.HotComposter;

import hardbuckaroo.craftfactories.CraftFactories;
import hardbuckaroo.craftfactories.ShowFactoryInfo;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Lightable;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.ArrayList;

public class HammerHotComposter implements Listener {
    private final CraftFactories plugin;
    public HammerHotComposter(CraftFactories plugin) {this.plugin = plugin;}

    @EventHandler
    public void register(PlayerInteractEvent event) {
        if(event.getHand() == EquipmentSlot.HAND && event.getPlayer().getInventory().getItemInMainHand().getType() == Material.WOODEN_HOE && event.getClickedBlock() != null) {
            Block block = event.getClickedBlock();
            FileConfiguration factoryData = plugin.getfactoryData();

            if(!factoryData.contains(plugin.getBlockKey(block)) && block.getType() == Material.BARREL) {
                ArrayList<String> blockArray = new ArrayList<>();
                blockArray.add(plugin.getBlockKey(block));
                //check for aspects
                if(block.getRelative(0,-1,0).getType() == Material.FURNACE) {
                    blockArray.add(plugin.getBlockKey(block.getRelative(0,-1,0)));
                } else return;
                if(block.getRelative(-1,0,0).getType() == Material.AIR && block.getRelative(1,0,0).getType() == Material.AIR && block.getRelative(-1,-1,0).getType() == Material.COMPOSTER && block.getRelative(1,-1,0).getType() == Material.COMPOSTER) {
                    blockArray.add(plugin.getBlockKey(block.getRelative(-1,0,0)));
                    blockArray.add(plugin.getBlockKey(block.getRelative(1,0,0)));
                    blockArray.add(plugin.getBlockKey(block.getRelative(-1,-1,0)));
                    blockArray.add(plugin.getBlockKey(block.getRelative(1,-1,0)));
                } else if(block.getRelative(0,0,-1).getType() == Material.AIR && block.getRelative(0,0,1).getType() == Material.AIR && block.getRelative(0,-1,-1).getType() == Material.COMPOSTER && block.getRelative(0,-1,1).getType() == Material.COMPOSTER) {
                    blockArray.add(plugin.getBlockKey(block.getRelative(0,0,-1)));
                    blockArray.add(plugin.getBlockKey(block.getRelative(0,0,1)));
                    blockArray.add(plugin.getBlockKey(block.getRelative(0,-1,-1)));
                    blockArray.add(plugin.getBlockKey(block.getRelative(0,-1,1)));
                } else return;

                factoryData.set(plugin.getBlockKey(block)+".Blocks",blockArray);
                factoryData.set(plugin.getBlockKey(block)+".Type","Hot Composter");
                factoryData.set(plugin.getBlockKey(block)+".Fuel",0);
                plugin.saveFactoryData();
                event.getPlayer().sendRawMessage("Hot Composter successfully created!");
            }
        }
    }
}
