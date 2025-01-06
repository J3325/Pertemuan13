package controller;

import model.Nilai;
import model.NilaiModel;
import view.FormInputNilai;
import view.FormMahasiswa;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class NilaiController {
    private final NilaiModel nilaiModel;
    private final FormMahasiswa view;

    public NilaiController(FormMahasiswa view) {
        this.view = view;
        this.nilaiModel = new NilaiModel();

        // Tombol Grade untuk menampilkan form nilai
        view.btnViewNilai.addActionListener(e -> showNilai());
    }

    private void showNilai() {
        int selectedRow = view.tblMahasiswa.getSelectedRow();
        if (selectedRow != -1) {
            int mahasiswaId = Integer.parseInt(view.tblMahasiswa.getModel().getValueAt(selectedRow, 0).toString());
            String namaMahasiswa = view.tblMahasiswa.getModel().getValueAt(selectedRow, 2).toString();

            JFrame nilaiFrame = new JFrame("Nilai Mahasiswa: " + namaMahasiswa);
            nilaiFrame.setSize(600, 400);
            nilaiFrame.setLocationRelativeTo(view);

            DefaultTableModel tableModel = new DefaultTableModel(new String[]{"ID", "Mata Kuliah", "Semester", "Nilai"}, 0);
            JTable nilaiTable = new JTable(tableModel);
            JScrollPane scrollPane = new JScrollPane(nilaiTable);

            JPanel buttonPanel = new JPanel();
            JButton btnTambah = new JButton("Create");
            JButton btnEdit = new JButton("Update");
            JButton btnHapus = new JButton("Delete");

            // Memuat data ke tabel
            List<Nilai> nilaiList = nilaiModel.findByMahasiswaId(mahasiswaId);
            for (Nilai nilai : nilaiList) {
                tableModel.addRow(new Object[]{nilai.getId(), nilai.getMataKuliah(), nilai.getSemester(), nilai.getNilai()});
            }

            // Logika tombol Create
            btnTambah.addActionListener(e -> {
                FormInputNilai formInputNilai = new FormInputNilai(nilaiModel);
                formInputNilai.setMahasiswaId(String.valueOf(mahasiswaId));
                formInputNilai.setMahasiswaNama(namaMahasiswa);
                formInputNilai.setVisible(true);

                formInputNilai.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                        reloadTableData(tableModel, mahasiswaId);
                    }
                });
            });

            // Logika tombol Update
            btnEdit.addActionListener(e -> {
                int selectedRowInTable = nilaiTable.getSelectedRow();
                if (selectedRowInTable != -1) {
                    int nilaiId = Integer.parseInt(nilaiTable.getValueAt(selectedRowInTable, 0).toString());
                    String mataKuliah = nilaiTable.getValueAt(selectedRowInTable, 1).toString();
                    int semester = Integer.parseInt(nilaiTable.getValueAt(selectedRowInTable, 2).toString());
                    double nilai = Double.parseDouble(nilaiTable.getValueAt(selectedRowInTable, 3).toString());

                    FormInputNilai formInputNilai = new FormInputNilai(nilaiModel);
                    formInputNilai.setMahasiswaId(String.valueOf(mahasiswaId));
                    formInputNilai.setMahasiswaNama(namaMahasiswa);
                    formInputNilai.setIdNilai(nilaiId); // Set ID untuk update
                    formInputNilai.setMataKuliah(mataKuliah);
                    formInputNilai.setSemester(String.valueOf(semester));
                    formInputNilai.setNilai(String.valueOf(nilai));
                    formInputNilai.setVisible(true);

                    formInputNilai.addWindowListener(new java.awt.event.WindowAdapter() {
                        @Override
                        public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                            reloadTableData(tableModel, mahasiswaId);
                        }
                    });
                } else {
                    JOptionPane.showMessageDialog(nilaiFrame, "Pilih nilai yang akan diedit!");
                }
            });

            // Logika tombol Delete
            btnHapus.addActionListener(e -> {
                int selectedRowInTable = nilaiTable.getSelectedRow();
                if (selectedRowInTable != -1) {
                    int nilaiId = Integer.parseInt(nilaiTable.getValueAt(selectedRowInTable, 0).toString());
                    int confirm = JOptionPane.showConfirmDialog(nilaiFrame, "Yakin ingin menghapus nilai ini?");
                    if (confirm == JOptionPane.YES_OPTION) {
                        if (nilaiModel.deleteNilai(nilaiId)) {
                            tableModel.removeRow(selectedRowInTable);
                            JOptionPane.showMessageDialog(nilaiFrame, "Nilai berhasil dihapus!");
                        } else {
                            JOptionPane.showMessageDialog(nilaiFrame, "Gagal menghapus nilai!");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(nilaiFrame, "Pilih nilai yang akan dihapus!");
                }
            });

            nilaiFrame.setLayout(new BorderLayout());
            nilaiFrame.add(scrollPane, BorderLayout.CENTER);
            buttonPanel.add(btnTambah);
            buttonPanel.add(btnEdit);
            buttonPanel.add(btnHapus);
            nilaiFrame.add(buttonPanel, BorderLayout.SOUTH);
            nilaiFrame.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(view, "Pilih mahasiswa terlebih dahulu!");
        }
    }

    private void reloadTableData(DefaultTableModel tableModel, int mahasiswaId) {
        tableModel.setRowCount(0);
        List<Nilai> nilaiList = nilaiModel.findByMahasiswaId(mahasiswaId);
        for (Nilai nilai : nilaiList) {
            tableModel.addRow(new Object[]{nilai.getId(), nilai.getMataKuliah(), nilai.getSemester(), nilai.getNilai()});
        }
    }
}
