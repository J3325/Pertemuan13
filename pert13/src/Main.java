import controller.MahasiswaController;
import view.FormMahasiswa;

public class Main {
    public static void main(String[] args) {
        FormMahasiswa view = new FormMahasiswa();
        new MahasiswaController(view);
        view.setVisible(true);
    }
}
