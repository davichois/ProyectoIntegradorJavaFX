package controlador;

import java.io.IOException;
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.Producto;
import utils.db.ConnectionDB;

/**
 * @author davichois
 */
public class ProductosControllerGeneral implements Initializable {

    @FXML
    private TableView<Producto> tblProducto;
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
    private TableColumn colIDCategoria;
    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnActualizar;
    @FXML
    private Button btnEliminar;
    @FXML
    private TextField txtBusqueda;

    private ObservableList producto;

    private static ConnectionDB db = new ConnectionDB();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        producto = FXCollections.observableArrayList();
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
            Logger.getLogger(ProductosControllerGeneral.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void agregar(ActionEvent event) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/ProductoDialogVista.fxml"));

        //Cargamos al padre
        Parent root = loader.load();

        ProductoDialogController controlador = loader.getController();

        Scene scene = new Scene(root);

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Agregar Usuario");
        stage.setScene(scene);
        stage.showAndWait();

        Producto p = controlador.getProducto();
        if (p != null) {
            Connection conex = db.openConexion();
            PreparedStatement ps = conex.prepareStatement("insert into producto(nombre, precio, stock, descripcion, codigoBarra, idCategoria) values (?,?,?,?,?,?)");
            ps.setString(1, p.getNombre());
            ps.setInt(2, p.getPrecio());
            ps.setInt(3, p.getStock());
            ps.setString(4, p.getDescripcion());
            ps.setString(5, p.getDescripcion());
            ps.setInt(6, p.getIdCategoria());

            int resultado = ps.executeUpdate();

            if (resultado > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                System.out.println(alert);
                alert.setHeaderText(null);
                alert.setTitle("Info");
                alert.setContentText("Producto insertado correctamente.");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                System.out.println(alert);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.setContentText("Error al insertar Producto");
                alert.showAndWait();
            }
            this.producto.clear();
            traerDatos();
            db.cerrarConnection();
        }

    }

    @FXML
    private void actualizar(ActionEvent event) {
    }

    @FXML
    private void eliminar(ActionEvent event) throws SQLException {
        Producto p = this.tblProducto.getSelectionModel().getSelectedItem();

        if (p == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            System.out.println(alert);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Debes seleccionar a un producto.");
            alert.showAndWait();
        } else {
            Connection conex = db.openConexion();
            PreparedStatement ps = conex.prepareStatement("delete from producto where idProducto=?");
            ps.setInt(1, p.getIdProducto());

            int resultado = ps.executeUpdate();

            if (resultado > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                System.out.println(alert);
                alert.setHeaderText(null);
                alert.setTitle("Info");
                alert.setContentText("Producto eliminado correctamente.");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                System.out.println(alert);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.setContentText("Error al eliminar Producto");
                alert.showAndWait();
            }
            this.producto.clear();
            traerDatos();
            db.cerrarConnection();
        }
    }

    @FXML
    private void busquedaFiltrada(KeyEvent event) {
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

}
