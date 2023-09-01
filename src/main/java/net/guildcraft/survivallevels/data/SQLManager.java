package net.guildcraft.survivallevels.data;

import net.guildcraft.survivallevels.SurvivalLevels;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLManager {
    private final SurvivalLevels plugin;
    private Connection connection;
    private String host, database, username, password, table;
    private int port;

    public SQLManager(SurvivalLevels plugin) {
        this.plugin = plugin;
        setDetails();
    }
    public void setDetails() {
        this.host = plugin.getConfig().getString("database.host");
        this.port = plugin.getConfig().getInt("database.port");
        this.database = plugin.getConfig().getString("database.database");
        this.username = plugin.getConfig().getString("database.username");
        this.password = plugin.getConfig().getString("database.password");
    }
    public synchronized boolean connect() {
        try {
            if (connection != null && !connection.isClosed()) return true;

            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            connection = null;
            e.printStackTrace();
            return false;
        }
    }

    public synchronized void disconnect() {
        if (connection == null) return;

        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public synchronized boolean isConnected() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }

    public synchronized Connection getConnection() {
        return connection;
    }
}
