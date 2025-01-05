package model; 
 
public class Mahasiswa { 
    private int id; 
    private String nim; 
    private String nama; 
    private String jurusan; 
    private int angkatan; 
 
    public Mahasiswa() { 
        super(); 
    } 
 
    public Mahasiswa(int id, String nim, String nama, String jurusan, int 
angkatan) { 
        this.id = id; 
        this.nim = nim; 
        this.nama = nama; 
        this.jurusan = jurusan; 
        this.angkatan = angkatan; 
    } 
 
    // getter and setter 
 
    public int getId() { 
        return id; 
    } 
 
    public void setId(int id) { 
        this.id = id; 
    } 
 
    public String getNim() { 
        return nim; 
    } 
 
    public void setNim(String nim) { 
        this.nim = nim; 
    } 
 
    public String getNama() { 
        return nama; 
    } 
 
    public void setNama(String nama) { 
        this.nama = nama; 
    } 
 
    public String getJurusan() { 
        return jurusan; 
    } 
 
    public void setJurusan(String jurusan) { 
        this.jurusan = jurusan; 
    } 
 
    public int getAngkatan() { 
        return angkatan; 
    } 
 
    public void setAngkatan(int angkatan) { 
        this.angkatan = angkatan; 
    } 
}