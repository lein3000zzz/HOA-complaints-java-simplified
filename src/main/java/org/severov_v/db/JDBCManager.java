package org.severov_v.db;

import java.sql.Connection;
import java.sql.SQLException;

public interface JDBCManager {
    Connection getConnection();
}
