package org.agenda_contatos.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.agenda_contatos.MainApp;
import org.agenda_contatos.model.entities.Contato;
import org.agenda_contatos.model.services.ContatoService;
import org.agenda_contatos.utils.Alerta;
import org.agenda_contatos.utils.PathFXML;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private Button btnAdicionar;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnExcluir;

    @FXML
    TableView<Contato> tbContato;
    @FXML
    TableColumn<Contato, Integer> columnIdContato;
    @FXML
    TableColumn <Contato, String> columnNomeContato;
    @FXML
    TableColumn <Contato, String> columnTelefoneContato;
    @FXML
    TableColumn <Contato, String> columnEmailContato;
    @FXML
    TableColumn <Contato, String> columnEnderecoContato;

    private Contato contato;
    private ContatoService contatoService = new ContatoService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        inicializarGUI();
        updateTableView();
    }

    public void inicializarGUI(){
        tbContato.setOnMouseClicked(event -> {
            if(event.getButton() == MouseButton.PRIMARY) {
                this.contato = tbContato.getSelectionModel().getSelectedItem();

                this.btnEditar.setDisable(this.contato == null);
                this.btnExcluir.setDisable(this.contato == null);

                if(event.getClickCount() == 2){
                    onBtnEditarAction();
                }
            }
        });
    }

    public void atualizarGUI(){
        tbContato.getItems().clear();
        this.btnEditar.setDisable(true);
        this.btnExcluir.setDisable(true);
        columnIdContato.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnNomeContato.setCellValueFactory(new PropertyValueFactory<>("nome"));
        columnTelefoneContato.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        columnEmailContato.setCellValueFactory(new PropertyValueFactory<>("email"));
        columnEnderecoContato.setCellValueFactory(new PropertyValueFactory<>("endereco"));
    }

    public void updateTableView(){
        atualizarGUI();

        if (contatoService == null){
            throw  new IllegalStateException("Sercice ContatoService não instanciado: nullpoint exception");
        }

        List<Contato> dados = contatoService.getAllContatos();
        ObservableList<Contato> observableList = FXCollections.observableList(dados);
        tbContato.setItems(observableList);
    }

    @FXML
    public void onBtnAdicionarAction(){
        this.contato = null;
        exibirModal("Cadastrar Contato", "ContatoModalView.fxml");
        updateTableView();
    }

    @FXML
    public void onBtnEditarAction() {
        exibirModal("Editar Contato", "ContatoModalView.fxml");
        updateTableView();
    }

    @FXML
    public void onBtnExcluirAction() {
        boolean confirmar = Alerta.exibirConfirmacao("Confirmação", "Deseja realmente excluir o contato?");
        if (!confirmar) return;
        try {
            contatoService.removeContato(this.contato.getId());
            updateTableView();
        } catch (Exception e) {
            Alerta.exibirAlerta("Error", "Erro ao excluir contato", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void exibirModal(String title, String arquivoFXML){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root = fxmlLoader.load(new FileInputStream(PathFXML.pathBase() + arquivoFXML));
            ContatoModalController controller = fxmlLoader.getController();
            controller.setContatoService(new ContatoService());
            controller.setContato(this.contato);
            Stage modal = new Stage();
            modal.setTitle(title);
            modal.setScene(new Scene(root));
            modal.initModality(Modality.WINDOW_MODAL);
            modal.initOwner(MainApp.getScene().getWindow());
            modal.showAndWait();

        } catch (RuntimeException | IOException e) {
            Alerta.exibirAlerta("Error","Erro ao carregar a view",e.getMessage(), Alert.AlertType.ERROR);
        }
    }

}
