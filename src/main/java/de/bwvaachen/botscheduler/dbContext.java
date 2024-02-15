package de.bwvaachen.botscheduler;
import java.sql.*;
import java.util.Properties;
import java.util.logging.Logger;

public class dbContext {
    public static void main(String[] args) throws Exception {

            Connection conn = DriverManager.getConnection("jdbc:h2:C/Users/gabor_tom/test", "sa", "");
            //...
            conn.close();
        }
    }
