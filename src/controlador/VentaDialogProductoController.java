package controlador;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import modelo.Producto;
import utils.db.ConnectionDB;

/**
 * @author davichois
 */
public class VentaDialogProductoController implements Initializable {

    @FXML
    private TextField txtBusqueda;
    @FXML
    private TableView<Producto> tblProducto;
    @FXML
    private TableColumn colID;
    @FXML
    private TableColumn colCodigoBarra;
    @FXML
    private TableColumn colIDCategoria;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colPrecio;
    @FXML
    private TableColumn colStock;
    @FXML
    private TableColumn colDescription;
    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnSalir;

    private ObservableList<Producto> producto;

    private ObservableList<Producto> productoFiltrado;

    private Producto p;

    private static ConnectionDB db = new ConnectionDB();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        producto = FXCollections.observableArrayList();
        productoFiltrado = FXCollections.observableArrayList();

        this.tblProducto.setItems(producto);

        this.colID.setCellValueFactory(new PropertyValueFactory("idProducto"));
        this.colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        this.colPrecio.setCellValueFactory(new PropertyValueFactory("precio"));
        this.colStock.setCellValueFactory(new PropertyValueFactory("stock"));
        this.colDescription.setCellValueFactory(new PropertyValueFactory("descripcion"));
        this.colCodigoBarra.setCellValueFactory(new PropertyValueFactory("codigoBarra"));
        this.colIDCategoria.setCellValueFactory(new PropertyValueFactory("idCategoria"));

        try {
            traerDatos();
        } catch (SQLException ex) {
            Logger.getLogger(VentaDialogProductoController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void agregar(MouseEvent event) {
        Producto p = this.tblProducto.getSelectionModel().getSelectedItem();
        if (p == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            System.out.println(alert);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Debes seleccionar a un producto.");
            alert.showAndWait();
        } else {
            Producto producto = new Producto(p.getIdProducto(), p.getNombre(), p.getPrecio(), p.getStock(), p.getDescripcion(), p.getCodigoBarra(), p.getIdCategoria());
            this.p = producto;
        }

        Stage stage = (Stage) this.btnAgregar.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void salir(MouseEvent event) {
        this.p = null;
        Stage stage = (Stage) this.btnSalir.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void busquedaProducto(KeyEvent event) {
        String busquedaC = this.txtBusqueda.getText();
        if (busquedaC.isEmpty()) {
            this.tblProducto.setItems(producto);
        } else {
            this.productoFiltrado.clear();
            for (Producto p : this.producto) {
                if (p.getNombre().toLowerCase().contains(busquedaC.toLowerCase())) {
                    this.productoFiltrado.add(p);
                }
            }
            this.tblProducto.setItems(productoFiltrado);
            this.tblProducto.refresh();
        }
    }

    private void traerDatos() throws SQLException {
        Connection conex = db.openConexion();
        PreparedStatement ps = conex.prepareStatement("select * from producto");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Producto newProducto = new Producto(rs.getInt("idProducto"), rs.getString("nombre"), rs.getInt("precio"), rs.getInt("stock"), rs.getString("descripcion"), rs.getString("codigoBarra"), rs.getInt("idCategoria"));
            this.producto.add(newProducto);
            this.tblProducto.refresh();
        }
    }

    public Producto getP() {
        return p;
    }

}
