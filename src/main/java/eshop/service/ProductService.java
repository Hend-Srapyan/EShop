package eshop.service;

import eshop.db.DBConnectionProvider;
import eshop.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductService {

    private final Connection connection = DBConnectionProvider.getInstance().getConnection();
    private final CategoryService categoryService = new CategoryService();

    public void add(Product product) {
        String sql = "INSERT INTO product(name,description,price,qty,category_id) VALUES(?,?,?,?,?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getDescription());
            preparedStatement.setDouble(3, product.getPrice());
            preparedStatement.setInt(4, product.getQty());
            preparedStatement.setInt(5, product.getCategory().getId());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                product.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Product> getAllProducts() {
        String sql = "SELECT * FROM product";
        List<Product> products = new ArrayList<>();

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                products.add(Product.builder()
                        .id(resultSet.getInt("id"))
                        .name(resultSet.getString("name"))
                        .description(resultSet.getString("description"))
                        .price(resultSet.getDouble("price"))
                        .qty(resultSet.getInt("qty"))
                        .category(categoryService.getCategoryById(resultSet.getInt("category_id")))
                        .build());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    public Product getProductById(int id) {
        String sql = "SELECT * FROM product WHERE id = " + id;

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                Product product = Product.builder()
                        .id(resultSet.getInt("id"))
                        .name(resultSet.getString("name"))
                        .description(resultSet.getString("description"))
                        .price(resultSet.getDouble("price"))
                        .qty(resultSet.getInt("qty"))
                        .category(categoryService.getCategoryById(resultSet.getInt("category_id")))
                        .build();
                return product;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void deleteProduct(int id) {
        if (getProductById(id) == null) {
            System.err.println("PRODUCT DOESN'T EXIST");
        }

        String sql = "DELETE FROM product WHERE id = " + id;

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.err.println("PRODUCT DELETED");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editProduct(Product product) {


        String sql = "UPDATE product SET name = ?, description = ?, price = ?, qty = ?, category_id = ? WHERE id = " + product.getId();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getDescription());
            preparedStatement.setDouble(3, product.getPrice());
            preparedStatement.setInt(4, product.getQty());
            preparedStatement.setInt(5, product.getCategory().getId());
            preparedStatement.executeUpdate();
            System.err.println("PRODUCT WAS UPDATE!!!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public double getProductMaxPrice() {
        String sql = "SELECT MAX(price) FROM product";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public double getProductMinPrice() {
        String sql = "SELECT MIN(price) FROM product";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public double getProductAvgPrice() {
        String sql = "SELECT AVG(price) FROM product";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getSumOfProducts() {
        try  {
        String sql = "SELECT SUM(qty) FROM product";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
