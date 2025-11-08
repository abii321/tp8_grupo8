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

        assertNotNull(cliente.getId(), "El ID no debería ser nulo después de guardar");
    }

    @Test
    void testModificarCliente() {
        Cliente cliente = new Cliente("Ana", "Gómez", "Jujuy", 12345678, true);
        dao.guardarCliente(cliente);

        cliente.setNombre("Ana María");
        dao.modificarCliente(cliente);

        assertEquals("Ana María", cliente.getNombre(), "El nombre debería haberse modificado");
    }

    @Test
    void testObtenerClientes() {
        assertNotNull(dao.obtenerClientes(), "La lista de clientes no debería ser nula");
    }
}
