package ar.edu.unju.escmi.main;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import ar.edu.unju.escmi.config.EmfSingleton;
import ar.edu.unju.escmi.dao.imp.ClienteDaoImp;
import ar.edu.unju.escmi.dao.imp.DetalleFacturaDaoImp;
import ar.edu.unju.escmi.dao.imp.FacturaDaoImp;
import ar.edu.unju.escmi.dao.imp.ProductoDaoImp;


public class MenuPrincipal {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ClienteDaoImp clienteDao = new ClienteDaoImp();
        ProductoDaoImp productoDao = new ProductoDaoImp();
        FacturaDaoImp facturaDao = new FacturaDaoImp();
        DetalleFacturaDaoImp detalleDao = new DetalleFacturaDaoImp();

        int opcion = -1;
        do {
            System.out.println("\n--- MENÚ PRINCIPAL ---");
            System.out.println("1 - Alta de cliente");
            System.out.println("2 - Alta de producto");
            System.out.println("3 - Realizar venta de productos (nueva factura)");
            System.out.println("4 - Buscar factura por ID");
            System.out.println("5 - Eliminar lógicamente una factura");
            System.out.println("6 - Eliminar lógicamente un producto");
            System.out.println("7 - Modificar datos de cliente");
            System.out.println("8 - Modificar precio de producto");
            System.out.println("9 - Eliminar producto (eliminación lógica)");
            System.out.println("10 - Mostrar todas las facturas");
            System.out.println("11 - Mostrar todos los clientes");
            System.out.println("12 - Mostrar facturas con total mayor a $500.000");
            System.out.println("0 - Salir");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = sc.nextInt();
                sc.nextLine();

                switch (opcion) {
                    case 1 -> clienteDao.altaCliente(sc);
                    case 2 -> productoDao.altaProducto(sc);
                    case 3 -> facturaDao.altaFactura(sc);
                    case 4 -> facturaDao.buscarFacturaPorId(sc);
                    case 5 -> facturaDao.eliminarFactura(sc);
                    case 6, 9 -> productoDao.eliminarLogicoProducto(sc);
                    case 7 -> clienteDao.modificarCliente(sc);
                    case 8 -> productoDao.modificarPrecio(sc);
                    case 10 -> facturaDao.mostrarFacturas();
                    case 11 -> clienteDao.mostrarClientes();
                    case 12 -> facturaDao.mostrarFacturasMayoresA(500000);
                    case 0 -> System.out.println("Programa finalizado.");
                    default -> System.out.println("Opción inválida. Intente nuevamente.");
                }

            } catch (InputMismatchException e) {
                System.out.println("Error: Debe ingresar un número válido.");
                sc.nextLine();
            } catch (Exception e) {
                System.out.println("Ocurrió un error: " + e.getMessage());
            }
        } while (opcion != 0);

        sc.close();
        EmfSingleton.close();
    }
}
