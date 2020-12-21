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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import modelo.Categoria;
import utils.db.ConnectionDB;

/**
 * @author davichois
 */
public class CategoriasControllerGeneral implements Initializable {

    @FXML
    private TableView<Categoria> tblCategoria;
    @FXML
    private TableColumn colID;
    @FXML
    private TableColumn colCategoria;
    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnActualizar;
    @FXML
    private Button btnEliminar;
    @FXML
    private TextField txtBusqueda;
    @FXML
    private TextField txtCategoria;

    private ObservableList<Categoria> categoria;

    private ObservableList categoriaFiltrador;

    private static ConnectionDB db = new ConnectionDB();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        categoria = FXCollections.observableArrayList();
        categoriaFiltrador = FXCollections.observableArrayList();

        this.tblCategoria.setItems(categoria);

        this.colID.setCellValueFactory(new PropertyValueFactory("idCategoria"));
        this.colCategoria.setCellValueFactory(new PropertyValueFactory("nombre"));

        try {
            traerDatos();
        } catch (SQLException ex) {
            Logger.getLogger(CategoriasControllerGeneral.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void agregar(ActionEvent event) throws SQLException {
        String categoria = txtCategoria.getText();
        Connection conex = db.openConexion();
        PreparedStatement ps = conex.prepareStatement("insert into categoria(nombre) values(?)");
        ps.setString(1, categoria);

        int resultado = ps.executeUpdate();

        if (resultado > 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Info");
            alert.setContentText("Insertado Correctamente");
            alert.showAndWait();
            this.categoria.clear();
            traerDatos();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Error al insertar");
            alert.showAndWait();
        }

        db.cerrarConnection();
    }

    @FXML
    private void actualizar(ActionEvent event) throws SQLException {
        String nombre = txtCategoria.getText();
        Categoria c = this.tblCategoria.getSelectionModel().getSelectedItem();
        if (c == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            System.out.println(alert);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Debes seleccionar a una persona.");
            alert.showAndWait();
        } else {
            Connection conex = db.openConexion();
            PreparedStatement ps = conex.prepareStatement("update categoria set nombre=? where idCategoria=?");
            ps.setString(1, nombre);
            ps.setInt(2, c.getIdCategoria());

            int resultado = ps.executeUpdate();

            if (resultado > 0) {
                System.out.println("Registro eliminado correctamente");
            } else {
                System.out.println("Error al eliminar");
            }
            this.categoria.clear();
            traerDatos();
            db.cerrarConnection();
        }
    }

    @FXML
    private void eliminar(ActionEvent event) throws SQLException {
        Categoria c = this.tblCategoria.getSelectionModel().getSelectedItem();
        if (c == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            System.out.println(alert);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Debes seleccionar a una persona.");
            alert.showAndWait();
        } else {
            Connection conex = db.openConexion();
            PreparedStatement ps = conex.prepareStatement("delete from categoria where idCategoria=?");
            ps.setInt(1, c.getIdCategoria());

            int resultado = ps.executeUpdate();

            if (resultado > 0) {
                System.out.println("Registro eliminado correctamente");
            } else {
                System.out.println("Error al eliminar");
            }
            this.categoria.clear();
            traerDatos();
            db.cerrarConnection();
        }

    }

    private void traerDatos() throws SQLException {
        Connection conex = db.openConexion();
        PreparedStatement ps = conex.prepareStatement("select * from categoria");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Categoria addCategoria = new Categoria(rs.getInt("idCategoria"), rs.getString("nombre"));
            this.categoria.add(addCategoria);
            this.tblCategoria.refresh();
        }
    }

    @FXML
    private void busquedaCategoria(KeyEvent event) {
        String filtrador = txtBusqueda.getText();
        if (filtrador.isEmpty()) {
            this.tblCategoria.setItems(categoria);
        } else {
            this.categoriaFiltrador.clear();
            for (Categoria c : categoria) {
                if (c.getNombre().toLowerCase().contains(filtrador.toLowerCase())) {
                    this.categoriaFiltrador.add(c);
                }
            }
            this.tblCategoria.setItems(categoriaFiltrador);
            this.tblCategoria.refresh();
        }

    }
}
