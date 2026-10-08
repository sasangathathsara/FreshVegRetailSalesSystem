/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author Achintha
 */
public class DashboardDAO {
    public int getTotalProducts() {

        String sql = "SELECT COUNT(*) FROM products";

        try (
            java.sql.Connection con = util.DBConnection.getConnection();
            java.sql.PreparedStatement ps = con.prepareStatement(sql);
            java.sql.ResultSet rs = ps.executeQuery()
        ) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
    
    public int getTotalCustomers() {

        String sql = "SELECT COUNT(*) FROM customers";

        try (
            java.sql.Connection con = util.DBConnection.getConnection();
            java.sql.PreparedStatement ps = con.prepareStatement(sql);
            java.sql.ResultSet rs = ps.executeQuery()) 
        {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
    
    public int getTotalOrders() {

        String sql = "SELECT COUNT(*) FROM sales";

        try (
            java.sql.Connection con = util.DBConnection.getConnection();
            java.sql.PreparedStatement ps = con.prepareStatement(sql);
            java.sql.ResultSet rs = ps.executeQuery()
        ) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
    
    public double getTotalSales() {

        String sql = "SELECT COALESCE(SUM(net_amount), 0) FROM sales";

        try (
            java.sql.Connection con = util.DBConnection.getConnection();
            java.sql.PreparedStatement ps = con.prepareStatement(sql);
            java.sql.ResultSet rs = ps.executeQuery()
        ) {

            if (rs.next()) {
                return rs.getDouble(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0.0;
    }
    
    public java.util.Map<String, Double> getLast7DaysSales() {

        java.util.Map<String, Double> salesMap =
                new java.util.LinkedHashMap<>();

        String sql =
                "SELECT DATE(sale_date) AS sale_day, " +
                "COALESCE(SUM(net_amount), 0) AS total_sales " +
                "FROM sales " +
                "WHERE sale_date >= CURDATE() - INTERVAL 6 DAY " +
                "GROUP BY DATE(sale_date) " +
                "ORDER BY sale_day";

        try (
            java.sql.Connection con = util.DBConnection.getConnection();
            java.sql.PreparedStatement ps = con.prepareStatement(sql);
            java.sql.ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {
                salesMap.put(
                    rs.getString("sale_day"),
                    rs.getDouble("total_sales")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return salesMap;
    }

}
