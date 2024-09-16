package repositories.Devis;


import Config.DBConnection;
import Entities.Devis;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DevisRepositoryImpl implements DevisRepository {
    private DBConnection dbConnection;
    private Connection connection = null;

    @Override
    public Devis save(Devis devis) {
        String sql = devis.getId() == null ? "INSERT INTO Devis (estimatedPrice, issueDate, validityDate, accepted, project_id) VALUES (?, ?, ?, ?, ?)" : "UPDATE Devis SET estimatedPrice = ?, issueDate = ?, validityDate = ?, accepted = ?, project_id = ? WHERE id = ?";

        try {
            dbConnection = DBConnection.getInstance();
            if (dbConnection != null) {
                connection = dbConnection.getConnection();

                try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                    stmt.setDouble(1, devis.getEstimatedPrice());
                    stmt.setDate(2, Date.valueOf(devis.getIssueDate()));
                    stmt.setDate(3, Date.valueOf(devis.getValidityDate()));
                    stmt.setBoolean(4, devis.getAccepted());
                    stmt.setInt(5, devis.getProject_id());

                    if (devis.getId() != null) {
                        stmt.setInt(6, devis.getId());
                    }

                    int affectedRows = stmt.executeUpdate();

                    if (affectedRows == 0) {
                        throw new SQLException("Creating devis failed, no rows affected.");
                    }

                    if (devis.getId() == null) {
                        try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                            if (generatedKeys.next()) {
                                devis.setId(generatedKeys.getInt(1));
                            } else {
                                throw new SQLException("Creating devis failed, no ID obtained.");
                            }
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (dbConnection != null) {
                dbConnection.closeConnection();
            }
        }


        return devis;
    }

    @Override
    public Optional<Devis> findById(Integer id) {
        String sql = "SELECT * FROM Devis WHERE id = ?";

        try {
            dbConnection = DBConnection.getInstance();
            if (dbConnection != null) {
                connection = dbConnection.getConnection();
                try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                    stmt.setInt(1, id);
                    ResultSet rs = stmt.executeQuery();
                    if (rs.next()) {
                        return Optional.of(mapResultSetToDevis(rs));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
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
    public List<Devis> findAll() {
        List<Devis> devisList = new ArrayList<>();
        String sql = "SELECT * FROM Devis";
        try {
            dbConnection = DBConnection.getInstance();
            if (dbConnection != null) {
                connection = dbConnection.getConnection();

                try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
                    while (rs.next()) {
                        devisList.add(mapResultSetToDevis(rs));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (dbConnection != null) {
                dbConnection.closeConnection();
            }
        }


        return devisList;
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM Devis WHERE id = ?";
        try {
            dbConnection = DBConnection.getInstance();
            if (dbConnection != null) {
                connection = dbConnection.getConnection();

                try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                    pstmt.setInt(1, id);
                    pstmt.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (dbConnection != null) {
                dbConnection.closeConnection();
            }
        }

    }

    @Override
    public List<Devis> findByProjectId(Integer projectId) {
        List<Devis> devisList = new ArrayList<>();
        String sql = "SELECT * FROM Devis WHERE project_id = ?";

        try {
            dbConnection = DBConnection.getInstance();
            if (dbConnection != null) {
                connection = dbConnection.getConnection();

                try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                    stmt.setInt(1, projectId);
                    ResultSet rs = stmt.executeQuery();
                    while (rs.next()) {
                        devisList.add(mapResultSetToDevis(rs));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (dbConnection != null) {
                dbConnection.closeConnection();
            }
        }


        return devisList;
    }

    private Devis mapResultSetToDevis(ResultSet rs) throws SQLException {
        return new Devis(
                rs.getInt("id"),
                rs.getDouble("estimatedPrice"),
                rs.getDate("issueDate").toLocalDate(),
                rs.getDate("validityDate").toLocalDate(),
                rs.getBoolean("accepted"),
                rs.getInt("project_id"));
    }
}