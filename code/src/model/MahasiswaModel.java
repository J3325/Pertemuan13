package model; 
 
import classes.BaseModel; 
import java.sql.ResultSet; 
import java.sql.SQLException; 
import java.util.Arrays; 
 
public class MahasiswaModel extends BaseModel<Mahasiswa> { 
    public MahasiswaModel() { 
        super("mahasiswa", Arrays.asList("id", "nim", "nama", "jurusan", "angkatan")); 
    } 
 
    @Override 
    protected boolean isNewRecord(Mahasiswa mahasiswa) { 
        return mahasiswa.getId() == 0; 
    } 
 
    @Override 
    protected Mahasiswa mapRow(ResultSet rs) throws SQLException { 
        return new Mahasiswa( 
                rs.getInt("id"), 
                rs.getString("nim"), 
                rs.getString("nama"), 
                rs.getString("jurusan"), 
                rs.getInt("angkatan") 
        ); 
    } 
 
    @Override 
    protected Object[] getValues(Mahasiswa mahasiswa, boolean includeId) { 
        if (includeId) { 
            return new Object[]{mahasiswa.getNim(), mahasiswa.getNama(), 
mahasiswa.getJurusan(), mahasiswa.getAngkatan(), mahasiswa.getId()}; 
        } else { 
            return new Object[]{mahasiswa.getNim(), mahasiswa.getNama(), 
mahasiswa.getJurusan(), mahasiswa.getAngkatan()}; 
        } 
    } 
}