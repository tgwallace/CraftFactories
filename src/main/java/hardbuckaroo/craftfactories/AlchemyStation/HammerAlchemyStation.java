package hardbuckaroo.craftfactories.AlchemyStation;

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

public class HammerAlchemyStation implements Listener {
    private final CraftFactories plugin;
    public HammerAlchemyStation(CraftFactories plugin) {this.plugin = plugin;}

    @EventHandler
    public void register(PlayerInteractEvent event) {
        if(event.getHand() == EquipmentSlot.HAND && event.getPlayer().getInventory().getItemInMainHand().getType() == Material.WOODEN_HOE && event.getClickedBlock() != null) {
            Block block = event.getClickedBlock();
            FileConfiguration factoryData = plugin.getfactoryData();

            if(!factoryData.contains(plugin.getBlockKey(block)) && block.getType() == Material.BARREL) {
                ArrayList<String> blockArray = new ArrayList<>();
                blockArray.add(plugin.getBlockKey(block));
                //check for aspects
                if(block.getRelative(BlockFace.EAST).getType().toString().contains("FENCE") && block.getRelative(BlockFace.EAST).getRelative(BlockFace.DOWN).getType().toString().contains("FENCE")
                        && block.getRelative(BlockFace.DOWN).getType() == Material.SOUL_CAMPFIRE
                        && block.getRelative(BlockFace.WEST).getType() == Material.GOLD_BLOCK && block.getRelative(BlockFace.WEST).getRelative(BlockFace.DOWN).getType() == Material.SOUL_CAMPFIRE && block.getRelative(BlockFace.WEST).getRelative(BlockFace.UP).getType() == Material.BREWING_STAND
                        && block.getRelative(BlockFace.WEST,2).getType().toString().contains("FENCE") && block.getRelative(BlockFace.WEST,2).getRelative(BlockFace.DOWN).getType().toString().contains("FENCE")
                ) {
                    blockArray.add(plugin.getBlockKey(block.getRelative(BlockFace.EAST)));
                    blockArray.add(plugin.getBlockKey(block.getRelative(BlockFace.EAST).getRelative(BlockFace.DOWN)));
                    blockArray.add(plugin.getBlockKey(block.getRelative(BlockFace.DOWN)));
                    blockArray.add(plugin.getBlockKey(block.getRelative(BlockFace.WEST)));
                    blockArray.add(plugin.getBlockKey(block.getRelative(BlockFace.WEST).getRelative(BlockFace.DOWN)));
                    blockArray.add(plugin.getBlockKey(block.getRelative(BlockFace.WEST).getRelative(BlockFace.UP)));
                    blockArray.add(plugin.getBlockKey(block.getRelative(BlockFace.WEST,2)));
                    blockArray.add(plugin.getBlockKey(block.getRelative(BlockFace.WEST,2).getRelative(BlockFace.DOWN)));
                } else if(block.getRelative(BlockFace.WEST).getType().toString().contains("FENCE") && block.getRelative(BlockFace.WEST).getRelative(BlockFace.DOWN).getType().toString().contains("FENCE")
                        && block.getRelative(BlockFace.DOWN).getType() == Material.SOUL_CAMPFIRE
                        && block.getRelative(BlockFace.EAST).getType() == Material.GOLD_BLOCK && block.getRelative(BlockFace.EAST).getRelative(BlockFace.DOWN).getType() == Material.SOUL_CAMPFIRE && block.getRelative(BlockFace.EAST).getRelative(BlockFace.UP).getType() == Material.BREWING_STAND
                        && block.getRelative(BlockFace.EAST,2).getType().toString().contains("FENCE") && block.getRelative(BlockFace.EAST,2).getRelative(BlockFace.DOWN).getType().toString().contains("FENCE")
                ) {
                    blockArray.add(plugin.getBlockKey(block.getRelative(BlockFace.WEST)));
                    blockArray.add(plugin.getBlockKey(block.getRelative(BlockFace.WEST).getRelative(BlockFace.DOWN)));
                    blockArray.add(plugin.getBlockKey(block.getRelative(BlockFace.DOWN)));
                    blockArray.add(plugin.getBlockKey(block.getRelative(BlockFace.EAST)));
                    blockArray.add(plugin.getBlockKey(block.getRelative(BlockFace.EAST).getRelative(BlockFace.DOWN)));
                    blockArray.add(plugin.getBlockKey(block.getRelative(BlockFace.EAST).getRelative(BlockFace.UP)));
                    blockArray.add(plugin.getBlockKey(block.getRelative(BlockFace.EAST,2)));
                    blockArray.add(plugin.getBlockKey(block.getRelative(BlockFace.EAST,2).getRelative(BlockFace.DOWN)));
                } else if(block.getRelative(BlockFace.NORTH).getType().toString().contains("FENCE") && block.getRelative(BlockFace.NORTH).getRelative(BlockFace.DOWN).getType().toString().contains("FENCE")
                        && block.getRelative(BlockFace.DOWN).getType() == Material.SOUL_CAMPFIRE
                        && block.getRelative(BlockFace.SOUTH).getType() == Material.GOLD_BLOCK && block.getRelative(BlockFace.SOUTH).getRelative(BlockFace.DOWN).getType() == Material.SOUL_CAMPFIRE && block.getRelative(BlockFace.SOUTH).getRelative(BlockFace.UP).getType() == Material.BREWING_STAND
                        && block.getRelative(BlockFace.SOUTH,2).getType().toString().contains("FENCE") && block.getRelative(BlockFace.SOUTH,2).getRelative(BlockFace.DOWN).getType().toString().contains("FENCE")
                ) {
                    blockArray.add(plugin.getBlockKey(block.getRelative(BlockFace.NORTH)));
                    blockArray.add(plugin.getBlockKey(block.getRelative(BlockFace.NORTH).getRelative(BlockFace.DOWN)));
                    blockArray.add(plugin.getBlockKey(block.getRelative(BlockFace.DOWN)));
                    blockArray.add(plugin.getBlockKey(block.getRelative(BlockFace.SOUTH)));
                    blockArray.add(plugin.getBlockKey(block.getRelative(BlockFace.SOUTH).getRelative(BlockFace.DOWN)));
                    blockArray.add(plugin.getBlockKey(block.getRelative(BlockFace.SOUTH).getRelative(BlockFace.UP)));
                    blockArray.add(plugin.getBlockKey(block.getRelative(BlockFace.SOUTH,2)));
                    blockArray.add(plugin.getBlockKey(block.getRelative(BlockFace.SOUTH,2).getRelative(BlockFace.DOWN)));
                } else if(block.getRelative(BlockFace.SOUTH).getType().toString().contains("FENCE") && block.getRelative(BlockFace.SOUTH).getRelative(BlockFace.DOWN).getType().toString().contains("FENCE")
                        && block.getRelative(BlockFace.DOWN).getType() == Material.SOUL_CAMPFIRE
                        && block.getRelative(BlockFace.NORTH).getType() == Material.GOLD_BLOCK && block.getRelative(BlockFace.NORTH).getRelative(BlockFace.DOWN).getType() == Material.SOUL_CAMPFIRE && block.getRelative(BlockFace.NORTH).getRelative(BlockFace.UP).getType() == Material.BREWING_STAND
                        && block.getRelative(BlockFace.NORTH,2).getType().toString().contains("FENCE") && block.getRelative(BlockFace.NORTH,2).getRelative(BlockFace.DOWN).getType().toString().contains("FENCE")
                ) {
                    blockArray.add(plugin.getBlockKey(block.getRelative(BlockFace.SOUTH)));
                    blockArray.add(plugin.getBlockKey(block.getRelative(BlockFace.SOUTH).getRelative(BlockFace.DOWN)));
                    blockArray.add(plugin.getBlockKey(block.getRelative(BlockFace.DOWN)));
                    blockArray.add(plugin.getBlockKey(block.getRelative(BlockFace.NORTH)));
                    blockArray.add(plugin.getBlockKey(block.getRelative(BlockFace.NORTH).getRelative(BlockFace.DOWN)));
                    blockArray.add(plugin.getBlockKey(block.getRelative(BlockFace.NORTH).getRelative(BlockFace.UP)));
                    blockArray.add(plugin.getBlockKey(block.getRelative(BlockFace.NORTH,2)));
                    blockArray.add(plugin.getBlockKey(block.getRelative(BlockFace.NORTH,2).getRelative(BlockFace.DOWN)));
                }

                factoryData.set(plugin.getBlockKey(block)+".Blocks",blockArray);
                factoryData.set(plugin.getBlockKey(block)+".Type","Alchemy Station");
                factoryData.set(plugin.getBlockKey(block)+".Fuel",0);
                plugin.saveFactoryData();
                event.getPlayer().sendRawMessage("Alchemy Station successfully created!");
            }
        }
    }
}
