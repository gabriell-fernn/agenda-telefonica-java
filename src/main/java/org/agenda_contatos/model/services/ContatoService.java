package org.agenda_contatos.model.services;

import org.agenda_contatos.model.entities.Contato;
import org.agenda_contatos.persistence.ContatoDAO;

import java.util.List;

public class ContatoService {
    private ContatoDAO contatoDAO = new ContatoDAO();

    public void addContato(Contato contato) {
        contatoDAO.save(contato);
    }

    public List<Contato> getAllContatos() {
        return contatoDAO.getAll();
    }

    public Contato getContatoById(Long id) {
        return contatoDAO.getById(id);
    }

    public void updateContato(Contato contato) {
        contatoDAO.update(contato);
    }

    public void removeContato(Long id) {
        contatoDAO.delete(id);
    }

}
