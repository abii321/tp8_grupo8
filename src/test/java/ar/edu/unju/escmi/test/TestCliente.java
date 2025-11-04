package ar.edu.unju.escmi.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import ar.edu.unju.escmi.dao.IClienteDao;
import ar.edu.unju.escmi.dao.imp.ClienteDaoImp;
import ar.edu.unju.escmi.entities.Cliente;

class TestCliente {

    @Test
    void testGuardarYBuscarCliente() {
        IClienteDao dao = new ClienteDaoImp();

        Cliente cliente = new Cliente();
        cliente.setNombre("Luján");
        cliente.setApellido("Cansino");
        cliente.setDni(45678901);
        cliente.setDomicilio("San Salvador");
        cliente.setEstado(true);

        dao.guardarCliente(cliente);

        assertNotNull(cliente.getId(), "El ID no debería ser nulo después de guardar");

        Cliente clienteBuscado = dao.buscarPorId(cliente.getId());

        assertNotNull(clienteBuscado, "El cliente guardado debería existir en la BD");
        assertEquals("Luján", clienteBuscado.getNombre());
        assertEquals("Cansino", clienteBuscado.getApellido());
        assertEquals(45678901, clienteBuscado.getDni());
    }

    @Test
    void testModificarCliente() {
        IClienteDao dao = new ClienteDaoImp();

        Cliente cliente = new Cliente();
        cliente.setNombre("Ana");
        cliente.setApellido("Gómez");
        cliente.setDni(12345678);
        cliente.setDomicilio("Jujuy");
        cliente.setEstado(true);

        dao.guardarCliente(cliente);
        cliente.setNombre("Ana María");

        dao.modificarCliente(cliente);

        Cliente clienteModificado = dao.buscarPorId(cliente.getId());

        assertEquals("Ana María", clienteModificado.getNombre(), "El nombre debería haberse modificado");
    }
}

