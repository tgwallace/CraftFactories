package hardbuckaroo.craftfactories;
import org.bukkit.Bukkit;

import java.util.ArrayList;

public class RecipeRun {
    public String blockKey;
    public String type;
    public int id;
    public String recipe;
    public long durationRemaining;
    private static RecipeRun runs;
    public ArrayList<RecipeRun> runningFactories = new ArrayList<>();
    public RecipeRun(String blockKey, String type, int id, long durationRemaining, String recipe) {
        this.blockKey = blockKey;
        this.type = type;
        this.id = id;
        this.recipe = recipe;
        this.durationRemaining = durationRemaining;
    }

    public RecipeRun(){}

    public RecipeRun getRunFromBlockKey(String blockKey) {
        for(RecipeRun run : runningFactories) {
            if(run.getBlockKey().equalsIgnoreCase(blockKey)) {
                return run;
            }
        }
        return null;
    }
    public void subtractTime(long l){
        durationRemaining-=l;
    }

    public String getBlockKey(){
        return blockKey;
    }

    public String getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public long getDurationRemaining(){
        return durationRemaining;
    }

    public String getRecipe() {
        return recipe;
    }

    public static RecipeRun getInstance() {
        if(runs == null) {
            runs = new RecipeRun();
        }
        return runs;
    }

    public void deleteRun(String blockKey) {
        runningFactories.removeIf(run -> run.getBlockKey().equalsIgnoreCase(blockKey));
    }
}
