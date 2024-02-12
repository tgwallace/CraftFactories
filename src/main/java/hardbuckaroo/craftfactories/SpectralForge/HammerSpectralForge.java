package hardbuckaroo.craftfactories.SpectralForge;

import hardbuckaroo.craftfactories.ApplyEffect;
import hardbuckaroo.craftfactories.CraftFactories;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.ArrayList;

public class HammerSpectralForge implements Listener {
    private final CraftFactories plugin;
    public HammerSpectralForge(CraftFactories plugin) {this.plugin = plugin;}

    @EventHandler
    public void register(PlayerInteractEvent event) {
        if(event.getHand() == EquipmentSlot.HAND && event.getPlayer().getInventory().getItemInMainHand().getType() == Material.WOODEN_HOE && event.getClickedBlock() != null) {
            Block block = event.getClickedBlock();
            FileConfiguration factoryData = plugin.getfactoryData();

            if(!factoryData.contains(plugin.getBlockKey(block)) && block.getType() == Material.CHEST) {
                ArrayList<String> blockArray = new ArrayList<>();
                blockArray.add(plugin.getBlockKey(block));
                //check for aspects
                if(block.getRelative(BlockFace.EAST).getType() == Material.SOUL_CAMPFIRE && block.getRelative(BlockFace.EAST,2).getType() == Material.BLAST_FURNACE
                    && block.getRelative(BlockFace.EAST).getRelative(BlockFace.UP).getType() == Material.NETHERITE_BLOCK && block.getRelative(BlockFace.EAST,2).getRelative(BlockFace.UP).getType() == Material.IRON_BARS
                        && block.getRelative(BlockFace.NORTH_EAST).getType() == Material.BLAST_FURNACE && block.getRelative(BlockFace.NORTH_EAST).getRelative(BlockFace.UP).getType() == Material.IRON_BARS
                        && block.getRelative(BlockFace.SOUTH_EAST).getType() == Material.BLAST_FURNACE && block.getRelative(BlockFace.SOUTH_EAST).getRelative(BlockFace.UP).getType() == Material.IRON_BARS
                ) {
                    blockArray.add(plugin.getBlockKey(block.getRelative(BlockFace.EAST)));
                    blockArray.add(plugin.getBlockKey(block.getRelative(BlockFace.EAST,2)));
                    blockArray.add(plugin.getBlockKey(block.getRelative(BlockFace.EAST).getRelative(BlockFace.UP)));
                    blockArray.add(plugin.getBlockKey(block.getRelative(BlockFace.EAST,2).getRelative(BlockFace.UP)));
                    blockArray.add(plugin.getBlockKey(block.getRelative(BlockFace.NORTH_EAST)));
                    blockArray.add(plugin.getBlockKey(block.getRelative(BlockFace.NORTH_EAST).getRelative(BlockFace.UP)));
                    blockArray.add(plugin.getBlockKey(block.getRelative(BlockFace.SOUTH_EAST)));
                    blockArray.add(plugin.getBlockKey(block.getRelative(BlockFace.SOUTH_EAST).getRelative(BlockFace.UP)));
                } else if(block.getRelative(BlockFace.WEST).getType() == Material.SOUL_CAMPFIRE && block.getRelative(BlockFace.WEST,2).getType() == Material.BLAST_FURNACE
                        && block.getRelative(BlockFace.WEST).getRelative(BlockFace.UP).getType() == Material.NETHERITE_BLOCK && block.getRelative(BlockFace.WEST,2).getRelative(BlockFace.UP).getType() == Material.IRON_BARS
                        && block.getRelative(BlockFace.NORTH_WEST).getType() == Material.BLAST_FURNACE && block.getRelative(BlockFace.NORTH_WEST).getRelative(BlockFace.UP).getType() == Material.IRON_BARS
                        && block.getRelative(BlockFace.SOUTH_WEST).getType() == Material.BLAST_FURNACE && block.getRelative(BlockFace.SOUTH_WEST).getRelative(BlockFace.UP).getType() == Material.IRON_BARS
                ) {
                    blockArray.add(plugin.getBlockKey(block.getRelative(BlockFace.WEST)));
                    blockArray.add(plugin.getBlockKey(block.getRelative(BlockFace.WEST,2)));
                    blockArray.add(plugin.getBlockKey(block.getRelative(BlockFace.WEST).getRelative(BlockFace.UP)));
                    blockArray.add(plugin.getBlockKey(block.getRelative(BlockFace.WEST,2).getRelative(BlockFace.UP)));
                    blockArray.add(plugin.getBlockKey(block.getRelative(BlockFace.NORTH_WEST)));
                    blockArray.add(plugin.getBlockKey(block.getRelative(BlockFace.NORTH_WEST).getRelative(BlockFace.UP)));
                    blockArray.add(plugin.getBlockKey(block.getRelative(BlockFace.SOUTH_WEST)));
                    blockArray.add(plugin.getBlockKey(block.getRelative(BlockFace.SOUTH_WEST).getRelative(BlockFace.UP)));
                } else if(block.getRelative(BlockFace.NORTH).getType() == Material.SOUL_CAMPFIRE && block.getRelative(BlockFace.NORTH,2).getType() == Material.BLAST_FURNACE
                        && block.getRelative(BlockFace.NORTH).getRelative(BlockFace.UP).getType() == Material.NETHERITE_BLOCK && block.getRelative(BlockFace.NORTH,2).getRelative(BlockFace.UP).getType() == Material.IRON_BARS
                        && block.getRelative(BlockFace.NORTH_WEST).getType() == Material.BLAST_FURNACE && block.getRelative(BlockFace.NORTH_WEST).getRelative(BlockFace.UP).getType() == Material.IRON_BARS
                        && block.getRelative(BlockFace.NORTH_EAST).getType() == Material.BLAST_FURNACE && block.getRelative(BlockFace.NORTH_EAST).getRelative(BlockFace.UP).getType() == Material.IRON_BARS
                ) {
                    blockArray.add(plugin.getBlockKey(block.getRelative(BlockFace.NORTH)));
                    blockArray.add(plugin.getBlockKey(block.getRelative(BlockFace.NORTH,2)));
                    blockArray.add(plugin.getBlockKey(block.getRelative(BlockFace.NORTH).getRelative(BlockFace.UP)));
                    blockArray.add(plugin.getBlockKey(block.getRelative(BlockFace.NORTH,2).getRelative(BlockFace.UP)));
                    blockArray.add(plugin.getBlockKey(block.getRelative(BlockFace.NORTH_WEST)));
                    blockArray.add(plugin.getBlockKey(block.getRelative(BlockFace.NORTH_WEST).getRelative(BlockFace.UP)));
                    blockArray.add(plugin.getBlockKey(block.getRelative(BlockFace.NORTH_EAST)));
                    blockArray.add(plugin.getBlockKey(block.getRelative(BlockFace.NORTH_EAST).getRelative(BlockFace.UP)));
                } else if(block.getRelative(BlockFace.SOUTH).getType() == Material.SOUL_CAMPFIRE && block.getRelative(BlockFace.SOUTH,2).getType() == Material.BLAST_FURNACE
                        && block.getRelative(BlockFace.SOUTH).getRelative(BlockFace.UP).getType() == Material.NETHERITE_BLOCK && block.getRelative(BlockFace.SOUTH,2).getRelative(BlockFace.UP).getType() == Material.IRON_BARS
                        && block.getRelative(BlockFace.SOUTH_WEST).getType() == Material.BLAST_FURNACE && block.getRelative(BlockFace.SOUTH_WEST).getRelative(BlockFace.UP).getType() == Material.IRON_BARS
                        && block.getRelative(BlockFace.SOUTH_EAST).getType() == Material.BLAST_FURNACE && block.getRelative(BlockFace.SOUTH_EAST).getRelative(BlockFace.UP).getType() == Material.IRON_BARS
                ) {
                    blockArray.add(plugin.getBlockKey(block.getRelative(BlockFace.SOUTH)));
                    blockArray.add(plugin.getBlockKey(block.getRelative(BlockFace.SOUTH,2)));
                    blockArray.add(plugin.getBlockKey(block.getRelative(BlockFace.SOUTH).getRelative(BlockFace.UP)));
                    blockArray.add(plugin.getBlockKey(block.getRelative(BlockFace.SOUTH,2).getRelative(BlockFace.UP)));
                    blockArray.add(plugin.getBlockKey(block.getRelative(BlockFace.SOUTH_WEST)));
                    blockArray.add(plugin.getBlockKey(block.getRelative(BlockFace.SOUTH_WEST).getRelative(BlockFace.UP)));
                    blockArray.add(plugin.getBlockKey(block.getRelative(BlockFace.SOUTH_EAST)));
                    blockArray.add(plugin.getBlockKey(block.getRelative(BlockFace.SOUTH_EAST).getRelative(BlockFace.UP)));
                } else return;

                factoryData.set(plugin.getBlockKey(block)+".Blocks",blockArray);
                factoryData.set(plugin.getBlockKey(block)+".Type","Spectral Forge");
                factoryData.set(plugin.getBlockKey(block)+".Fuel",0);
                plugin.saveFactoryData();
                ApplyEffect ae = new ApplyEffect(plugin);
                ae.applyEffect(plugin.getBlockKey(block),false);
                event.getPlayer().sendRawMessage("Spectral Forge successfully created!");
            }
        }
    }
}
