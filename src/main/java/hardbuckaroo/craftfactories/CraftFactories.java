package hardbuckaroo.craftfactories;

import hardbuckaroo.craftfactories.AlchemyStation.HammerAlchemyStation;
import hardbuckaroo.craftfactories.HotComposter.HammerHotComposter;
import hardbuckaroo.craftfactories.Smithy.HammerSmithy;
import hardbuckaroo.craftfactories.SpectralForge.HammerSpectralForge;
import hardbuckaroo.craftfactories.StewBarrel.HammerStewBarrel;
import hardbuckaroo.craftfactories.Tannery.HammerTannery;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public final class CraftFactories extends JavaPlugin {
    private File factoryDataFile;
    private FileConfiguration factoryData;
    private File recipesFile;
    private FileConfiguration recipes;

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.saveDefaultConfig();
        createCustomConfigs();

        RestartRuns restartRuns = new RestartRuns(this);
        restartRuns.restart();

        Bukkit.getPluginManager().registerEvents(new HammerStewBarrel(this), this);
        Bukkit.getPluginManager().registerEvents(new HammerHotComposter(this), this);
        Bukkit.getPluginManager().registerEvents(new HammerTannery(this), this);
        Bukkit.getPluginManager().registerEvents(new HammerSmithy(this), this);
        Bukkit.getPluginManager().registerEvents(new HammerSpectralForge(this), this);
        Bukkit.getPluginManager().registerEvents(new HammerAlchemyStation(this), this);

        Bukkit.getPluginManager().registerEvents(new UnregisterBrokenFactories(this), this);
        Bukkit.getPluginManager().registerEvents(new ExitInventoryListener(this),this);
        Bukkit.getPluginManager().registerEvents(new ShowFactoryInfo(this),this);

        RecipeRun recipeRun = RecipeRun.getInstance();
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, () -> {
            for(RecipeRun run : recipeRun.runningFactories) {
                run.subtractTime(20);
            }
        },20L,20L);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        RecipeRun rr = RecipeRun.getInstance();
        for(RecipeRun run : rr.runningFactories) {
            factoryData.set(run.getBlockKey()+".Recipe",run.getRecipe());
            factoryData.set(run.getBlockKey()+".Duration",run.getDurationRemaining());
        }
        saveFactoryData();
    }

    public String getBlockKey(Block block) {
        return block.getWorld().getName()+","+block.getX()+","+block.getY()+","+block.getZ();
    }

    public Block getBlockFromKey(String blockKey) {
        String[] keyParts = blockKey.split(",");
        World world = Bukkit.getServer().getWorld(keyParts[0]);
        if(world == null) {
            getLogger().log(Level.WARNING,"Null world check, has "+keyParts[0]+" been deleted?");
            return null;
        }
        else return world.getBlockAt(Integer.parseInt(keyParts[1]),Integer.parseInt(keyParts[2]),Integer.parseInt(keyParts[3]));
    }

    public FileConfiguration getfactoryData() {
        return factoryData;
    }

    public void saveFactoryData(){
        try {
            this.factoryData.save(this.factoryDataFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public FileConfiguration getRecipes() {
        return recipes;
    }

    public void saveRecipes(){
        try {
            this.recipes.save(this.recipesFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void createCustomConfigs() {
        factoryDataFile = new File(getDataFolder(), "factoryData.yml");
        if (!factoryDataFile.exists()) {
            factoryDataFile.getParentFile().mkdirs();
            saveResource("factoryData.yml", false);
        }

        factoryData = new YamlConfiguration();
        try {
            factoryData.load(factoryDataFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

        recipesFile = new File(getDataFolder(), "recipes.yml");
        if (!recipesFile.exists()) {
            recipesFile.getParentFile().mkdirs();
            saveResource("recipes.yml", false);
        }

        recipes = new YamlConfiguration();
        try {
            recipes.load(recipesFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
}
