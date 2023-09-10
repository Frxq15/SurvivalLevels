package net.guildcraft.survivallevels.data;

import net.guildcraft.survivallevels.SurvivalLevels;
import org.bukkit.Bukkit;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class SQLSetterGetter {
    private SurvivalLevels plugin = SurvivalLevels.getInstance();
    private SQLManager sqlManager = plugin.getSQLManager();
    public boolean playerExists(UUID uuid) {
        try {
            PreparedStatement statement = sqlManager.getConnection().prepareStatement("SELECT * FROM survivallevels_users WHERE uuid=?");
            statement.setString(1, uuid.toString());
            ResultSet results = statement.executeQuery();
            if (results.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public void createTable(String table) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin.getInstance(), () -> {
            try {
                PreparedStatement statement = sqlManager.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS `" + table + "` (uuid VARCHAR(36) PRIMARY KEY, slevel INT(11), xp BIGINT(36));");
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
    public void createPlayer(final UUID uuid) {
        if (!sqlManager.isConnected() && !sqlManager.connect()) {
            plugin.getLogger().severe("Can't establish a database connection!");
            return;
        }
        if(playerExists(uuid)) {
            return;
        }
        try {
            PreparedStatement insert = sqlManager.getConnection()
                    .prepareStatement("INSERT INTO survivallevels_users (uuid,slevel,xp) VALUES (?,?,?)");
            insert.setString(1, uuid.toString());
            insert.setInt(2, 1);
            insert.setInt(3, 0);
            insert.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void updateLevel(UUID uuid, int level) {
        if (!sqlManager.isConnected() && !sqlManager.connect()) {
            plugin.getLogger().severe("Can't establish a database connection!");
            return;
        }
        if(!playerExists(uuid)) {
            plugin.log("An error whilst updating data for level "+uuid+", please contact the developer about this error.");
            return;
        }
        try {
            PreparedStatement statement = sqlManager.getConnection().prepareStatement("UPDATE survivallevels_users SET slevel=? WHERE uuid=?");
            statement.setInt(1, level);
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void updateXP(UUID uuid, int xp) {
        if (!sqlManager.isConnected() && !sqlManager.connect()) {
            plugin.getLogger().severe("Can't establish a database connection!");
            return;
        }
        if(!playerExists(uuid)) {
            plugin.log("An error whilst updating data for level "+uuid+", please contact the developer about this error.");
            return;
        }
        try {
            PreparedStatement statement = sqlManager.getConnection().prepareStatement("UPDATE survivallevels_users SET xp=? WHERE uuid=?");
            statement.setInt(1, xp);
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public int getLevel(UUID uuid) {
        if (!sqlManager.isConnected() && !sqlManager.connect()) {
            plugin.getLogger().severe("Can't establish a database connection!");
            return 0;
        }
        if(!playerExists(uuid)) {
            plugin.log("An error whilst getting data for uuid "+uuid+", please contact the developer about this error.");
            return 0;
        }
        try {
            PreparedStatement statement = sqlManager.getConnection().prepareStatement("SELECT slevel FROM survivallevels_users WHERE uuid=?");
            statement.setString(1, uuid.toString());
            ResultSet results = statement.executeQuery();
            results.next();
            return results.getInt("slevel");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 1;
    }
    public int getXP(UUID uuid) {
        if (!sqlManager.isConnected() && !sqlManager.connect()) {
            plugin.getLogger().severe("Can't establish a database connection!");
            return 0;
        }
        if(!playerExists(uuid)) {
            plugin.log("An error whilst getting data for uuid "+uuid+", please contact the developer about this error.");
            return 0;
        }
        try {
            PreparedStatement statement = sqlManager.getConnection().prepareStatement("SELECT xp FROM survivallevels_users WHERE uuid=?");
            statement.setString(1, uuid.toString());
            ResultSet results = statement.executeQuery();
            results.next();
            return results.getInt("xp");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
