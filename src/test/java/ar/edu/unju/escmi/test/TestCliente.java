package ar.edu.unju.escmi.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import ar.edu.unju.escmi.dao.IClienteDao;
import ar.edu.unju.escmi.dao.imp.ClienteDaoImp;
import ar.edu.unju.escmi.entities.Cliente;


class TestCliente {

    private IClienteDao dao = new ClienteDaoImp();

    @Test
    void testGuardarCliente() {
        Cliente cliente = new Cliente("Luján", "Cansino", "San Salvador", 48678917, true);
        dao.guardarCliente(cliente);

        // buscar por DNI para confirmar que se guardó en la BD
        Cliente guardado = dao.buscarPorDni(cliente.getDni());

        assertNotNull(guardado);
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
