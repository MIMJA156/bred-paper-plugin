package org.mimja156.bred;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandExecutor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

class IsEnabled {
    private boolean isEnabled;

    public IsEnabled(boolean startingState){
        this.isEnabled = startingState;
    }
    public boolean get(){
        return this.isEnabled;
    }

    public void set(boolean newState){
        this.isEnabled = newState;
        JavaPlugin.getPlugin(Bred.class).getConfig().set("isEnabled", newState);
        JavaPlugin.getPlugin(Bred.class).saveConfig();
    }
}

public final class Bred extends JavaPlugin implements CommandExecutor {
    public NamespacedKey tKey = new NamespacedKey(this, "mimja_top_bread");
    public NamespacedKey bKey = new NamespacedKey(this, "mimja_bottom_bread");

    public ItemStack bread = new ItemStack(Material.BREAD);
    public ShapedRecipe breadRecipeTop = new ShapedRecipe(tKey, bread);
    public ShapedRecipe breadRecipeBottom = new ShapedRecipe(bKey, bread);

    @Override
    public void onEnable() {
        System.out.println("Hello World!");
        saveDefaultConfig();

        boolean isEnabledFromConfig = (boolean) getConfig().get("isEnabled");
        IsEnabled isEnabled = new IsEnabled(isEnabledFromConfig);

        Objects.requireNonNull(getCommand("state")).setExecutor(new Commands(tKey, bKey, breadRecipeTop, breadRecipeBottom, isEnabled));

        breadRecipeTop.shape(
                "!!",
                " !");

        breadRecipeTop.shape(
                "!!",
                "! ");

        breadRecipeTop.setIngredient('!', Material.WHEAT);

        breadRecipeBottom.shape(
                " !",
                "!!");

        breadRecipeBottom.shape(
                "! ",
                "!!");

        breadRecipeBottom.setIngredient('!', Material.WHEAT);


        if (isEnabled.get()){
            getServer().addRecipe(breadRecipeTop);
            getServer().addRecipe(breadRecipeBottom);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("Goodbye World!");
    }
}
