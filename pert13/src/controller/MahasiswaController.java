package controller;

import model.Mahasiswa;
import model.MahasiswaModel;
import view.FormMahasiswa;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class MahasiswaController {
    private MahasiswaModel mahasiswaModel;
    private FormMahasiswa view;

    public MahasiswaController(FormMahasiswa view) {
        this.view = view;
        view.btnSave.addActionListener(e -> saveData());
        view.btnUpdate.addActionListener(e -> updateData());
        view.btnDelete.addActionListener(e -> deleteData());
        mahasiswaModel = new MahasiswaModel();
        loadData();
    }

    private void deleteData() {
        int selectedRow = view.tblMahasiswa.getSelectedRow();
        if (selectedRow != -1) {
            int id = Integer.parseInt(view.tblMahasiswa.getModel().getValueAt(selectedRow, 0).toString());
            if (mahasiswaModel.delete(id)) {
                JOptionPane.showMessageDialog(null, "Data berhasil dihapus!");
                loadData();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(null, "Gagal menghapus data!");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Pilih data yang akan dihapus!");
        }
    }

    private void updateData() {
        int selectedRow = view.tblMahasiswa.getSelectedRow();
        if (selectedRow != -1) {
            int id = Integer.parseInt(view.tblMahasiswa.getModel().getValueAt(selectedRow, 0).toString());
            Mahasiswa mhs = mahasiswaModel.find(id);
            view.txtNim.setText(mhs.getNim());
            view.txtNama.setText(mhs.getNama());
            view.txtJurusan.setText(mhs.getJurusan());
            view.txtAngkatan.setText(String.valueOf(mhs.getAngkatan()));
        } else {
            JOptionPane.showMessageDialog(null, "Pilih data yang akan diubah!");
        }
    }

    private void saveData() {
        String nim = view.txtNim.getText();
        String nama = view.txtNama.getText();
        String jurusan = view.txtJurusan.getText();
        int angkatan = Integer.parseInt(view.txtAngkatan.getText());

        Mahasiswa mhs = new Mahasiswa();
        mhs.setNim(nim);
        mhs.setNama(nama);
        mhs.setJurusan(jurusan);
        mhs.setAngkatan(angkatan);

        int selectedRow = view.tblMahasiswa.getSelectedRow();
        if (selectedRow != -1) {
            mhs.setId(Integer.parseInt(view.tblMahasiswa.getModel().getValueAt(selectedRow, 0).toString()));
        }

        if (mahasiswaModel.save(mhs)) {
            loadData();
            clearForm();
            JOptionPane.showMessageDialog(view, "Data berhasil disimpan");
        } else {
            JOptionPane.showMessageDialog(view, "Gagal menyimpan data");
        }
    }

    private void clearForm() {
        view.txtNim.setText("");
        view.txtNama.setText("");
        view.txtJurusan.setText("");
        view.txtAngkatan.setText("");
        view.tblMahasiswa.clearSelection();
    }

    private void loadData() {
        DefaultTableModel tableModel = (DefaultTableModel) view.tblMahasiswa.getModel();
        tableModel.setRowCount(0);
        view.tblMahasiswa.setModel(tableModel);
        List<Mahasiswa> mahasiswaList = mahasiswaModel.find();
        for (Mahasiswa mahasiswa : mahasiswaList) {
            tableModel.addRow(new Object[]{
                    mahasiswa.getId(),
                    mahasiswa.getNim(),
                    mahasiswa.getNama(),
                    mahasiswa.getJurusan(),
                    mahasiswa.getAngkatan()
            });
        }
    }
}
