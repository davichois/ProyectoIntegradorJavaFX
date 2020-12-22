package controlador;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import modelo.Cliente;
import modelo.Producto;
import modelo.Vendedor;
import modelo.Venta;
import utils.db.ConnectionDB;

/**
 * @author davichois
 */
public class VentaControllerGeneral implements Initializable {

    @FXML
    private TableView<Producto> tblVenta;
    @FXML
    private TableColumn colID;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colPrecio;
    @FXML
    private TableColumn colStock;
    @FXML
    private TableColumn colDescription;
    @FXML
    private TableColumn colCodigoBarra;
    @FXML
    private TableColumn colCantidad;
    @FXML
    private TableColumn colTotalVenta;
    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnVender;
    @FXML
    private Button btnRecalcular;
    @FXML
    private TextField txtDni;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtApellido;
    @FXML
    private TextField txtTelefono;
    @FXML
    private TextField txtZona;
    @FXML
    private TextField txtVendedor;
    @FXML
    private ComboBox<String> cbModoPago;
    @FXML
    private TextField txtTotal;

    private ObservableList mPago;

    private ObservableList productosC;

    private int idCliente;

    private int idVendedor;

    private double precioTotalP;

    private static ConnectionDB db = new ConnectionDB();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mPago = FXCollections.observableArrayList("Contado", "Credito");
        productosC = FXCollections.observableArrayList();
        this.cbModoPago.setItems(mPago);
        this.tblVenta.setItems(productosC);
        this.tblVenta.setEditable(true);

        this.colID.setCellValueFactory(new PropertyValueFactory("idProducto"));
        //Hacer una colummna de tabla editable
        //this.colID.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        this.colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        this.colPrecio.setCellValueFactory(new PropertyValueFactory("precio"));
        this.colStock.setCellValueFactory(new PropertyValueFactory("stock"));
        this.colDescription.setCellValueFactory(new PropertyValueFactory("descripcion"));
        this.colCodigoBarra.setCellValueFactory(new PropertyValueFactory("codigoBarra"));
        this.colCantidad.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        this.colTotalVenta.setCellValueFactory(new PropertyValueFactory("preciototal"));
        this.colCantidad.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Producto, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Producto, String> t) {
                ((Producto) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).setCantidad(Integer.parseInt(String.valueOf(t.getNewValue())));

                Producto p = tblVenta.getSelectionModel().getSelectedItem();
                System.err.println("Primero" + p.getCantidad() + "  " + p.getNombre());
                productosC.remove(p);
                p.setPreciototal(p.getCantidad() * p.getPrecio());
                productosC.add(t.getTablePosition().getRow(), p);
                tblVenta.setItems(productosC);
            }
        }
        );
        System.out.println(productosC);
    }

    @FXML
    private void agregar(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/VentaDialogProductoVista.fxml"));

        //Cargamos al padre
        Parent root = loader.load();

        VentaDialogProductoController controlador = loader.getController();

        Scene scene = new Scene(root);

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Agregar Producto del buscador");
        stage.setScene(scene);
        stage.showAndWait();

        Producto p = controlador.getP();

        if (p != null) {
            this.productosC.add(p);
            this.tblVenta.refresh();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            System.out.println(alert);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Error al asignar datos de p");
            alert.showAndWait();
        }

    }

    @FXML
    private void eliminar(ActionEvent event) {
        Producto p = this.tblVenta.getSelectionModel().getSelectedItem();
        if (p == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            System.out.println(alert);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Debes seleccionar a un producto.");
            alert.showAndWait();
        } else {
            this.productosC.remove(p);
            this.tblVenta.refresh();
        }
    }

    @FXML
    private void vender(ActionEvent event) throws SQLException {
        //relacionVenta();
        for (int i = 0; i < productosC.size(); i++) {
            Producto p = (Producto) productosC.get(i);
            System.out.println("Holas La Venta" + p.getNombre() + "  " + p.getCantidad() + "    " + p.getPreciototal());
            //this.precioTotalP = p.getPreciototal();
        }
        productosC.removeAll(productosC);
        
    }

    @FXML
    private void buscarUsuario(KeyEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/VentaDialogUsuarioVista.fxml"));

        //Cargamos al padre
        Parent root = loader.load();

        VentaDialogUsuarioController controlador = loader.getController();

        Scene scene = new Scene(root);

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Agregar Usuario del buscador");
        stage.setScene(scene);
        stage.showAndWait();

        Cliente c = controlador.getC();

        if (c != null) {
            this.txtDni.setText(c.getDni());
            this.txtNombre.setText(c.getNombre());
            this.txtApellido.setText(c.getApellido());
            this.txtTelefono.setText(String.valueOf(c.getTelefono()));
            this.txtZona.setText(c.getIdZona());
            this.idCliente = c.getIdCliente();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            System.out.println(alert);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Error al asignar datos de c");
            alert.showAndWait();
        }
    }

    @FXML
    private void buscarVendedor(KeyEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/VentaDialogVendedorVista.fxml"));

        //Cargamos al padre
        Parent root = loader.load();

        VentaDialogVendedorController controlador = loader.getController();

        Scene scene = new Scene(root);

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Agregar Vendededor del buscador");
        stage.setScene(scene);
        stage.showAndWait();

        Vendedor v = controlador.getV();

        if (v != null) {
            this.txtVendedor.setText(v.getNombre());
            this.idCliente = v.getIdVendedor();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            System.out.println(alert);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Error al asignar datos de v");
            alert.showAndWait();
        }
    }

    private void relacionVenta() throws SQLException {
        Connection conex = db.openConexion();
        PreparedStatement ps = conex.prepareStatement("insert into venta(tipoVenta, totalVenta, idCliente, idVendedor) values(?,?,?,?)");
        ps.setString(1, this.cbModoPago.getValue());
        ps.setDouble(2, 200);
        ps.setInt(3, this.idCliente);
        ps.setInt(4, this.idVendedor);

        int resultado = ps.executeUpdate();

        if (resultado > 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            System.out.println(alert);
            alert.setHeaderText(null);
            alert.setTitle("Info");
            alert.setContentText("Exitoso");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            System.out.println(alert);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Error al relacionar vd");
            alert.showAndWait();
        }
    }

    @FXML
    private void recalcular(MouseEvent event) {
        this.tblVenta.refresh();
        this.txtTotal.setText(String.valueOf(this.precioTotalP));
    }

}
