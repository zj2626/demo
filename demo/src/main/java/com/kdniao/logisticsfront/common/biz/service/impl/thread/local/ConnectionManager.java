package com.kdniao.logisticsfront.common.biz.service.impl.thread.local;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    private Connection connect = null;

    public Connection openConnection() throws SQLException {
        if (connect == null) {
            connect = DriverManager.getConnection("");
        }
        return connect;
    }

    public void closeConnection() throws SQLException {
        if (connect != null)
            connect.close();
    }

}
