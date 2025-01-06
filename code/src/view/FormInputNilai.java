package view;

import model.Nilai;
import model.NilaiModel;

import javax.swing.*;
import java.awt.*;

public class FormInputNilai extends JFrame {
    private JTextField txtMahasiswaId;
    private JTextField txtNamaMahasiswa;
    private JTextField txtMataKuliah;
    private JTextField txtSemester;
    private JTextField txtNilai;
    private JButton btnSave, btnCancel;
    private final NilaiModel nilaiModel;

    // Field tambahan untuk membedakan Create dan Update
    private Integer nilaiId; // null untuk Create, non-null untuk Update

    public FormInputNilai(NilaiModel nilaiModel) {
        this.nilaiModel = nilaiModel;

        setTitle("Input Nilai Mahasiswa");
        setSize(500, 350);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel Utama
        JPanel mainPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Input Fields
        mainPanel.add(new JLabel("Mahasiswa ID:"));
        txtMahasiswaId = new JTextField();
        txtMahasiswaId.setEditable(false); // Tidak dapat diubah
        mainPanel.add(txtMahasiswaId);

        mainPanel.add(new JLabel("Nama Mahasiswa:"));
        txtNamaMahasiswa = new JTextField();
        txtNamaMahasiswa.setEditable(false); // Tidak dapat diubah
        mainPanel.add(txtNamaMahasiswa);

        mainPanel.add(new JLabel("Mata Kuliah:"));
        txtMataKuliah = new JTextField();
        mainPanel.add(txtMataKuliah);

        mainPanel.add(new JLabel("Semester:"));
        txtSemester = new JTextField();
        mainPanel.add(txtSemester);

        mainPanel.add(new JLabel("Nilai:"));
        txtNilai = new JTextField();
        mainPanel.add(txtNilai);

        // Tombol Simpan dan Batal
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnSave = new JButton("Simpan Nilai");
        btnSave.addActionListener(e -> saveNilai());
        btnCancel = new JButton("Batal");
        btnCancel.addActionListener(e -> dispose());
        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);

        // Tambahkan ke Frame
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void saveNilai() {
        try {
            // Validasi Mahasiswa ID
            String mahasiswaIdText = txtMahasiswaId.getText();
            if (mahasiswaIdText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Mahasiswa ID tidak boleh kosong.", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int mahasiswaId = Integer.parseInt(mahasiswaIdText);

            // Validasi Mata Kuliah
            String mataKuliah = txtMataKuliah.getText();
            if (mataKuliah.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Mata Kuliah tidak boleh kosong.", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Validasi Semester
            int semester;
            try {
                semester = Integer.parseInt(txtSemester.getText());
                if (semester <= 0) {
                    JOptionPane.showMessageDialog(this, "Semester harus lebih besar dari 0.", "Peringatan", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Semester harus berupa angka.", "Kesalahan", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validasi Nilai
            double nilai;
            try {
                nilai = Double.parseDouble(txtNilai.getText());
                if (nilai < 0 || nilai > 100) {
                    JOptionPane.showMessageDialog(this, "Nilai harus antara 0 dan 100.", "Peringatan", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Nilai harus berupa angka.", "Kesalahan", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Logika Simpan (Create atau Update)
            if (nilaiId == null) {
                // Create
                nilaiModel.createNilai(new Nilai(0, mahasiswaId, mataKuliah, semester, nilai));
                JOptionPane.showMessageDialog(this, "Nilai berhasil ditambahkan.");
            } else {
                // Update
                nilaiModel.updateNilai(new Nilai(nilaiId, mahasiswaId, mataKuliah, semester, nilai));
                JOptionPane.showMessageDialog(this, "Nilai berhasil diperbarui.");
            }
            dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Input tidak valid. Periksa lagi.", "Kesalahan", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Setter untuk mode Create atau Update
    public void setIdNilai(Integer nilaiId) {
        this.nilaiId = nilaiId; // Nilai ID akan digunakan untuk Update
    }

    // Setter Fields
    public void setMahasiswaId(String mahasiswaId) {
        txtMahasiswaId.setText(mahasiswaId);
    }

    public void setMahasiswaNama(String nama) {
        txtNamaMahasiswa.setText(nama);
    }

    public void setMataKuliah(String mataKuliah) {
        txtMataKuliah.setText(mataKuliah);
    }

    public void setSemester(String semester) {
        txtSemester.setText(semester);
    }

    public void setNilai(String nilai) {
        txtNilai.setText(nilai);
    }
}
