package Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static DBConnection instance;
    private final Connection connection;

    private static final String URL = "jdbc:postgresql://localhost:5432/javapostgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";
    
    private DBConnection() {
        Connection tempConnection = null;
        try {
            tempConnection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.connection = tempConnection;
    }
    
    public static DBConnection getInstance() throws SQLException {
        if (instance == null) {
            instance = new DBConnection();
        }else if (instance.getConnection().isClosed()) {
            instance = new DBConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                    System.out.println("Connection closed.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void startTransaction() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.setAutoCommit(false);
        } else {
            throw new SQLException("Connection is not open. Cannot start transaction.");
        }
    }

    public void commitTransaction() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.commit();
        } else {
            throw new SQLException("Connection is not open. Cannot commit transaction.");
        }
    }

    public void rollbackTransaction() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.rollback();
        } else {
            throw new SQLException("Connection is not open. Cannot rollback transaction.");
        }
    }

}
