
package com.mycompany.tradingjournal.Persistence;

import com.mycompany.tradingjournal.Logic.Operation;
import javax.persistence.EntityManager;
import java.util.List;

public class OperationRepository {
    
    public OperationRepository() {
        
    }
    
    public void save(Operation o) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try{
            em.getTransaction().begin();
            em.persist(o);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
    
    public List<Operation> findAll() {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try{
            return em.createQuery("SELECT o FROM Operation o" , Operation.class).getResultList();
        } finally {
            em.close();
        }
    }
    
    public Operation findById(int id) {
    EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
    try {
        return em.find(Operation.class, id);
    } finally {
        em.close();
    }
}
    
    public void delete(int id) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            Operation op = em.find(Operation.class, id);
            if(op != null) {
                em.remove(op);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
    
    public void update(Operation operation) {
    EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
    try {
        em.getTransaction().begin();
        em.merge(operation);
        em.getTransaction().commit();
    } catch (Exception e) {
        em.getTransaction().rollback();
        throw e;
    } finally {
        em.close();
    }
}
}
