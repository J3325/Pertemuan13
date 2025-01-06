import controller.MahasiswaController;
import controller.NilaiController;
import model.NilaiModel;
import view.FormMahasiswa;

public class Main {
    public static void main(String[] args) {
        
        FormMahasiswa view = new FormMahasiswa();
        NilaiModel nilaiModel = new NilaiModel();
        // Membuat objek MahasiswaController dan NilaiController dengan parameter view
        new MahasiswaController(view, nilaiModel);
        new NilaiController(view);
        // Menampilkan tampilan
        view.setVisible(true);
    }
}
