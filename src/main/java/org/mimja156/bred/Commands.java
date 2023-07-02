package org.mimja156.bred;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import static org.bukkit.Bukkit.getServer;

public class Commands implements CommandExecutor {
    private final NamespacedKey tKey;
    private final NamespacedKey bKey;
    private final Recipe breadRecipeTop;
    private final Recipe breadRecipeBottom;

    private final IsEnabled isEnabled;

    public Commands(NamespacedKey tKey, NamespacedKey bKey, ShapedRecipe breadRecipeTop, ShapedRecipe breadRecipeBottom, IsEnabled isEnabled){
        this.tKey = tKey;
        this.bKey = bKey;

        this.breadRecipeTop = breadRecipeTop;
        this.breadRecipeBottom = breadRecipeBottom;

        this.isEnabled = isEnabled;
    }

    @Override
    public boolean onCommand(CommandSender sender,Command command,String label, String[] args){
        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;

        if (command.getName().equalsIgnoreCase("state")) {
            if (args.length > 0){
                if (args[0].equalsIgnoreCase("disable") && this.isEnabled.get()) {
                    sender.sendMessage("Disabling...");
                    getServer().removeRecipe(this.tKey);
                    getServer().removeRecipe(this.bKey);
                    this.isEnabled.set(false);
                    sender.sendMessage("Disabled!");
                } else if (args[0].equalsIgnoreCase("enable") && !this.isEnabled.get()) {
                    sender.sendMessage("Enabling...");
                    getServer().addRecipe(this.breadRecipeTop);
                    getServer().addRecipe(this.breadRecipeBottom);
                    this.isEnabled.set(true);
                    sender.sendMessage("Enabled!");
                } else {
                    sender.sendMessage(String.format("§cInvalid state: \"%s\"§c\nPlease use either \"Enable\" or \"Disable\"", args[0]));
                    player.playSound(player.getLocation(), Sound.ENTITY_CREEPER_PRIMED, 1.0f, 1.0f);
                    return true;
                }
            } else {
                sender.sendMessage("§cInvalid syntax! Please see /help state§c");
                player.playSound(player.getLocation(), Sound.ENTITY_CREEPER_PRIMED, 1.0f, 1.0f);
                return true;
            }
        }

        return true;
    }
}
