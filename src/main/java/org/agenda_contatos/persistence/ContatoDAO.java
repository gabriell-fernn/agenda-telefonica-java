package org.agenda_contatos.persistence;

import org.agenda_contatos.model.entities.Contato;
import org.agenda_contatos.utils.HibernateSessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ContatoDAO {
    // CREATE
    public void save(Contato contato) {
        Transaction transaction = null;
        try (Session session = HibernateSessionFactory.getSession()) {
            transaction = session.beginTransaction();
            session.save(contato);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    // READ - Buscar por ID
    public Contato getById(Long id) {
        try (Session session = HibernateSessionFactory.getSession()) {
            return session.get(Contato.class, id);
        }
    }

    // READ - Buscar Todos
    public List<Contato> getAll() {
        try (Session session = HibernateSessionFactory.getSession()) {
            return session.createQuery("from Contato", Contato.class).list();
        }
    }

    // UPDATE
    public void update(Contato contato) {
        Transaction transaction = null;
        try (Session session = HibernateSessionFactory.getSession()) {
            transaction = session.beginTransaction();
            session.update(contato);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    // DELETE
    public void delete(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateSessionFactory.getSession()) {
            transaction = session.beginTransaction();
            Contato contato = session.get(Contato.class, id);
            if (contato != null) {
                session.delete(contato);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
}
