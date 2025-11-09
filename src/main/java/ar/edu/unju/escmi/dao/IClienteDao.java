  package ar.edu.unju.escmi.dao;

import java.util.List;
import ar.edu.unju.escmi.entities.Cliente;

public interface IClienteDao {
    void guardarCliente(Cliente cliente);
    void modificarCliente(Cliente cliente);
     void borrarCliente(Cliente cliente);
    List<Cliente> obtenerClientes();
    Cliente buscarPorDni(int dni);
}
