package modelo;

/**
 * @author davichois
 */
public class Categoria {

    private int idCategoria;
    private String nombre;

    public Categoria(String nombre) {
        this.nombre = nombre;
    }

    public Categoria(int idCategoria, String nombre) {
        this.nombre = nombre;
        this.idCategoria = idCategoria;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
