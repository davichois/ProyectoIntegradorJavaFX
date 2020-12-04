package modelo;

/**
 * @author usuario
 */
public class Codigo {

    private int idCodigo;
    private String codigo;

    public Codigo(int idCodigo, String codigo) {
        this.idCodigo = idCodigo;
        this.codigo = codigo;
    }

    public int getIdCodigo() {
        return idCodigo;
    }

    public void setIdCodigo(int idCodigo) {
        this.idCodigo = idCodigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

}
