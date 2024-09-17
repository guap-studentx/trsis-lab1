package db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBManager {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/equipment_db";
    private static final String DB_USER = "admin";
    private static final String DB_PASSWORD = "1234";

    // Конструктор
    public DBManager() {
        try {
            // Проверяем наличие драйвера
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Метод для редактирования устройства
    public boolean updateDevice(int id, String name, String type, String ipAddress, String location) {
        String query = "UPDATE devices SET name = ?, type = ?, ip_address = ?, location = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, name);
            stmt.setString(2, type);
            stmt.setString(3, ipAddress);
            stmt.setString(4, location);
            stmt.setInt(5, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Метод для добавления устройства
    public boolean addDevice(String name, String type, String ipAddress, String location) {
        String query = "INSERT INTO devices (name, type, ip_address, location) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, name);
            stmt.setString(2, type);
            stmt.setString(3, ipAddress);
            stmt.setString(4, location);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // Метод для удаления устройства по ID
    public boolean deleteDevice(int id) {
        String query = "DELETE FROM devices WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // Метод для проверки существования устройства по ID
    public boolean deviceExistsById(int id) {
        String query = "SELECT COUNT(*) FROM devices WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Метод для получения всех устройств
    public List<DeviceObject> getAllDevices() {
        List<DeviceObject> devices = new ArrayList<>();
        String query = "SELECT * FROM devices";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                /*String device = "ID: " + rs.getInt("id") + ", Название: " + rs.getString("name") +
                        ", Тип: " + rs.getString("type") + ", IP: " + rs.getString("ip_address") +
                        ", Местоположение: " + rs.getString("location");*/
                DeviceObject device = new DeviceObject(rs.getInt("id"), rs.getString("name"),
                        rs.getString("type"), rs.getString("ip_address"), rs.getString("location"));
                devices.add(device);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return devices;
    }
}
