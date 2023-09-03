package net.guildcraft.survivallevels.gui.menus;

import net.guildcraft.survivallevels.SurvivalLevels;
import net.guildcraft.survivallevels.data.GPlayer;
import net.guildcraft.survivallevels.gui.GUITemplate;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

public class MainMenu extends GUITemplate {
    private final SurvivalLevels plugin;
    private final Player player;
    private final GPlayer gPlayer;

    public MainMenu(SurvivalLevels plugin, Player player) {
        super(plugin, 3, "&8Survival Levels");
        this.plugin = plugin;
        this.player = player;
        this.gPlayer = GPlayer.getPlayerData(plugin, player.getUniqueId());
        initialize();
    }
    public void initialize() {
        setItem(11, createSkull());
    }
    public ItemStack createSkull() {
        List<String> lore = new ArrayList<String>();
        ItemStack i = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta meta = (SkullMeta) i.getItemMeta();
        meta.setOwner(player.getName());
        i.setItemMeta(meta);
        String name = plugin.colourize("&a"+player.getName());

        lore.add(plugin.colourize("&7Dive in head first with our level system."));
        lore.add(plugin.colourize(""));
        lore.add(plugin.colourize("&fCurrent Level: &b"+gPlayer.getLevel()));
        lore.add(plugin.colourize("&fCurrent XP: &b"+plugin.format(gPlayer.getXP())));
        lore.add(plugin.colourize(""));
        lore.add(plugin.colourize("&7You can increase your level by earning xp, xp can be earned"));
        lore.add(plugin.colourize("&7through many different ways such as fishing, killing mobs,"));
        lore.add(plugin.colourize("&7player kills and much more..."));
        lore.add(plugin.colourize(""));
        lore.add(plugin.colourize("&aLevels increase automatically."));

        meta.setLore(lore);
        meta.setDisplayName(name);
        i.setItemMeta(meta);

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);

        return i;
    }
}
