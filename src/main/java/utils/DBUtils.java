package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class DBUtils {

    private static final Logger LOGGER = LogManager.getLogger(DBUtils.class.getName());

    private static final String DB_PROPS_PATH = "db/db.properties";
    private static final String DB_URL = "db.URL";

    private static Connection CONNECTION;

    private DBUtils() {
    }

    static {
        Properties props = new Properties();

        InputStream inp = Thread.currentThread().getContextClassLoader().getResourceAsStream(DB_PROPS_PATH);

        if (inp != null) {
            try {
                props.load(inp);

                inp.close();
            } catch (IOException e) {
                LOGGER.error("Failed to read file: " + DB_PROPS_PATH + "\n" + e);
            }
        }
        try {
            CONNECTION = DriverManager.getConnection(props.getProperty(DB_URL));
        } catch (SQLException e) {
            LOGGER.error("Failed to get connection with database.\n" + e);
        }
    }

    public static Connection getConnection() {
        return CONNECTION;
    }
}

