package hardbuckaroo.craftfactories;

import org.apache.commons.lang3.text.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.ArrayList;

public class ShowFactoryInfo implements Listener {
    private final CraftFactories plugin;
    public ShowFactoryInfo(CraftFactories plugin) {this.plugin = plugin;}

    @EventHandler
    public void showInfo(PlayerInteractEvent event) {
        Block block = event.getClickedBlock();
        Player player = event.getPlayer();
        FileConfiguration factoryData = plugin.getfactoryData();
        FileConfiguration recipes = plugin.getRecipes();

        if(event.getHand() != EquipmentSlot.HAND || event.getAction() != Action.LEFT_CLICK_BLOCK) return;

        if(factoryData.contains(plugin.getBlockKey(block)) && !player.isSneaking() && RecipeRun.getInstance().getRunFromBlockKey(plugin.getBlockKey(block)) == null) {
            String type = factoryData.getString(plugin.getBlockKey(block)+".Type");
            int fuel = factoryData.getInt(plugin.getBlockKey(block)+".Fuel");
            player.sendRawMessage(type + ": " + fuel + " fuel remaining.");
        } else if(factoryData.contains(plugin.getBlockKey(block)) && !player.isSneaking() && RecipeRun.getInstance().getRunFromBlockKey(plugin.getBlockKey(block)) != null) {
            String type = factoryData.getString(plugin.getBlockKey(block) + ".Type");
            int fuel = factoryData.getInt(plugin.getBlockKey(block) + ".Fuel");
            RecipeRun run = RecipeRun.getInstance().getRunFromBlockKey(plugin.getBlockKey(block));
            String recipe = run.getRecipe();
            int secondsLeft = Math.round((float) run.getDurationRemaining()/20);

            if(secondsLeft < 60) {
                String s = "s";
                if(secondsLeft == 1) s="";
                player.sendRawMessage("Working on " + recipe + ", " + secondsLeft + " second"+s+" remaining.");
            } else if(secondsLeft < 3600) {
                int minutesLeft = secondsLeft/60;
                secondsLeft = secondsLeft%60;
                String s1 = "s";
                if(minutesLeft == 1) s1 = "";
                String s2 = "s";
                if(secondsLeft == 1) s2 = "";
                player.sendRawMessage("Working on " + recipe + "n " + minutesLeft + " minute"+s1+" and "+ secondsLeft +" second"+s2+" remaining.");
            }  else {
                int hoursLeft = secondsLeft/3600;
                int minutesLeft = (secondsLeft%3600)/60;
                String s1 = "s";
                if(hoursLeft == 1) s1 = "";
                String s2 = "s";
                if(minutesLeft == 1) s2 = "";
                player.sendRawMessage("Working on " + recipe + ", " + hoursLeft + " hour"+s1+" and "+minutesLeft+" minute"+s2+" remaining.");
            }
        } else if(factoryData.contains(plugin.getBlockKey(block)) && player.isSneaking()) {
            String type = factoryData.getString(plugin.getBlockKey(block)+".Type");
            ArrayList<String> recipeBook = new ArrayList<>();
            if(recipes.contains(type+".Recipes")) {
                for (String recipe : recipes.getConfigurationSection(type + ".Recipes").getKeys(false)) {
                    String recipeText = ChatColor.translateAlternateColorCodes('&', "&l&n ") + recipe + ChatColor.translateAlternateColorCodes('&', "&r") + "\n\n Ingredients:\n";
                    for (String component : recipes.getStringList(type + ".Recipes." + recipe + ".Input")) {
                        String material = component.split(",")[0];
                        int count = Integer.parseInt(component.split(",")[1]);
                        recipeText = recipeText.concat("- " + material + " (" + count + ")\n");
                    }
                    for (String component : recipes.getStringList(type + ".Recipes." + recipe + ".Conditions")) {
                        String material = component.split(",")[0];
                        int count = Integer.parseInt(component.split(",")[1]);
                        recipeText = recipeText.concat("- " + material + " (" + count + ")\n");
                    }
                    recipeText = recipeText.concat("\n Yields:\n");
                    for (String output : recipes.getStringList(type + ".Recipes." + recipe + ".Output")) {
                        String material = output.split(",")[0];
                        int count = Integer.parseInt(output.split(",")[1]);
                        recipeText = recipeText.concat("- " + material + " (" + count + ")\n");
                    }
                    recipeText = recipeText.concat("\n Fuel: " + recipes.getInt(type + ".Recipes." + recipe + ".Fuel"));
                    recipeText = recipeText.replace("_", " ");
                    recipeText = WordUtils.capitalizeFully(recipeText);
                    recipeBook.add(recipeText);
                }
            }
            ItemStack writtenBook = new ItemStack(Material.WRITTEN_BOOK);
            BookMeta bookMeta = (BookMeta) writtenBook.getItemMeta();
            bookMeta.setTitle(type + " Cookbook");
            bookMeta.setAuthor("HardBuckaroo");
            bookMeta.setPages(recipeBook);
            writtenBook.setItemMeta(bookMeta);
            player.openBook(writtenBook);
        }
    }
}
