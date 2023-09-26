package net.guildcraft.survivallevels.command;

import net.guildcraft.survivallevels.SurvivalLevels;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class givelevelxpCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender p, Command command, String s, String[] strings) {
        SurvivalLevels plugin = SurvivalLevels.getInstance();
        if(!p.hasPermission("survivallevels.givelevelxp")) {
            p.sendMessage(plugin.formatMsg("NO_PERMISSION"));
            return true;
        }
        return true;
    }
}
