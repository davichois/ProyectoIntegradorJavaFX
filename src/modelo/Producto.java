package modelo;

/**
 * @author davichois
 */
public class Producto {

    private int idProducto;
    private String nombre;
    private int precio;
    private Usuario idUsuario;
    private Categoria idCategoria;
    private Codigo idCodigo;

    public Producto(int idProducto, String nombre, int precio, Usuario idUsuario, Categoria idCategoria, Codigo idCodigo) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.precio = precio;
        this.idUsuario = idUsuario;
        this.idCategoria = idCategoria;
        this.idCodigo = idCodigo;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Categoria getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Categoria idCategoria) {
        this.idCategoria = idCategoria;
    }

    public Codigo getIdCodigo() {
        return idCodigo;
    }

    public void setIdCodigo(Codigo idCodigo) {
        this.idCodigo = idCodigo;
    }

}
