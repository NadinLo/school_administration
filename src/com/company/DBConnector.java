package com.company;

import java.sql.*;

public class DBConnector {
    private Connection conn = null;
    private Statement stmt = null;
    private String url;

    public DBConnector(String url) {
        this.url = url;
    }

    private void buildConnection () {
        try{
            conn = DriverManager.getConnection(url);
            stmt = conn.createStatement();
        } catch (SQLException ex){
            System.out.println("couldn't connect");
            ex.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            if (conn != null){
                conn.close();
            }
            if (stmt != null){
                stmt.close();
            }
        } catch (SQLException ex) {
            System.out.println("could not close connection");
            ex.printStackTrace();
        }
    }

    public ResultSet callUp (String sql){
        buildConnection();
        try {
            return stmt.executeQuery(sql);
        } catch (SQLException ex){
            System.out.println("could not call the required data");
            ex.printStackTrace();
            closeConnection();
        }
        return null;
    }
}
