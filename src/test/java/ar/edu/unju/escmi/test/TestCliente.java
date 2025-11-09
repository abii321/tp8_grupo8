package ar.edu.unju.escmi.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ar.edu.unju.escmi.config.EmfSingleton;
import ar.edu.unju.escmi.dao.IClienteDao;
import ar.edu.unju.escmi.dao.imp.ClienteDaoImp;
import ar.edu.unju.escmi.entities.Cliente;
import jakarta.persistence.EntityManagerFactory;


class TestCliente {

    private IClienteDao dao;

    @BeforeEach
    void setUp() {
        dao = new ClienteDaoImp();
    }

    @AfterAll
    static void cerrarConexion() {
        EntityManagerFactory emf = EmfSingleton.getInstance().getEmf();
        if (emf.isOpen()) {
            emf.close();
        }
    }

    @Test
    void testGuardarCliente() {
        Cliente cliente = new Cliente("Luján", "Cansino", "San Salvador", 48678917, true);
        dao.guardarCliente(cliente);

        // buscar por DNI para confirmar que se guardó en la BD
      Cliente guardado = dao.buscarPorDni(cliente.getDni());

        assertNotNull(guardado, "El cliente debería existir en la base de datos");
        assertEquals("Luján", guardado.getNombre());
    }

    @Test
    void testModificarCliente() {
        Cliente cliente = new Cliente("Ana", "Gómez", "Jujuy", 12345678, true);
        dao.guardarCliente(cliente);

        cliente.setNombre("Ana María");
        dao.modificarCliente(cliente);

        Cliente modificado = dao.buscarPorDni(cliente.getDni());


        assertNotNull(modificado, "El cliente modificado debería existir en la base");
        assertEquals("Ana María", modificado.getNombre(), "El nombre debería haberse modificado en la BD");
    }

    @Test
    void testObtenerClientes() {
        assertNotNull(dao.obtenerClientes(), "La lista de clientes no debería ser nula");
        assertTrue(dao.obtenerClientes().size() >= 0, "Debería devolver una lista (aunque esté vacía)");
    }
}
