package de.bwvaachen.botscheduler.database;

import java.sql.*;
import java.util.Properties;
import java.util.logging.Logger;
import java.lang.ClassLoader;


public class dbContext {
    public static void main(String[] args) throws Exception {

        Class.forName("org.h2.Driver");
        Connection conn = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");
        String sqlCreate = "DROP TABLE IF EXISTS Persons;" +
                "CREATE TABLE Persons (" +
                "    PersonID int," +
                "    LastName varchar(255)," +
                "    FirstName varchar(255)," +
                "    Address varchar(255)," +
                "    City varchar(255)" +
                ");";
        String sqlInsert = "INSERT INTO Persons VALUES (2, 'Gabor', 'Tomas', 'Bla', 'Aachen');";
        String sql = "SELECT * FROM Persons;";

        Statement statement = conn.createStatement();
        statement.executeUpdate(sqlCreate);
        statement.executeUpdate(sqlInsert);
        ResultSet resultSet = statement.executeQuery(sql);

        resultSet.next();
        System.out.println(resultSet.getString("PersonID"));

        conn.close();
    }
}
