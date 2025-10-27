package org.severov_v.db;

import java.sql.Connection;

public interface JDBCManager {
    Connection getConnection();
}
