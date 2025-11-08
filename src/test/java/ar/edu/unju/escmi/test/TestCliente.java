package ar.edu.unju.escmi.test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
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

        Cliente clienteGuardado = dao.buscarPorDni(48678917); // buscar después de guardar
        assertNotNull(clienteGuardado.getId(), "El ID no debería ser nulo después de guardar");
    }

    @Test
    void testModificarCliente() {
        Cliente cliente = new Cliente("Ana", "Gómez", "Jujuy", 12345678, true);
        dao.guardarCliente(cliente);

        Cliente clienteGuardado = dao.buscarPorDni(12345678); // 🔹 buscar con ID actualizado
        clienteGuardado.setNombre("Ana María");
        dao.modificarCliente(clienteGuardado);

        Cliente clienteModificado = dao.buscarPorDni(12345678);
        assertEquals("Ana María", clienteModificado.getNombre(), "El nombre debería haberse modificado");
    }

    @Test
    void testObtenerClientes() {
        List<Cliente> clientes = dao.obtenerClientes();
        assertNotNull(clientes, "La lista de clientes no debería ser nula");
    }
}
