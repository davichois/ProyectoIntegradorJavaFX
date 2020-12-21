package controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * @author davichois
 */
public class MenuGeneralController implements Initializable {

    @FXML
    private BorderPane bp;
    @FXML
    private Pane ap_principal;
    @FXML
    private MenuItem About;
    @FXML
    private Button btnCrearVenta;
    @FXML
    private Button btnCrearProductos;
    @FXML
    private Button btnCrearClientes;
    @FXML
    private Button btnCrearVendedores;
    @FXML
    private Button btnCrearCategorias;
    @FXML
    private Button btnCrearZonas;
    @FXML
    private Button btnInicio;
    @FXML
    private Button btnRegresar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void navigation(Stage stage, String page) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/vista/" + page + ".fxml"));
        bp.setCenter(root);

    }

    @FXML
    private void aboutProduct(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/AcercaDelProducto.fxml"));

        //Cargamos al padre
        Parent root = loader.load();

        Scene scene = new Scene(root);

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Acerca del producto");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.showAndWait();
    }

    @FXML
    private void CrearVentaVista(ActionEvent event) throws IOException {
        Stage stage = null;
        navigation(stage, "VentaVistaGeneral");
    }

    @FXML
    private void CrearProductosVista(ActionEvent event) throws IOException {
        Stage stage = null;
        navigation(stage, "ProductosVistaGeneral");
    }

    @FXML
    private void CrearClientesVista(ActionEvent event) throws IOException {
        Stage stage = null;
        navigation(stage, "ClientesVistaGeneral");
    }

    @FXML
    private void CrearVendedoresVista(ActionEvent event) throws IOException {
        Stage stage = null;
        navigation(stage, "VendedoresVistaGeneral");
    }

    @FXML
    private void CrearCategoriasVista(ActionEvent event) throws IOException {
        Stage stage = null;
        navigation(stage, "CategoriasVistaGeneral");
    }

    @FXML
    private void CrearZonasVista(ActionEvent event) throws IOException {
        Stage stage = null;
        navigation(stage, "ZonasVistaGeneral");
    }

    @FXML
    private void Inicio(ActionEvent event) {
        bp.setCenter(ap_principal);
    }

    @FXML
    private void regresarMenu(ActionEvent event) {
        bp.setCenter(ap_principal);
    }

}
