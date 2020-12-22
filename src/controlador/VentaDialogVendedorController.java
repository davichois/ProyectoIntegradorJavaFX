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
import modelo.Vendedor;
import utils.db.ConnectionDB;

/**
 * @author davichois
 */
public class VentaDialogVendedorController implements Initializable {

    @FXML
    private TextField txtBusqueda;
    @FXML
    private TableView<Vendedor> tblVendedor;
    @FXML
    private TableColumn colID;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colEdad;
    @FXML
    private TableColumn colDni;
    @FXML
    private TableColumn colTelefono;
    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnSalir;

    private ObservableList<Vendedor> vendedor;

    private ObservableList<Vendedor> vendedorFiltrado;

    private Vendedor v;

    private static ConnectionDB db = new ConnectionDB();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        vendedor = FXCollections.observableArrayList();
        vendedorFiltrado = FXCollections.observableArrayList();

        this.tblVendedor.setItems(vendedor);

        this.colID.setCellValueFactory(new PropertyValueFactory("idVendedor"));
        this.colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        this.colEdad.setCellValueFactory(new PropertyValueFactory("apellido"));
        this.colDni.setCellValueFactory(new PropertyValueFactory("dni"));
        this.colTelefono.setCellValueFactory(new PropertyValueFactory("telefono"));

        try {
            traerDatos();
        } catch (SQLException ex) {
            Logger.getLogger(VentaDialogVendedorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void agregar(MouseEvent event) {
        Vendedor v = this.tblVendedor.getSelectionModel().getSelectedItem();
        if (v == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            System.out.println(alert);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Debes seleccionar a un producto.");
            alert.showAndWait();
        } else {
            Vendedor vendedor = new Vendedor(v.getIdVendedor(), v.getNombre(), v.getEdad(), v.getDni(), v.getTelefono());
            this.v = vendedor;
        }

        Stage stage = (Stage) this.btnAgregar.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void salir(MouseEvent event) {
        this.v = null;
        Stage stage = (Stage) this.btnSalir.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void busquedaProducto(KeyEvent event) {
        String busquedaC = this.txtBusqueda.getText();
        if (busquedaC.isEmpty()) {
            this.tblVendedor.setItems(vendedor);
        } else {
            this.vendedorFiltrado.clear();
            for (Vendedor v : this.vendedor) {
                if (v.getNombre().toLowerCase().contains(busquedaC.toLowerCase())) {
                    this.vendedorFiltrado.add(v);
                }
            }
            this.tblVendedor.setItems(vendedorFiltrado);
            this.tblVendedor.refresh();
        }
    }

    private void traerDatos() throws SQLException {
        Connection conex = db.openConexion();
        PreparedStatement ps = conex.prepareStatement("select * from vendedor");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Vendedor newVendedor = new Vendedor(rs.getInt("idVendedor"), rs.getString("nombre"), rs.getInt("edad"), rs.getInt("dni"), rs.getInt("telefono"));
            this.vendedor.add(newVendedor);
            this.tblVendedor.refresh();
        }
    }

    public Vendedor getV() {
        return v;
    }

}
