/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.telefonos.persistence;

import co.edu.uniandes.csw.telefonos.entities.CompradorEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Laura Valentina Prieto Jimenez
 */
@Stateless
public class CompradorPersistence {

    @PersistenceContext(unitName = "telefonosPU")
    protected EntityManager em;

    public CompradorEntity create(CompradorEntity compradorEntity) {
        em.persist(compradorEntity);
        return compradorEntity;
    }

    public CompradorEntity find(Long compradorId) {
        return em.find(CompradorEntity.class, compradorId);
    }

    public List<CompradorEntity> findAll() {
        TypedQuery<CompradorEntity> query = em.createQuery("select u from CompradorEntity u", CompradorEntity.class);
        return query.getResultList();
    }
}
