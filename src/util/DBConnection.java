/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Achintha
 */
public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/fresh_retail_db";

    private static final String USER = "root";

    private static final String PASSWORD = "";

    public static Connection getConnection() throws Exception {

        return DriverManager.getConnection(
                URL,
                USER,
                PASSWORD
        );
    }    
}
