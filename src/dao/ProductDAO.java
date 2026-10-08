
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.Product;
import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Achintha
 */
public class ProductDAO {

    public List<Product> getAllActiveProducts() throws Exception {

        List<Product> products = new ArrayList<>();

        String sql =
                "SELECT product_id, product_code, product_name, "
                + "category_id, unit, selling_price, reorder_level, status "
                + "FROM products "
                + "WHERE status = 'ACTIVE' "
                + "ORDER BY product_name";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery()
        ) {

            while (rs.next()) {

                Product product = new Product();

                product.setProductId(
                        rs.getInt("product_id")
                );

                product.setProductCode(
                        rs.getString("product_code")
                );

                product.setProductName(
                        rs.getString("product_name")
                );

                product.setCategoryId(
                        rs.getInt("category_id")
                );

                product.setUnit(
                        rs.getString("unit")
                );

                product.setSellingPrice(
                        rs.getDouble("selling_price")
                );

                product.setReorderLevel(
                        rs.getDouble("reorder_level")
                );

                product.setStatus(
                        rs.getString("status")
                );

                products.add(product);
            }
        }

        return products;
    }

    public List<Product> getAllProducts() throws Exception {

        List<Product> products = new ArrayList<>();

        String sql =
                "SELECT p.product_id, p.product_code, p.product_name, "
                + "c.category_name, p.category_id, p.unit, "
                + "p.selling_price, p.reorder_level, p.status, "
                + "COALESCE(i.quantity, 0) AS stock_quantity "
                + "FROM products p "
                + "JOIN categories c "
                + "ON p.category_id = c.category_id "
                + "LEFT JOIN inventory i "
                + "ON p.product_id = i.product_id "
                + "WHERE p.status = 'ACTIVE' "
                + "ORDER BY p.product_id DESC";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery()
        ) {

            while (rs.next()) {

                Product product = new Product();

                product.setProductId(
                        rs.getInt("product_id")
                );

                product.setProductCode(
                        rs.getString("product_code")
                );

                product.setProductName(
                        rs.getString("product_name")
                );

                product.setCategoryId(
                        rs.getInt("category_id")
                );

                product.setCategoryName(
                        rs.getString("category_name")
                );

                product.setUnit(
                        rs.getString("unit")
                );

                product.setSellingPrice(
                        rs.getDouble("selling_price")
                );

                product.setReorderLevel(
                        rs.getDouble("reorder_level")
                );

                product.setStockQuantity(
                        rs.getDouble("stock_quantity")
                );

                product.setStatus(
                        rs.getString("status")
                );

                products.add(product);
            }
        }

        return products;
    }

    public void addProduct(model.Product product) throws Exception {

        String productSql =
                "INSERT INTO products "
                + "(product_code, product_name, category_id, unit, "
                + "selling_price, reorder_level, status) "
                + "VALUES (?, ?, ?, ?, ?, ?, 'ACTIVE')";

        String inventorySql =
                "INSERT INTO inventory (product_id, quantity) "
                + "VALUES (?, ?)";

        try (
            java.sql.Connection con =
                    util.DBConnection.getConnection()
        ) {

            con.setAutoCommit(false);

            try (
                java.sql.PreparedStatement productPs =
                        con.prepareStatement(
                                productSql,
                                java.sql.Statement.RETURN_GENERATED_KEYS
                        )
            ) {

                productPs.setString(
                        1,
                        product.getProductCode()
                );

                productPs.setString(
                        2,
                        product.getProductName()
                );

                productPs.setInt(
                        3,
                        product.getCategoryId()
                );

                productPs.setString(
                        4,
                        product.getUnit()
                );

                productPs.setDouble(
                        5,
                        product.getSellingPrice()
                );

                productPs.setDouble(
                        6,
                        product.getReorderLevel()
                );

                productPs.executeUpdate();

                try (
                    java.sql.ResultSet keys =
                            productPs.getGeneratedKeys()
                ) {

                    if (!keys.next()) {
                        throw new Exception(
                                "Unable to get generated product ID."
                        );
                    }

                    int productId =
                            keys.getInt(1);

                    try (
                        java.sql.PreparedStatement inventoryPs =
                                con.prepareStatement(inventorySql)
                    ) {

                        inventoryPs.setInt(
                                1,
                                productId
                        );

                        inventoryPs.setDouble(
                                2,
                                0.0
                        );

                        inventoryPs.executeUpdate();
                    }
                }

                con.commit();

            } catch (Exception e) {

                con.rollback();
                throw e;
            }
        }
    }

    public java.util.List<String> getActiveCategoryNames()
            throws Exception {

        java.util.List<String> categories =
                new java.util.ArrayList<>();

        String sql =
                "SELECT category_name "
                + "FROM categories "
                + "WHERE status = 'ACTIVE' "
                + "ORDER BY category_name";

        try (
            java.sql.Connection con =
                    util.DBConnection.getConnection();

            java.sql.PreparedStatement pst =
                    con.prepareStatement(sql);

            java.sql.ResultSet rs =
                    pst.executeQuery()
        ) {

            while (rs.next()) {

                categories.add(
                        rs.getString("category_name")
                );
            }
        }

        return categories;
    }

    public java.util.List<String> getExistingUnits()
            throws Exception {

        java.util.List<String> units =
                new java.util.ArrayList<>();

        String sql =
                "SELECT DISTINCT unit "
                + "FROM products "
                + "WHERE unit IS NOT NULL "
                + "AND unit <> '' "
                + "ORDER BY unit";

        try (
            java.sql.Connection con =
                    util.DBConnection.getConnection();

            java.sql.PreparedStatement pst =
                    con.prepareStatement(sql);

            java.sql.ResultSet rs =
                    pst.executeQuery()
        ) {

            while (rs.next()) {

                units.add(
                        rs.getString("unit")
                );
            }
        }

        return units;
    }

    public int getCategoryIdByName(String categoryName)
            throws Exception {

        String sql =
                "SELECT category_id "
                + "FROM categories "
                + "WHERE category_name = ? "
                + "AND status = 'ACTIVE'";

        try (
            java.sql.Connection con =
                    util.DBConnection.getConnection();

            java.sql.PreparedStatement pst =
                    con.prepareStatement(sql)
        ) {

            pst.setString(
                    1,
                    categoryName
            );

            try (
                java.sql.ResultSet rs =
                        pst.executeQuery()
            ) {

                if (rs.next()) {

                    return rs.getInt(
                            "category_id"
                    );
                }
            }
        }

        return 0;
    }
    
    public void updateProduct(model.Product product) throws Exception {

        String sql =
                "UPDATE products SET "
                + "product_name = ?, "
                + "category_id = ?, "
                + "unit = ?, "
                + "selling_price = ?, "
                + "reorder_level = ? "
                + "WHERE product_id = ?";

        try (
            java.sql.Connection con =
                    util.DBConnection.getConnection();

            java.sql.PreparedStatement ps =
                    con.prepareStatement(sql)
        ) {

            ps.setString(
                    1,
                    product.getProductName()
            );

            ps.setInt(
                    2,
                    product.getCategoryId()
            );

            ps.setString(
                    3,
                    product.getUnit()
            );

            ps.setDouble(
                    4,
                    product.getSellingPrice()
            );

            ps.setDouble(
                    5,
                    product.getReorderLevel()
            );

            ps.setInt(
                    6,
                    product.getProductId()
            );

            int rowsUpdated = ps.executeUpdate();

            if (rowsUpdated == 0) {
                throw new Exception(
                        "Product was not found. Update failed."
                );
            }
        }
    }
    
    public void deleteProduct(int productId) throws Exception {

        String sql =
                "UPDATE products "
                + "SET status = 'INACTIVE' "
                + "WHERE product_id = ?";

        try (
            java.sql.Connection con =
                    util.DBConnection.getConnection();

            java.sql.PreparedStatement ps =
                    con.prepareStatement(sql)
        ) {

            ps.setInt(1, productId);

            int rowsUpdated = ps.executeUpdate();

            if (rowsUpdated == 0) {
                throw new Exception(
                        "Product was not found. Delete failed."
                );
            }
        }
    }
    
}
