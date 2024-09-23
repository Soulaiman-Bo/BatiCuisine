package repositories.Projet;

import Config.DBConnection;
import Domain.Entities.Projet;
import Utils.ConsolePrinter;
import Utils.Mappers;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

    public class ProjetRepositoryImpl implements ProjetRepository {
        DBConnection dbConnection = null;
        Connection connection = null;

        @Override
        public Projet save(Projet projet) {
            String sql = projet.getId() == null ?
                    "INSERT INTO Projets (projectName, profit, totalCost, status, client_id, discount) VALUES (?, ?, ?, ?::projectstatus, ?, ?)" :
                    "UPDATE Projets SET projectName = ?, profit = ?, totalCost = ?, status = ?, client_id = ? WHERE id = ?";

            try {
                dbConnection = DBConnection.getInstance();
                if (dbConnection != null) {
                    connection = dbConnection.getConnection();

                    try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                        stmt.setString(1, projet.getProjectName());
                        stmt.setDouble(2, projet.getProfit());
                        stmt.setDouble(3, projet.getTotalCost());
                        stmt.setString(4, projet.getProjectStatus().name());
                        stmt.setInt(5, projet.getClient().getId());
                        stmt.setDouble(6, projet.getDiscount());

                        if (projet.getId() != null) {
                            stmt.setInt(6, projet.getId());
                        }

                        int affectedRows = stmt.executeUpdate();

                        if (affectedRows == 0) {
                            throw new SQLException("Creating projet failed, no rows affected.");
                        }

                        if (projet.getId() == null) {
                            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                                if (generatedKeys.next()) {
                                    projet.setId(generatedKeys.getInt(1));
                                } else {
                                    throw new SQLException("Creating projet failed, no ID obtained.");
                                }
                            }
                        }
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return projet;
        }

        @Override
        public Optional<Projet> findById(Integer id) {
            String sql = "SELECT * FROM Projets WHERE id = ?";
            try {
                dbConnection = DBConnection.getInstance();
                if (dbConnection != null) {
                    connection = dbConnection.getConnection();

                    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                        stmt.setInt(1, id);
                        ResultSet rs = stmt.executeQuery();
                        if (rs.next()) {
                            return Optional.of(Mappers.mapResultSetToProjet(rs));
                        }
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return Optional.empty();
        }

        @Override
        public List<Projet> findAll() {
            List<Projet> projetList = new ArrayList<>();
            String sql = "SELECT * FROM Projets";

            try {
                dbConnection = DBConnection.getInstance();
                if (dbConnection != null) {
                    connection = dbConnection.getConnection();

                    try (Statement stmt = connection.createStatement();
                         ResultSet rs = stmt.executeQuery(sql)) {
                        while (rs.next()) {
                            projetList.add(Mappers.mapResultSetToProjet(rs));
                        }
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return projetList;
        }

        @Override
        public boolean deleteById(Integer id) {
            String sql = "DELETE FROM Projets WHERE id = ?";
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
            }
            return isDeleted;
        }

        @Override
        public Projet update(Projet projet) {

            if (projet.getId() == null) {
                throw new IllegalArgumentException("Cannot update a Project without an ID");
            }

            StringBuilder sql = new StringBuilder("UPDATE projets SET ");
            List<Object> params = new ArrayList<>();
            boolean needComma = false;

            if (projet.getProjectName() != null) {
                sql.append("projectname = ?");
                params.add(projet.getProjectName());
                needComma = true;
            }
            if (projet.getProfit() != null) {
                if (needComma) sql.append(", ");
                sql.append("profit = ?");
                params.add(projet.getProfit());
                needComma = true;
            }
            if (projet.getTotalCost() != null) {
                if (needComma) sql.append(", ");
                sql.append("totalcost = ?");
                params.add(projet.getTotalCost());
                needComma = true;
            }
            if (projet.getDiscount() != null) {
                if (needComma) sql.append(", ");
                sql.append("discount = ?");
                params.add(projet.getDiscount());
                needComma = true;
            }

            if (projet.getProjectStatus() != null) {
                if (needComma) sql.append(", ");
                sql.append("status = ?::projectstatus");
                params.add(projet.getProjectStatus().name());
                needComma = true;
            }

            if (projet.getClient() != null && projet.getClient().getId() != null) {
                if (needComma) sql.append(", ");
                sql.append("client_id = ?");
                params.add(projet.getClient().getId());
            }

            sql.append(" WHERE id = ?");
            params.add(projet.getId());

            sql.append(" RETURNING *");


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
                            throw new SQLException("Updating project failed, no rows affected.");
                        }

                        try (ResultSet rs = stmt.getGeneratedKeys()) {
                            if (rs.next()) {
                                projet = Mappers.mapResultSetToProjet(rs);
                            } else {
                                throw new SQLException("Updating project failed, no rows returned.");
                            }
                        }
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            return projet;
        }

    }
