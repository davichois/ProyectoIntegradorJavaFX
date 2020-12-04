package modelo;

/**
 * @author davichois
 */
public class Zona {

    private int idZona;
    private String nombreZona;

    public Zona(int idZona, String nombreZona) {
        this.idZona = idZona;
        this.nombreZona = nombreZona;
    }

    public int getIdZona() {
        return idZona;
    }

    public void setIdZona(int idZona) {
        this.idZona = idZona;
    }

    public String getNombreZona() {
        return nombreZona;
    }

    public void setNombreZona(String nombreZona) {
        this.nombreZona = nombreZona;
    }

}
