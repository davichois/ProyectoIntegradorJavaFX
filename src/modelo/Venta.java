package modelo;

/**
 * @author davichois
 */
public class Venta {

    private int idVenta;
    private String tipoVenta;
    private int idCliente;
    private int idVendedor;
    private double totalVenta;

    public Venta(int idVenta, String tipoVenta, int idCliente, int idVendedor, double totalVenta) {
        this.idVenta = idVenta;
        this.tipoVenta = tipoVenta;
        this.idCliente = idCliente;
        this.idVendedor = idVendedor;
        this.totalVenta = totalVenta;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public String getTipoVenta() {
        return tipoVenta;
    }

    public void setTipoVenta(String tipoVenta) {
        this.tipoVenta = tipoVenta;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(int idVendedor) {
        this.idVendedor = idVendedor;
    }

}
