package de.kappa.paperchase.repositories;

import de.kappa.paperchase.services.DatabaseService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

public class ItemRepository {

    public static Integer insertItemStack(
            UUID stackUUID,
            UUID worldUUID,
            double pos_x,
            double pos_y,
            double pos_z
    ) throws SQLException {
        return insertItemStack(stackUUID, worldUUID, pos_x, pos_y, pos_z, null);
    }

    public static Integer insertItemStack(
            UUID stackUUID,
            UUID worldUUID,
            double pos_x,
            double pos_y,
            double pos_z,
            String name
    ) throws SQLException {

        String insertQuery = "INSERT INTO paperchase_items (name, itemstack_uuid, world_uuid, pos_x, pos_y, pos_z) "
                + "VALUES (?,?,?,?,?,?)";

        PreparedStatement stmt = DatabaseService.getConnection().prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, name);
        stmt.setString(2, stackUUID.toString());
        stmt.setString(3, worldUUID.toString());
        stmt.setDouble(4, pos_x);
        stmt.setDouble(5, pos_y);
        stmt.setDouble(6, pos_z);

        int affectedRows = stmt.executeUpdate();

        if (affectedRows == 0) {
            throw new SQLException("Creating item failed, no rows affected.");
        }

        try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            }
            else {
                throw new SQLException("Creating item failed, no ID obtained.");
            }
        }
    }

    public static Integer insertItemFound (
            UUID playeruuid,
            Integer paperchaseItemId
    ) throws SQLException {
        String insertQuery = "INSERT INTO paperchase_found (player_uuid, paperchase_item_id, created) "
                + "VALUES (?,?,?)";

        PreparedStatement stmt = DatabaseService.getConnection().prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, playeruuid.toString());
        stmt.setInt(2, paperchaseItemId);
        stmt.setLong(3, System.currentTimeMillis()/1000L);

        int affectedRows = stmt.executeUpdate();

        if (affectedRows == 0) {
            throw new SQLException("Creating item_found failed, no rows affected.");
        }

        try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            }
            else {
                throw new SQLException("Creating item_found failed, no ID obtained.");
            }
        }
    }

    public static boolean doesItemFoundExist(
            UUID playeruuid,
            Integer paperchaseItemId
    ) throws SQLException {
        String query = "SELECT ID FROM paperchase_found WHERE player_uuid = ? AND paperchase_item_id = ?";

        PreparedStatement stmt = DatabaseService.getConnection().prepareStatement(query);
        stmt.setString(1, playeruuid.toString());
        stmt.setInt(2, paperchaseItemId);

        ResultSet result = stmt.executeQuery();

        return result.next();
    }

    public static Integer deleteItemFoundByItemId(
            Integer paperChaseId
    ) throws SQLException {
        String query = "DELETE FROM paperchase_found WHERE paperchase_item_id = ?";

        PreparedStatement stmt = DatabaseService.getConnection().prepareStatement(query);
        stmt.setInt(1, paperChaseId);

        return stmt.executeUpdate();
    }

    public static boolean deleteItemById(
            Integer paperChaseId
    ) throws SQLException {
        String query = "DELETE FROM paperchase_items WHERE ID = ?";

        PreparedStatement stmt = DatabaseService.getConnection().prepareStatement(query);
        stmt.setInt(1, paperChaseId);

        return stmt.executeUpdate() > 0;
    }
}
