package modelo;

/**
 * @author davichois
 */
public class Venta {

    private int idVenta;
    private String tipoVenta;
    private Cliente idCliente;
    private Vendedor idVendedor;

    public Venta(String tipoVenta, Cliente idCliente, Vendedor idVendedor) {
        this.tipoVenta = tipoVenta;
        this.idCliente = idCliente;
        this.idVendedor = idVendedor;
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

    public Cliente getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Cliente idCliente) {
        this.idCliente = idCliente;
    }

    public Vendedor getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(Vendedor idVendedor) {
        this.idVendedor = idVendedor;
    }

}
