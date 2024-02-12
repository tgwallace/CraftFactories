package hardbuckaroo.craftfactories.Tannery;

import hardbuckaroo.craftfactories.CraftFactories;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Lightable;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.ArrayList;

public class HammerTannery implements Listener {
    private final CraftFactories plugin;
    public HammerTannery(CraftFactories plugin) {this.plugin = plugin;}

    @EventHandler
    public void register(PlayerInteractEvent event) {
        if(event.getHand() == EquipmentSlot.HAND && event.getPlayer().getInventory().getItemInMainHand().getType() == Material.WOODEN_HOE && event.getClickedBlock() != null) {
            Block block = event.getClickedBlock();
            FileConfiguration factoryData = plugin.getfactoryData();

            if(!factoryData.contains(plugin.getBlockKey(block)) && block.getType() == Material.BARREL) {
                ArrayList<String> blockArray = new ArrayList<>();
                blockArray.add(plugin.getBlockKey(block));
                //check for aspects
                if(block.getRelative(BlockFace.EAST).getType() == Material.BLAST_FURNACE && block.getRelative(BlockFace.EAST,2).getType() == Material.WATER_CAULDRON) {
                    blockArray.add(plugin.getBlockKey(block.getRelative(BlockFace.EAST)));
                    blockArray.add(plugin.getBlockKey(block.getRelative(BlockFace.EAST,2)));
                } else if(block.getRelative(BlockFace.WEST).getType() == Material.BLAST_FURNACE && block.getRelative(BlockFace.WEST,2).getType() == Material.WATER_CAULDRON) {
                    blockArray.add(plugin.getBlockKey(block.getRelative(BlockFace.WEST)));
                    blockArray.add(plugin.getBlockKey(block.getRelative(BlockFace.WEST,2)));
                } else if(block.getRelative(BlockFace.NORTH).getType() == Material.BLAST_FURNACE && block.getRelative(BlockFace.NORTH,2).getType() == Material.WATER_CAULDRON) {
                    blockArray.add(plugin.getBlockKey(block.getRelative(BlockFace.NORTH)));
                    blockArray.add(plugin.getBlockKey(block.getRelative(BlockFace.NORTH,2)));
                } else if(block.getRelative(BlockFace.SOUTH).getType() == Material.BLAST_FURNACE && block.getRelative(BlockFace.SOUTH,2).getType() == Material.WATER_CAULDRON) {
                    blockArray.add(plugin.getBlockKey(block.getRelative(BlockFace.SOUTH)));
                    blockArray.add(plugin.getBlockKey(block.getRelative(BlockFace.SOUTH,2)));
                } else return;

                factoryData.set(plugin.getBlockKey(block)+".Blocks",blockArray);
                factoryData.set(plugin.getBlockKey(block)+".Type","Tannery");
                factoryData.set(plugin.getBlockKey(block)+".Fuel",0);
                plugin.saveFactoryData();
                event.getPlayer().sendRawMessage("Tannery successfully created!");
            }
        }
    }
}
