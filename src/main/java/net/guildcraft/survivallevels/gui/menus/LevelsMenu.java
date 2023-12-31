package net.guildcraft.survivallevels.gui.menus;

import net.guildcraft.survivallevels.SurvivalLevels;
import net.guildcraft.survivallevels.data.GPlayer;
import net.guildcraft.survivallevels.gui.GUITemplate;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.enchantments.Enchantment;
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

public class LevelsMenu extends GUITemplate {
    private final SurvivalLevels plugin;
    private final Player player;
    private final GPlayer gPlayer;

    public LevelsMenu(SurvivalLevels plugin, Player player) {
        super(plugin, 5, "&8Levels");
        this.plugin = plugin;
        this.player = player;
        this.gPlayer = GPlayer.getPlayerData(plugin, player.getUniqueId());
        initialize();
    }
    public void initialize() {
        setItem(11, createLevelItem(1));
        setItem(12, createLevelItem(2));
        setItem(13, createLevelItem(3));
        setItem(14, createLevelItem(4));
        setItem(15, createLevelItem(5));
        setItem(20, createLevelItem(6));
        setItem(21, createLevelItem(7));
        setItem(22, createLevelItem(8));
        setItem(23, createLevelItem(9));
        setItem(24, createLevelItem(10));
        setItem(40, createCloseItem(), p -> {
            p.getOpenInventory().close();
            new MainMenu(plugin, p).open(p);
        });
    }
    public ItemStack createLevelItem(int level) {
        List<String> lore = new ArrayList<String>();
        ItemStack i = new ItemStack(Material.PLAYER_HEAD, 1, (short) SkullType.PLAYER.ordinal());
        SkullMeta meta = (SkullMeta) i.getItemMeta();
        PlayerProfile profile = Bukkit.createPlayerProfile(UUID.randomUUID());
        PlayerTextures textures = profile.getTextures();
        URL l;

        try {
            l = new URL(plugin.getConfig().getString("level_items."+level+".TEXTURE"));
        } catch (MalformedURLException e) {
            l = null;
        }
        textures.setSkin(l);
        meta.setOwnerProfile(profile);
        i.setItemMeta(meta);
        String name = plugin.colourize(plugin.getConfig().getString("level_items."+level+".NAME"));

        for(String lines : plugin.getConfig().getStringList("level_items."+level+".LORE")) {
            lines = lines.replace("%xp_required%", plugin.getConfig().getInt("level_requirements." + level) + "");
            if (gPlayer.getLevel() >= level) {
                lines = lines.replace("%lore_outcome%", plugin.getConfig().getString("lore_outcomes.pass"));
            } else {
                lines = lines.replace("%lore_outcome%", plugin.getConfig().getString("lore_outcomes.fail"));
            }
            lore.add(plugin.colourize(lines));
        }

        meta.setLore(lore);
        meta.setDisplayName(name);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);

        i.setItemMeta(meta);

        return i;
    }
    public ItemStack createCloseItem() {
        final ItemStack i = new ItemStack(Material.ARROW, 1);
        String name = "&cReturn";
        final ItemMeta meta = i.getItemMeta();
        meta.setDisplayName(plugin.colourize(name));
        i.setItemMeta(meta);
        return i;
    }
}
