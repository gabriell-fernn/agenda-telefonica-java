package org.agenda_contatos.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Setter;
import org.agenda_contatos.model.entities.Contato;
import org.agenda_contatos.model.services.ContatoService;
import org.agenda_contatos.utils.Alerta;

import java.net.URL;
import java.util.ResourceBundle;

public class ContatoModalController implements Initializable {
    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtTelefone;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtEndereco;

    @FXML
    private Button btnSalvar;

    @FXML
    public Button btnExcluir;

    @Setter
    private ContatoService contatoService;

    private Contato contato;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { }

    @FXML
    private void onBtnSalvarAction(){
        try {
            if (contato == null) {
                this.contato = new Contato();
                lerCampos();
                contatoService.addContato(contato);
            } else {
                lerCampos();
                contatoService.updateContato(contato);
            }
            fecharModal();
        } catch (Exception e) {
            Alerta.exibirAlerta("Error","Erro ao salvar contato",e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void fecharModal() {
        Stage stage = (Stage) btnSalvar.getScene().getWindow();
        stage.close();
    }

    public void setContato(Contato contato) {
        this.contato = contato;
        if (contato != null) {
            preencherCampos();
        }
    }

    private void preencherCampos() {
        if (contato != null) {
            txtNome.setText(contato.getNome());
            txtTelefone.setText(contato.getTelefone());
            txtEmail.setText(contato.getEmail());
            txtEndereco.setText(contato.getEndereco());
        }
    }

    public void lerCampos() {
        this.contato.setNome(txtNome.getText());
        this.contato.setTelefone(txtTelefone.getText());
        this.contato.setEmail(txtEmail.getText());
        this.contato.setEndereco(txtEndereco.getText());
    }
}
