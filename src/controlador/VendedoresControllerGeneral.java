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
import modelo.Vendedor;
import utils.db.ConnectionDB;

/**
 * @author davichois
 */
public class VendedoresControllerGeneral implements Initializable {

    @FXML
    private TableView<Vendedor> tblVendedores;
    @FXML
    private TableColumn colID;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colEdad;
    @FXML
    private TableColumn colDNI;
    @FXML
    private TableColumn colTelefono;
    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnActualizar;
    @FXML
    private Button btnEliminar;
    @FXML
    private TextField txtBusqueda;

    private ObservableList<Vendedor> vendedores;
    
    private ObservableList<Vendedor> vendedoresFiltrador;

    private static ConnectionDB db = new ConnectionDB();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        vendedores = FXCollections.observableArrayList();
        vendedoresFiltrador = FXCollections.observableArrayList();
        this.tblVendedores.setItems(vendedores);

        this.colID.setCellValueFactory(new PropertyValueFactory("idVendedor"));
        this.colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        this.colEdad.setCellValueFactory(new PropertyValueFactory("edad"));
        this.colDNI.setCellValueFactory(new PropertyValueFactory("dni"));
        this.colTelefono.setCellValueFactory(new PropertyValueFactory("telefono"));
        try {
            traerDatos();
        } catch (SQLException ex) {
            Logger.getLogger(VendedoresControllerGeneral.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void agregar(ActionEvent event) throws SQLException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/VendedoresDialogVista.fxml"));

            //Cargamos al padre
            Parent root = loader.load();

            VendedoresDialogController controlador = loader.getController();

            Scene scene = new Scene(root);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Agregar vendedor");
            stage.setScene(scene);
            stage.showAndWait();

            Vendedor v = controlador.getVendedor();
            if (v != null) {
                Connection conex = db.openConexion();
                PreparedStatement ps = conex.prepareStatement("insert into vendedor(nombre, edad, dni, telefono) values(?, ?, ?, ?)");
                ps.setString(1, v.getNombre());
                ps.setInt(2, v.getEdad());
                ps.setInt(3, v.getDni());
                ps.setInt(4, v.getTelefono());

                int resultado = ps.executeUpdate();

                if (resultado > 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setTitle("Imformation");
                    alert.setContentText("Vendedor registrado correctamente");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setTitle("Error");
                    alert.setContentText("Error al registrar Vendedor");
                    alert.showAndWait();
                }
                this.vendedores.clear();
                traerDatos();
                db.cerrarConnection();
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }

    }

    @FXML
    private void actualizar(ActionEvent event) throws IOException, SQLException {
        Vendedor vs = this.tblVendedores.getSelectionModel().getSelectedItem();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/VendedoresDialogVista.fxml"));

        //Cargamos al padre
        Parent root = loader.load();

        VendedoresDialogController controlador = loader.getController();

        Scene scene = new Scene(root);

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Actualizar vendedor");
        stage.setScene(scene);
        stage.showAndWait();

        Vendedor v = controlador.getVendedor();
        if (v != null) {
            Connection conex = db.openConexion();
            PreparedStatement ps = conex.prepareStatement("update vendedor set nombre=?, edad=?, dni=?, telefono=? where idVendedor=?");
            ps.setString(1, v.getNombre());
            ps.setInt(2, v.getEdad());
            ps.setInt(3, v.getDni());
            ps.setInt(4, v.getTelefono());
            ps.setInt(5, vs.getIdVendedor());

            int resultado = ps.executeUpdate();

            if (resultado > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setTitle("Imformation");
                alert.setContentText("Vendedor actualizado correctamente");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.setContentText("Error al actualizar Vendedor");
                alert.showAndWait();
            }
            this.vendedores.clear();
            traerDatos();
            db.cerrarConnection();
        }
    }

    @FXML
    private void eliminar(ActionEvent event) throws SQLException {
        Vendedor v = this.tblVendedores.getSelectionModel().getSelectedItem();

        if (v == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            System.out.println(alert);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Debes seleccionar a una persona.");
            alert.showAndWait();
        } else {
            Connection conex = db.openConexion();
            PreparedStatement ps = conex.prepareStatement("delete from vendedor where idVendedor=?");
            ps.setInt(1, v.getIdVendedor());

            int resultado = ps.executeUpdate();

            if (resultado > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setTitle("Imformation");
                alert.setContentText("Vendedor Eliminado correctamente");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.setContentText("Error al eliminar vendedor");
                alert.showAndWait();
            }
            this.vendedores.clear();
            traerDatos();
            db.cerrarConnection();
        }

    }

    private void traerDatos() throws SQLException {
        Connection conex = db.openConexion();
        PreparedStatement ps = conex.prepareStatement("select * from vendedor");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Vendedor addVendedor = new Vendedor(rs.getInt("idVendedor"), rs.getString("nombre"), rs.getInt("edad"), rs.getInt("dni"), rs.getInt("telefono"));
            this.vendedores.add(addVendedor);
            this.tblVendedores.refresh();
        }
    }

    @FXML
    private void busquedaVendedores(KeyEvent event) {
        String filtrador = txtBusqueda.getText();
        if (filtrador.isEmpty()) {
            this.tblVendedores.setItems(vendedores);
        } else {
            this.vendedoresFiltrador.clear();
            for (Vendedor v : vendedores) {
                if (v.getNombre().toLowerCase().contains(filtrador.toLowerCase())) {
                    this.vendedoresFiltrador.add(v);
                }
            }
            this.tblVendedores.setItems(vendedoresFiltrador);
            this.tblVendedores.refresh();
        }
    }

}
