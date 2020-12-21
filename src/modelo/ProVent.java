package modelo;

/**
 * @author davichois
 */
public class ProVent {

    private int idProVent;
    private Producto idProducto;
    private Venta idVenta;

    public ProVent(Producto idProducto, Venta idVenta) {
        this.idProducto = idProducto;
        this.idVenta = idVenta;
    }

    public int getIdProVent() {
        return idProVent;
    }

    public void setIdProVent(int idProVent) {
        this.idProVent = idProVent;
    }

    public Producto getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Producto idProducto) {
        this.idProducto = idProducto;
    }

    public Venta getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(Venta idVenta) {
        this.idVenta = idVenta;
    }

}
