package org.severov_v.repository;

import lombok.Getter;
import org.severov_v.db.JDBCManager;
import org.severov_v.db.PgManager;
import org.severov_v.entities.Request;
import org.severov_v.entities.RequestStatus;
import org.severov_v.entities.RequestType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RequestRepositoryPgImpl implements RequestRepository {
    @Getter
    private static final RequestRepositoryPgImpl instance = new RequestRepositoryPgImpl();

//    private static final JDBCManager jdbcManager = PgManager.getInstance(); // это ломается D:

    private final PreparedStatement insertStatement;
    private final PreparedStatement updateStatement;
    private final PreparedStatement deleteByIdStatement;
    private final PreparedStatement deleteAllStatement;
    private final PreparedStatement findByIdStatement;
    private final PreparedStatement findAllStatement;
    private final PreparedStatement getByComplaintStatement;
    private final PreparedStatement getByStatusStatement;
    private final PreparedStatement getByRequestTypeStatement;
    private final PreparedStatement getByComplainingIdStatement;
    private final PreparedStatement getByAddressStatement;

    private RequestRepositoryPgImpl() {
        try {
            // инициализация только здесь - если хранить референс на инстанс или коннекшн как финалку, там же
            // получать, то все ломается :skull:
            Connection connection = PgManager.getInstance().getConnection();

            this.insertStatement = connection.prepareStatement(
                    "INSERT INTO requests (complaint_text, type, id_complaining, house_address) " +
                            "VALUES (?, ?::request_type, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );

            this.updateStatement = connection.prepareStatement(
                    "UPDATE requests SET complaint_text = ?, status = ?::request_status, type = ?::request_type, id_complaining = ?, house_address = ?, updated_at = now() " +
                            "WHERE id = ?"
            );

            this.deleteByIdStatement = connection.prepareStatement(
                    "DELETE FROM requests WHERE id = ?"
            );

            this.deleteAllStatement = connection.prepareStatement(
                    "DELETE FROM requests"
            );

            this.findByIdStatement = connection.prepareStatement(
                    "SELECT id, complaint_text, status, type, id_complaining, house_address " +
                            "FROM requests WHERE id = ?"
            );

            this.findAllStatement = connection.prepareStatement(
                    "SELECT * " +
                            "FROM requests"
            );

            this.getByComplaintStatement = connection.prepareStatement(
                    "SELECT * " +
                            "FROM requests WHERE complaint_text LIKE ?"
            );

            this.getByStatusStatement = connection.prepareStatement(
                    "SELECT * " +
                            "FROM requests WHERE status = ?::request_status"
            );

            this.getByRequestTypeStatement = connection.prepareStatement(
                    "SELECT * " +
                            "FROM requests WHERE type = ?::request_type"
            );

            this.getByComplainingIdStatement = connection.prepareStatement(
                    "SELECT * " +
                            "FROM requests WHERE id_complaining = ?"
            );

            this.getByAddressStatement = connection.prepareStatement(
                    "SELECT * " +
                            "FROM requests WHERE house_address LIKE ?"
            );

        } catch (SQLException e) {
            throw new RuntimeException("Error while preparing database statements", e);
        }
    }

    @Override
    public synchronized void create(Request object) {
        try {
            insertStatement.clearParameters();
            insertStatement.setString(1, object.getComplaintText());
            insertStatement.setString(2, object.getType().name());
            insertStatement.setLong(3, object.getIdComplaining());
            insertStatement.setString(4, object.getHouseAddress());
            insertStatement.executeUpdate();

            try (ResultSet keys = insertStatement.getGeneratedKeys()) {
                if (keys.next()) {
                    object.setId(keys.getLong(1));
                }
            }
            System.out.println("Created request with id=" + object.getId());
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
    public synchronized void update(Request newObject) {
        try {
            updateStatement.clearParameters();
            updateStatement.setString(1, newObject.getComplaintText());
            updateStatement.setString(2, newObject.getStatus().name());
            updateStatement.setString(3, newObject.getType().name());
            updateStatement.setLong(4, newObject.getIdComplaining());
            updateStatement.setString(5, newObject.getHouseAddress());
            updateStatement.setLong(6, newObject.getId());
            updateStatement.executeUpdate();

            System.out.println("Updated request with id=" + newObject.getId());
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
    public synchronized Request getById(long id) {
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
    public synchronized List<Request> getAll() {
        try (ResultSet rs = findAllStatement.executeQuery()) {
            List<Request> result = new ArrayList<>();
            while (rs.next()) {
                result.add(mapRow(rs));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("DB error during getAll", e);
        }
    }

    @Override
    public synchronized List<Request> getByComplaint(String complaintToMatch) {
        try {
            getByComplaintStatement.clearParameters();
            getByComplaintStatement.setString(1, "%" + complaintToMatch + "%");
            try (ResultSet rs = getByComplaintStatement.executeQuery()) {
                List<Request> result = new ArrayList<>();
                while (rs.next()) {
                    result.add(mapRow(rs));
                }
                return result;
            }
        } catch (SQLException e) {
            throw new RuntimeException("DB error during getByComplaint", e);
        }
    }

    @Override
    public synchronized List<Request> getByStatus(RequestStatus status) {
        try {
            getByStatusStatement.clearParameters();
            getByStatusStatement.setString(1, status.name());
            try (ResultSet rs = getByStatusStatement.executeQuery()) {
                List<Request> result = new ArrayList<>();
                while (rs.next()) {
                    result.add(mapRow(rs));
                }
                return result;
            }
        } catch (SQLException e) {
            throw new RuntimeException("DB error during getByStatus", e);
        }
    }

    @Override
    public synchronized List<Request> getByRequestType(RequestType type) {
        try {
            getByRequestTypeStatement.clearParameters();
            getByRequestTypeStatement.setString(1, type.name());
            try (ResultSet rs = getByRequestTypeStatement.executeQuery()) {
                List<Request> result = new ArrayList<>();
                while (rs.next()) {
                    result.add(mapRow(rs));
                }
                return result;
            }
        } catch (SQLException e) {
            throw new RuntimeException("DB error during getByRequestType", e);
        }
    }

    @Override
    public List<Request> getByComplainingId(long id) {
        try {
            getByComplainingIdStatement.clearParameters();
            getByComplainingIdStatement.setLong(1, id);
            try (ResultSet rs = getByComplainingIdStatement.executeQuery()) {
                List<Request> result = new ArrayList<>();
                while (rs.next()) {
                    result.add(mapRow(rs));
                }
                return result;
            }
        } catch (SQLException e) {
            throw new RuntimeException("DB error during getByComplainingId", e);
        }
    }

    @Override
    public synchronized List<Request> getByAddress(String addressToMatch) {
        try {
            getByAddressStatement.clearParameters();
            getByAddressStatement.setString(1, "%" + addressToMatch + "%");
            try (ResultSet rs = getByAddressStatement.executeQuery()) {
                List<Request> result = new ArrayList<>();
                while (rs.next()) {
                    result.add(mapRow(rs));
                }
                return result;
            }
        } catch (SQLException e) {
            throw new RuntimeException("DB error during getByAddress", e);
        }
    }

    private Request mapRow(ResultSet rs) throws SQLException {
        return Request.builder()
                .id(rs.getLong("id"))
                .complaintText(rs.getString("complaint_text"))
                .status(RequestStatus.valueOf(rs.getString("status")))
                .type(RequestType.valueOf(rs.getString("type")))
                .idComplaining(rs.getLong("id_complaining"))
                .houseAddress(rs.getString("house_address"))
                .build();
    }
}
