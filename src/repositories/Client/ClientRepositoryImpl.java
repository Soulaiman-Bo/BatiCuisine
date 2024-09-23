package repositories.Client;

import Config.DBConnection;
import Domain.Entities.Client;
import Utils.Mappers;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientRepositoryImpl implements ClientRepository {
    DBConnection dbConnection = null;
    Connection connection = null;

    @Override
    public Client save(Client client) {
        String sql = client.getId() == null ?
                "INSERT INTO Clients (name, address, phoneNumber, isProfessional) VALUES (?, ?, ?, ?)" :
                "UPDATE Clients SET name = ?, address = ?, phoneNumber = ?, isProfessional = ? WHERE id = ?";

        try {
            dbConnection = DBConnection.getInstance();
            if (dbConnection != null) {
                connection = dbConnection.getConnection();

                try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                    stmt.setString(1, client.getName());
                    stmt.setString(2, client.getAddress());
                    stmt.setString(3, client.getPhoneNumber());
                    stmt.setBoolean(4, client.getProfessional());

                    if (client.getId() != null) {
                        stmt.setInt(5, client.getId());
                    }

                    int affectedRows = stmt.executeUpdate();

                    if (affectedRows == 0) {
                        throw new SQLException("Creating client failed, no rows affected.");
                    }

                    if (client.getId() == null) {
                        try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                            if (generatedKeys.next()) {
                                client.setId(generatedKeys.getInt(1));
                            } else {
                                throw new SQLException("Creating client failed, no ID obtained.");
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (dbConnection != null) {
                dbConnection.closeConnection();
            }
        }
        return client;
    }

    @Override
    public Optional<Client> findById(Integer id) {
        String sql = "SELECT * FROM Clients WHERE id = ?";

        try {
            dbConnection = DBConnection.getInstance();
            if (dbConnection != null) {
                connection = dbConnection.getConnection();

                try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                    stmt.setInt(1, id);
                    ResultSet rs = stmt.executeQuery();
                    if (rs.next()) {
                        return Optional.of(Mappers.mapResultSetToClient(rs));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (dbConnection != null) {
                dbConnection.closeConnection();
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Client> findAll() {
        List<Client> clientList = new ArrayList<>();
        String sql = "SELECT * FROM Clients";


        try {
            dbConnection = DBConnection.getInstance();
            if (dbConnection != null) {
                connection = dbConnection.getConnection();

                try (Statement stmt = connection.createStatement();
                     ResultSet rs = stmt.executeQuery(sql)) {
                    while (rs.next()) {
                        clientList.add(Mappers.mapResultSetToClient(rs));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (dbConnection != null) {
                dbConnection.closeConnection();
            }
        }
        return clientList;
    }

    @Override
    public boolean deleteById(Integer id) {
        String sql = "DELETE FROM Clients WHERE id = ?";
        boolean isDeleted = false;

        try {
            dbConnection = DBConnection.getInstance();
            if (dbConnection != null) {
                connection = dbConnection.getConnection();

                try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                    stmt.setInt(1, id);
                    int affectedRows = stmt.executeUpdate();
                    if (affectedRows > 0) {
                        isDeleted = true;
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (dbConnection != null) {
                dbConnection.closeConnection();
            }
        }

        return isDeleted;
    }

    @Override
    public List<Client> findByProfessional(Boolean isProfessional) {
        List<Client> clientList = new ArrayList<>();
        String sql = "SELECT * FROM Clients WHERE isProfessional = ?";

        try {
            dbConnection = DBConnection.getInstance();
            if (dbConnection != null) {
                connection = dbConnection.getConnection();

                try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                    stmt.setBoolean(1, isProfessional);
                    ResultSet rs = stmt.executeQuery();
                    while (rs.next()) {
                        clientList.add(Mappers.mapResultSetToClient(rs));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (dbConnection != null) {
                dbConnection.closeConnection();
            }
        }
        return clientList;
    }

    @Override
    public Client update(Client client) {
        if (client.getId() == null) {
            throw new IllegalArgumentException("Cannot update a Client without an ID");
        }

        StringBuilder sql = new StringBuilder("UPDATE clients SET ");
        List<Object> params = new ArrayList<>();
        boolean needComma = false;

        if (client.getName() != null) {
            sql.append("name = ?");
            params.add(client.getName());
            needComma = true;
        }
        if (client.getAddress() != null) {
            if (needComma) sql.append(", ");
            sql.append("address = ?");
            params.add(client.getAddress());
            needComma = true;
        }
        if (client.getPhoneNumber() != null) {
            if (needComma) sql.append(", ");
            sql.append("phonenumber = ?");
            params.add(client.getPhoneNumber());
            needComma = true;
        }
        if (client.getProfessional() != null) {
            if (needComma) sql.append(", ");
            sql.append("isprofessional = ?");
            params.add(client.getProfessional());
        }

        sql.append(" WHERE id = ?");
        params.add(client.getId());

        sql.append(" RETURNING *");

        DBConnection dbConnection = null;
        Connection connection = null;

        try {
            dbConnection = DBConnection.getInstance();
            if (dbConnection != null) {
                connection = dbConnection.getConnection();

                try (PreparedStatement stmt = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS)) {
                    for (int i = 0; i < params.size(); i++) {
                        stmt.setObject(i + 1, params.get(i));
                    }

                    int affectedRows = stmt.executeUpdate();

                    if (affectedRows == 0) {
                        throw new SQLException("Updating client failed, no rows affected.");
                    }

                    try (ResultSet rs = stmt.getGeneratedKeys()) {
                        if (rs.next()) {
                            client = Mappers.mapResultSetToClient(rs);
                        } else {
                            throw new SQLException("Updating client failed, no rows returned.");
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (dbConnection != null) {
                dbConnection.closeConnection();
            }
        }

        return client;
    }


}