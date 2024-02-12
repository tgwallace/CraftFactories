package hardbuckaroo.craftfactories;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class UnregisterBrokenFactories implements Listener {
    private final CraftFactories plugin;
    public UnregisterBrokenFactories(CraftFactories plugin) {this.plugin = plugin;}

    @EventHandler
    public void unregister(BlockBreakEvent event) {
        Block block = event.getBlock();
        String blockKey = plugin.getBlockKey(block);
        FileConfiguration factoryData = plugin.getfactoryData();
        for(String key : factoryData.getKeys(false)) {
            for(String checkKey : factoryData.getStringList(key+".Blocks")) {
                if(checkKey.equalsIgnoreCase(blockKey)) {

                    RecipeRun rr = RecipeRun.getInstance();
                    rr.deleteRun(key);

                    int jobID = rr.getId();
                    Bukkit.getScheduler().cancelTask(jobID);

                    ApplyEffect af = new ApplyEffect(plugin);
                    af.applyEffect(blockKey,false);

                    event.getPlayer().sendRawMessage(factoryData.getString(key+".Type")+ " has been broken!");
                    factoryData.set(key,null);
                }
            }
        }
        plugin.saveFactoryData();
    }
}
