package Utils;

import Config.DBConnection;
import Config.DIBootstrap;
import Domain.Entities.Client;

import java.sql.*;

public class DatabaseSeeder {
    DBConnection dbConnection = null;
    Connection connection = null;

    public void dropAndCreateTables() {
        try {
            dbConnection = DBConnection.getInstance();
            if (dbConnection != null) {
                connection = dbConnection.getConnection();

                try (Statement stmt = connection.createStatement()) {
                    System.out.println("Dropping tables...");
                    stmt.executeUpdate("DROP TABLE IF EXISTS devis CASCADE;");
                    stmt.executeUpdate("DROP TABLE IF EXISTS maindœuvre CASCADE;");
                    stmt.executeUpdate("DROP TABLE IF EXISTS materiaux CASCADE;");
                    stmt.executeUpdate("DROP TABLE IF EXISTS composants CASCADE;");
                    stmt.executeUpdate("DROP TABLE IF EXISTS projets CASCADE;");
                    stmt.executeUpdate("DROP TABLE IF EXISTS clients CASCADE;");
                    stmt.executeUpdate("DROP TYPE IF EXISTS projectstatus;");
                    stmt.executeUpdate("DROP TYPE IF EXISTS typeComposant;");

                    // Recreate the projectstatus enum
                    System.out.println("Creating Tables ...");
                    stmt.executeUpdate("CREATE TYPE projectstatus AS ENUM ('PENDING', 'INPROGRESS', 'FINISHED', 'CANCELLED');");

                    System.out.println("Creating Client Table ...");
                    stmt.executeUpdate("""
                                        CREATE TABLE clients (
                                            id SERIAL PRIMARY KEY,
                                            name VARCHAR(255) NOT NULL,
                                            address VARCHAR(255),
                                            phonenumber VARCHAR(20),
                                            isprofessional BOOLEAN NOT NULL
                                        );
                            """);


                    System.out.println("Creating Projets Table ...");
                    stmt.executeUpdate("""
                                CREATE TABLE projets (
                                    id SERIAL PRIMARY KEY,
                                    projectname VARCHAR(255) NOT NULL,
                                    profit DOUBLE PRECISION,
                                    totalcost DOUBLE PRECISION,
                                    status projectstatus,
                                    client_id INTEGER REFERENCES clients ON DELETE CASCADE,
                                    discount DOUBLE PRECISION
                                );
                            """);

                    stmt.executeUpdate("CREATE TYPE typeComposant AS ENUM ( 'MATERIEL', 'MAINDOUVRE');");

                    System.out.println("Creating Composants Table ...");
                    stmt.executeUpdate("""
                                CREATE TABLE composants (
                                    id SERIAL PRIMARY KEY,
                                    name VARCHAR(255) NOT NULL,
                                    taxrate DOUBLE PRECISION,
                                    composantType typeComposant
                                );
                            """);

                    // Recreate the materiaux table
                    System.out.println("Creating Materiaux Table ...");
                    stmt.executeUpdate("""
                                CREATE TABLE materiaux (
                                    id SERIAL PRIMARY KEY,
                                    unitcost DOUBLE PRECISION,
                                    quantity DOUBLE PRECISION,
                                    transportcost DOUBLE PRECISION,
                                    qualitycoefficient DOUBLE PRECISION,
                                    project_id INTEGER REFERENCES projets ON UPDATE CASCADE ON DELETE CASCADE
                                ) INHERITS (composants);
                            """);


                    System.out.println("Creating Maindœuvre Table ...");
                    stmt.executeUpdate("""
                                CREATE TABLE maindœuvre (
                                    id SERIAL PRIMARY KEY,
                                    hourlyrate DOUBLE PRECISION,
                                    workhourscount DOUBLE PRECISION,
                                    productivityrate DOUBLE PRECISION,
                                    project_id INTEGER NOT NULL REFERENCES projets ON UPDATE CASCADE ON DELETE CASCADE
                                ) INHERITS (composants);
                            """);

                    System.out.println("Creating Devis Table ...");
                    stmt.executeUpdate("""
                                CREATE TABLE devis (
                                    id SERIAL PRIMARY KEY,
                                    estimatedprice DOUBLE PRECISION,
                                    issuedate DATE,
                                    validitydate DATE,
                                    accepted BOOLEAN,
                                    project_id INTEGER REFERENCES projets ON DELETE CASCADE
                                );
                            """);

                    System.out.println("Seeding DB with Data ....");
                    seedDatabase();

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

    public void seedDatabase() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            // Seed data into clients table
            stmt.executeUpdate("""
                        INSERT INTO clients (name, address, phonenumber, isprofessional)
                        VALUES ('Soulaiman', 'Massira 1 Marrakech', '555-1234', true),
                               ('Nawal', 'Massira 2 Marrakech', '555-5678', false),
                               ('Hassan', 'Massira 3 Marrakech', '555-5678', false);
                    """);

            // Seed data into projets table
            stmt.executeUpdate("""
                        INSERT INTO projets (projectname, profit, totalcost, status, client_id, discount)
                        VALUES  ('Planet Fitness', 30.0, 25062.85, 'PENDING', 1, 10.0),
                                ('Restaurant', 30.0, 38337.8250, 'PENDING', 1, 10.0),
                                ('School', 30.0, 9426.260, 'PENDING', 1, 10.0),
                                ('Hotel', 30.0, 14029, 'PENDING', 1, 10.0),
                                ('SuperMarket', 30.0, 4899.6, 'PENDING', 2, 10.0),
                                ('Car wash', 30.0, 15413.5, 'PENDING', 2, 10.0);
                    """);

            stmt.executeUpdate("""
                        INSERT INTO materiaux (name, taxrate, composantType, unitcost, quantity, transportcost, qualitycoefficient, project_id)
                        VALUES   ('Cement', 18.0,  'MATERIEL', 50.0, 100.0, 200.0, 1.0, 1),
                                 ('Steel Rebars', 20.0,  'MATERIEL', 75.0, 150.0, 300.0, 1.2, 2),
                                 ('Bricks', 15.0,  'MATERIEL', 60.0, 120.0, 250.0, 1.1, 1),
                                 ('Gravel', 10.0,  'MATERIEL', 40.0, 200.0, 180.0, 0.9, 2),
                                 ('Sand', 8.0,  'MATERIEL', 30.0, 180.0, 160.0, 0.95, 3),
                                 ('Concrete Mix', 22.0,  'MATERIEL', 85.0, 90.0, 350.0, 1.3, 3),
                                 ('Wood Planks', 12.0,  'MATERIEL', 55.0, 130.0, 220.0, 1.05, 4),
                                 ('White Paint', 10.0,  'MATERIEL', 20.0, 50.0, 100.0, 0.98, 5),
                                 ('Primer', 15.0,  'MATERIEL', 25.0, 30.0, 90.0, 1.0, 4),
                                 ('Exterior Paint', 18.0,  'MATERIEL', 45.0, 70.0, 150.0, 1.1, 3),
                                 ('PVC Pipes', 5.0,  'MATERIEL', 10.0, 200.0, 50.0, 0.92, 1),
                                 ('Faucets', 20.0,  'MATERIEL', 60.0, 40.0, 75.0, 1.2, 2),
                                 ('Water Heater', 25.0,  'MATERIEL', 150.0, 10.0, 300.0, 1.3, 5),
                                 ('Copper Wires', 10.0,  'MATERIEL', 30.0, 300.0, 100.0, 1.0, 6),
                                 ('Light Bulbs', 5.0,  'MATERIEL', 5.0, 100.0, 20.0, 1.05, 3),
                                 ('Circuit Breaker', 15.0,  'MATERIEL', 100.0, 15.0, 200.0, 1.25, 6);
                    """);

            // Seed data into maindœuvre table
            stmt.executeUpdate("""
                        INSERT INTO maindœuvre (name, taxrate, composantType, hourlyrate, workhourscount, productivityrate, project_id)
                        VALUES  ('Mason', 18.0, 'MAINDOUVRE', 20.0, 40.0, 1.0, 1),
                                ('Electrician', 20.0, 'MAINDOUVRE', 25.0, 35.0, 1.1, 2),
                                ('Plumber', 15.0, 'MAINDOUVRE', 22.0, 30.0, 1.05, 3),
                                ('Painter', 12.0, 'MAINDOUVRE', 18.0, 25.0, 0.95, 4),
                                ('Carpenter', 18.0, 'MAINDOUVRE', 23.0, 45.0, 1.0, 4),
                                ('Welder', 22.0, 'MAINDOUVRE', 30.0, 20.0, 1.2, 2),
                                ('Roofer', 19.0, 'MAINDOUVRE', 27.0, 50.0, 1.15, 1),
                                ('General Laborer', 10.0,'MAINDOUVRE', 15.0, 60.0, 0.9, 2);
                    """);

        }
    }


}
