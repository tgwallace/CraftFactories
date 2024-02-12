package hardbuckaroo.craftfactories;

import org.bukkit.block.Block;
import org.bukkit.block.data.Lightable;
import org.bukkit.configuration.file.FileConfiguration;

public class ApplyEffect {
    private final CraftFactories plugin;
    public ApplyEffect(CraftFactories plugin) {this.plugin = plugin;}
    
    public void applyEffect(String blockKey, boolean on) {
        FileConfiguration factoryData = plugin.getfactoryData();

        if(factoryData.contains(blockKey)) {
            for(String partKey : factoryData.getStringList(blockKey+".Blocks")) {
                Block partBlock = plugin.getBlockFromKey(partKey);
                if(partBlock.getBlockData() instanceof  Lightable) {
                    Lightable fire = (Lightable) partBlock.getBlockData();
                    fire.setLit(on);
                    partBlock.setBlockData(fire);
                }
            }
        }
    }
}
