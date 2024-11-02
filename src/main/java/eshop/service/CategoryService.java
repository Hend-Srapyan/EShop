package eshop.service;

import eshop.db.DBConnectionProvider;
import eshop.model.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryService {
    private final Connection connection = DBConnectionProvider.getInstance().getConnection();

    public void add(Category category) {
        String sql = "INSERT INTO category(name) VALUES(?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, category.getName());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                category.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Category> getAllCategories() {
        String sql = "SELECT * FROM category";
        List<Category> categories = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                categories.add(Category.builder()
                        .id(resultSet.getInt("id"))
                        .name(resultSet.getString("name"))
                        .build());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    public Category getCategoryById(int id) {
        String sql = "SELECT * FROM category WHERE id = " + id;
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                Category category = Category.builder()
                        .id(resultSet.getInt("id"))
                        .name(resultSet.getString("name"))
                        .build();
                return category;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteCategory(int id) {
        if (getCategoryById(id) == null) {
            System.err.println("CATEGORY DOESN'T EXIST");
        }
        String sql = "DELETE FROM category WHERE id = " + id;
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.err.println("CATEGORY DELETED");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editCategory(Category category) {
        String sql = "UPDATE category SET name = ? WHERE id = " + category.getId();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, category.getName());
            preparedStatement.executeUpdate();
            System.err.println("CATEGORY WAS UPDATE!!!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
