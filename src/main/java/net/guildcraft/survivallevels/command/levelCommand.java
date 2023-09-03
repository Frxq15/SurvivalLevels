package net.guildcraft.survivallevels.command;

import net.guildcraft.survivallevels.SurvivalLevels;
import net.guildcraft.survivallevels.gui.menus.MainMenu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class levelCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        SurvivalLevels plugin = SurvivalLevels.getInstance();
        if(!(commandSender instanceof Player)) {
            plugin.log("This command cannot be executed from console.");
            return true;
        }
        Player p = (Player)commandSender;
        if(!p.hasPermission("survivallevels.level")) {
            p.sendMessage(plugin.formatMsg("NO_PERMISSION"));
            return true;
        }
        if(strings.length == 0) {
            new MainMenu(plugin, p).open(p);
            return true;
        }
        p.sendMessage(plugin.colourize("&cUsage: /level"));
        return true;
    }
}
