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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import modelo.Categoria;
import modelo.Producto;
import utils.db.ConnectionDB;

/**
 * @author davichois
 */
public class ProductoDialogController implements Initializable {

    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtPrecio;
    @FXML
    private TextField txtStock;
    @FXML
    private TextField txtDescription;
    @FXML
    private TextField txtCodigoBarra;
    @FXML
    private ComboBox<?> cbCategoria;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnSalir;

    private Producto producto;

    private static ConnectionDB db = new ConnectionDB();

    private ObservableList categoria;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        categoria = FXCollections.observableArrayList();

        try {
            initCategoria();
        } catch (SQLException ex) {
            Logger.getLogger(ProductoDialogController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void guardar(MouseEvent event) {
        String nombreP = this.txtNombre.getText();
        int precioP = Integer.parseInt(this.txtPrecio.getText());
        int stockP = Integer.parseInt(this.txtStock.getText());
        String description = this.txtDescription.getText();
        String codBarra = this.txtCodigoBarra.getText();
        String categoriaId = (String) this.cbCategoria.getValue();

        int cateId = Integer.parseInt(categoriaId.split(":")[0]);

        Producto p = new Producto(nombreP, precioP, stockP, description, codBarra, cateId);

        this.producto = p;

        Stage stage = (Stage) this.btnGuardar.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void salir(MouseEvent event) {
        this.producto = null;
        Stage stage = (Stage) this.btnSalir.getScene().getWindow();
        stage.close();
    }

    private void initCategoria() throws SQLException {
        Connection conex = db.openConexion();
        PreparedStatement ps = conex.prepareCall("select * from categoria");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Categoria c = new Categoria(rs.getInt("idCategoria"), rs.getString("nombre"));
            this.categoria.add(c.getIdCategoria() + ":" + c.getNombre());
            this.cbCategoria.setItems(categoria);
        }
    }

    public Producto getProducto() {
        return producto;
    }

}
