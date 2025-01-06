package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import classes.Database;  // Pastikan mengimpor kelas Database

public class NilaiModel {
    private Connection connection;

    // Konstruktor untuk menggunakan koneksi dari kelas Database
    public NilaiModel() {
        Database db = new Database(); // Membuat objek Database untuk mendapatkan koneksi
        this.connection = db.getConn(); // Mendapatkan koneksi dari Database
    }

    public List<Nilai> findByMahasiswaId(int mahasiswaId) {
        List<Nilai> nilaiList = new ArrayList<>();
        String query = "SELECT * FROM nilai WHERE mahasiswa_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, mahasiswaId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Nilai nilai = new Nilai(
                        resultSet.getInt("id"),
                        resultSet.getInt("mahasiswa_id"),
                        resultSet.getString("mata_kuliah"),
                        resultSet.getInt("semester"),
                        resultSet.getDouble("nilai")
                    );
                    nilaiList.add(nilai);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nilaiList;
    }

    public boolean createNilai(Nilai nilai) {
        String query = "INSERT INTO nilai (mahasiswa_id, mata_kuliah, semester, nilai) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, nilai.getMahasiswaId());
            statement.setString(2, nilai.getMataKuliah());
            statement.setInt(3, nilai.getSemester());
            statement.setDouble(4, nilai.getNilai());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateNilai(Nilai nilai) {
        String query = "UPDATE nilai SET mata_kuliah = ?, semester = ?, nilai = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, nilai.getMataKuliah());
            statement.setInt(2, nilai.getSemester());
            statement.setDouble(3, nilai.getNilai());
            statement.setInt(4, nilai.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteNilai(int id) {
        String query = "DELETE FROM nilai WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
