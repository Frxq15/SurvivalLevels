package net.guildcraft.survivallevels.gui.menus;

import net.guildcraft.survivallevels.SurvivalLevels;
import net.guildcraft.survivallevels.data.GPlayer;
import net.guildcraft.survivallevels.gui.GUITemplate;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainMenu extends GUITemplate {
    private final SurvivalLevels plugin;
    private final Player player;
    private final GPlayer gPlayer;

    public MainMenu(SurvivalLevels plugin, Player player) {
        super(plugin, 5, "&8Survival Levels");
        this.plugin = plugin;
        this.player = player;
        this.gPlayer = GPlayer.getPlayerData(plugin, player.getUniqueId());
        initialize();
    }
    public void initialize() {
        setItem(11, createSkull());
        setItem(15, createLevelsItem(), p -> {
            p.getOpenInventory().close();
            new LevelsMenu(plugin, p).open(p);
        });
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
        lore.add(plugin.colourize("&eLevels increase automatically."));

        meta.setLore(lore);
        meta.setDisplayName(name);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);

        i.setItemMeta(meta);

        return i;
    }
    ItemStack createLevelsItem() {
        ItemStack i = new ItemStack(Material.PLAYER_HEAD, 1, (short) SkullType.PLAYER.ordinal());
        SkullMeta meta = (SkullMeta) i.getItemMeta();
        List<String> lore = new ArrayList<String>();
        PlayerProfile profile = Bukkit.createPlayerProfile(UUID.randomUUID());
        PlayerTextures textures = profile.getTextures();
        URL l;
        try {
            l = new URL(plugin.getConfig().getString("level_items.menu_texture"));
        } catch (MalformedURLException e) {
            l = null;
        }
        textures.setSkin(l);
        meta.setOwnerProfile(profile);
        i.setItemMeta(meta);
        String name = plugin.colourize("&aSurvival Levels");
        lore.add(plugin.colourize("&7Work your way through the different survival"));
        lore.add(plugin.colourize("&7levels and unlock amazing rewards and perks as you"));
        lore.add(plugin.colourize("&7go along. It wont be easy!"));
        lore.add(plugin.colourize(""));
        lore.add(plugin.colourize("&eClick to view the levels."));
        meta.setDisplayName(name);
        meta.setLore(lore);
        i.setItemMeta(meta);
        return i;
    }
}
