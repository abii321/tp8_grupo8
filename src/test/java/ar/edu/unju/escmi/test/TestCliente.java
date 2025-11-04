package ar.edu.unju.escmi.test;

import ar.edu.unju.escmi.dao.imp.ClienteDaoImp;
import ar.edu.unju.escmi.entities.Cliente;

public class TestCliente {
    public static void main(String[] args) {

        ClienteDaoImp dao = new ClienteDaoImp();

        Cliente cli = new Cliente();
        cli.setNombre("Luján");
        cli.setApellido("Cansino");
        cli.setDni(44555666);
        cli.setDomicilio("San Salvador de Jujuy");
        cli.setEstado(true);

        dao.guardarCliente(cli);
        System.out.println(" Cliente guardado correctamente!");

        System.out.println("\n Listando clientes:");
        dao.obtenerClientes().forEach(System.out::println);
    }
}
