package ar.edu.unju.escmi.test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.Test;

public class TestConexion {

    @Test
    public void probarConexion() {
        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("tp8_grupo8");
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            System.out.println("Conexión a la base de datos exitosa!");
            em.close();
            emf.close();
        } catch (Exception e) {
            System.err.println("Error de conexión: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
