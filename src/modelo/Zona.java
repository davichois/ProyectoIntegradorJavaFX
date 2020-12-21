package modelo;

/**
 * @author davichois
 */
public class Zona {

    private int idZona;
    private String departamento;
    private String provincia;
    private String distrito;

    public Zona(int idZona, String departamento, String provincia, String distrito) {
        this.departamento = departamento;
        this.provincia = provincia;
        this.distrito = distrito;
        this.idZona = idZona;
    }

    public Zona(String departamento, String provincia, String distrito) {
        this.departamento = departamento;
        this.provincia = provincia;
        this.distrito = distrito;

    }

    public int getIdZona() {
        return idZona;
    }

    public void setIdZona(int idZona) {
        this.idZona = idZona;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

}
