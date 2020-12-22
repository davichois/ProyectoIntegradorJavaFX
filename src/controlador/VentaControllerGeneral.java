package controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import modelo.Producto;
import modelo.Venta;

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
    private Button btnAgregar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnVender;
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

    private ObservableList mPago;

    private ObservableList productosC;

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
    private void vender(ActionEvent event) {
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
    }

    @FXML
    private void buscarVendedor(KeyEvent event) {
    }

}
