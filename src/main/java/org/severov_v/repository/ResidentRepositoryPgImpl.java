package org.severov_v.repository;

import lombok.Getter;
import org.severov_v.db.JDBCManager;
import org.severov_v.db.PgManager;
import org.severov_v.entities.Resident;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ResidentRepositoryPgImpl implements ResidentRepository {
    @Getter
    private static final ResidentRepositoryPgImpl instance = new ResidentRepositoryPgImpl();

    private final PreparedStatement insertStatement;
    private final PreparedStatement updateStatement;
    private final PreparedStatement deleteByIdStatement;
    private final PreparedStatement deleteAllStatement;
    private final PreparedStatement findByIdStatement;
    private final PreparedStatement findAllStatement;
    private final PreparedStatement getByNameStatement;
    private final PreparedStatement getByPhoneStatement;

    private ResidentRepositoryPgImpl() {
        try {
            Connection connection = PgManager.getInstance().getConnection();

            this.insertStatement = connection.prepareStatement(
                    "INSERT INTO residents (full_name, phone) VALUES (?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );

            this.updateStatement = connection.prepareStatement(
                    "UPDATE residents SET full_name = ?, phone = ? WHERE id = ?"
            );

            this.deleteByIdStatement = connection.prepareStatement(
                    "DELETE FROM residents WHERE id = ?"
            );

            this.deleteAllStatement = connection.prepareStatement(
                    "DELETE FROM residents"
            );

            this.findByIdStatement = connection.prepareStatement(
                    "SELECT id, full_name, phone FROM residents WHERE id = ?"
            );

            this.findAllStatement = connection.prepareStatement(
                    "SELECT id, full_name, phone FROM residents"
            );

            this.getByNameStatement = connection.prepareStatement(
                    "SELECT id, full_name, phone FROM residents WHERE full_name LIKE ?"
            );

            this.getByPhoneStatement = connection.prepareStatement(
                    "SELECT id, full_name, phone FROM residents WHERE phone = ?"
            );
        } catch (SQLException e) {
            throw new RuntimeException("Error while preparing database statements", e);
        }
    }

    @Override
    public synchronized void create(Resident object) {
        try {
            insertStatement.clearParameters();
            insertStatement.setString(1, object.getFullName());
            insertStatement.setString(2, object.getPhoneNumber());
            insertStatement.executeUpdate();

            try (ResultSet keys = insertStatement.getGeneratedKeys()) {
                if (keys.next()) {
                    object.setId(keys.getLong(1));
                }
            }

            System.out.println("Created resident with id=" + object.getId());
        } catch (SQLException e) {
            throw new RuntimeException("DB error during create", e);
        }
    }

    @Override
    public synchronized void deleteAll() {
        try {
            deleteAllStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("DB error during deleteAll", e);
        }
    }

    @Override
    public synchronized void update(Resident newObject) {
        try {
            updateStatement.clearParameters();
            updateStatement.setString(1, newObject.getFullName());
            updateStatement.setString(2, newObject.getPhoneNumber());
            updateStatement.setLong(3, newObject.getId());
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("DB error during update", e);
        }
    }

    @Override
    public synchronized void delete(long id) {
        try {
            deleteByIdStatement.clearParameters();
            deleteByIdStatement.setLong(1, id);
            deleteByIdStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("DB error during delete", e);
        }
    }

    @Override
    public synchronized Resident getById(long id) {
        try {
            findByIdStatement.clearParameters();
            findByIdStatement.setLong(1, id);
            try (ResultSet rs = findByIdStatement.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("DB error during getById", e);
        }
    }

    @Override
    public synchronized List<Resident> getAll() {
        try (ResultSet rs = findAllStatement.executeQuery()) {
            List<Resident> result = new ArrayList<>();
            while (rs.next()) {
                result.add(mapRow(rs));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("DB error during getAll", e);
        }
    }

    @Override
    public synchronized List<Resident> getByName(String name) {
        try {
            getByNameStatement.clearParameters();
            getByNameStatement.setString(1, "%" + name + "%");
            try (ResultSet rs = getByNameStatement.executeQuery()) {
                List<Resident> result = new ArrayList<>();
                while (rs.next()) {
                    result.add(mapRow(rs));
                }
                return result;
            }
        } catch (SQLException e) {
            throw new RuntimeException("DB error during getByName", e);
        }
    }

    @Override
    public synchronized Resident getByPhone(String phone) {
        try {
            getByPhoneStatement.clearParameters();
            getByPhoneStatement.setString(1, phone);
            try (ResultSet rs = getByPhoneStatement.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("DB error during getByPhone", e);
        }
    }

    private Resident mapRow(ResultSet rs) throws SQLException {
        return Resident.builder()
                .id(rs.getLong("id"))
                .phoneNumber(rs.getString("phone"))
                .fullName(rs.getString("full_name"))
                .build();
    }
}