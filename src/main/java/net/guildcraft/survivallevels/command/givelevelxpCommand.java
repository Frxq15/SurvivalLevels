package net.guildcraft.survivallevels.command;

import net.guildcraft.survivallevels.SurvivalLevels;
import net.guildcraft.survivallevels.data.GPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class givelevelxpCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender p, Command command, String s, String[] args) {
        SurvivalLevels plugin = SurvivalLevels.getInstance();
        if(!p.hasPermission("survivallevels.givelevelxp")) {
            p.sendMessage(plugin.formatMsg("NO_PERMISSION"));
            return true;
        }
        if(args.length == 2) {
            String t = args[0];
            if(Bukkit.getPlayer(t) == null) {
                p.sendMessage(plugin.formatMsg("PLAYER_NOT_FOUND"));
                return true;
            }
            Player target = Bukkit.getPlayer(t);
            GPlayer gPlayer = GPlayer.getPlayerData(plugin, target.getUniqueId());

            int amount;
            try {
                amount = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                p.sendMessage(plugin.colourize("&cError! Please enter a valid integer."));
                return true;
            }
            gPlayer.addXP(amount);
            p.sendMessage(plugin.formatMsg("XP_ADDED").replace("%player%", target.getName()).replace("%amount%", amount+""));
            return true;
        }
        p.sendMessage(plugin.colourize("&cUsage: /givelevelxp <player <amount>"));
        return true;
    }
}
