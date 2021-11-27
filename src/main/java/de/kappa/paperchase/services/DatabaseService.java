package de.kappa.paperchase.services;

import de.kappa.paperchase.Main;

import java.sql.*;
import java.util.HashMap;

public class DatabaseService {

    public static Connection getConnection() {
        // TODO implement connection pooling with HikariCP in the future to prevent overhead

        HashMap<String, String> dbConfigValues = ConfigurationService.getDatabaseConfigValues();

        String jdbcUrl = "jdbc:mysql://" + dbConfigValues.get("host") + ":" + dbConfigValues.get("port") + "/" +
                dbConfigValues.get("database");

        try {
            return DriverManager
                    .getConnection(jdbcUrl, dbConfigValues.get("user"), dbConfigValues.get("password"));
        } catch (SQLException e) {
            Main.instance.getLogger().severe("Could not establish database connection");
        }

        return null;
    }
}
