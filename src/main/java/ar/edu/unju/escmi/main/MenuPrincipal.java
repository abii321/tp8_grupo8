package ar.edu.unju.escmi.main;

import ar.edu.unju.escmi.dao.imp.ClienteDaoImp;
import ar.edu.unju.escmi.dao.imp.ProductoDaoImp;
import ar.edu.unju.escmi.dao.imp.FacturaDaoImp;
import ar.edu.unju.escmi.entities.Cliente;
import ar.edu.unju.escmi.entities.Producto;
import ar.edu.unju.escmi.entities.Factura;
import ar.edu.unju.escmi.utils.InputUtil;

import java.util.List;

public class MenuPrincipal {

    public static void main(String[] args) {
        ClienteDaoImp clienteDao = new ClienteDaoImp();
        ProductoDaoImp productoDao = new ProductoDaoImp();
        FacturaDaoImp facturaDao = new FacturaDaoImp();

        int opcion;

        do {
            System.out.println("\n=== MENÚ PRINCIPAL ===");
            System.out.println("1 - Alta de cliente");
            System.out.println("2 - Alta de producto");
            System.out.println("3 - Realizar la venta de productos (Alta de una nueva factura)");
            System.out.println("4 - Buscar una factura por número y mostrar sus datos");
            System.out.println("5 - Eliminar una factura (eliminación lógica)");
            System.out.println("6 - Eliminar un producto (eliminación lógica)");
            System.out.println("7 - Modificar datos de cliente");
            System.out.println("8 - Modificar precio de producto");
            System.out.println("9 - Eliminar producto (eliminación lógica)");
            System.out.println("10 - Mostrar todas las facturas");
            System.out.println("11 - Mostrar todos los clientes");
            System.out.println("12 - Mostrar las facturas que superen $500.000");
            System.out.println("0 - Salir");

            opcion = InputUtil.leerEntero("Seleccione una opción:");

            switch (opcion) {
                case 1:
                    Cliente nuevoCliente = new Cliente();
                    nuevoCliente.setNombre(InputUtil.leerTexto("Ingrese nombre del cliente:"));
                    nuevoCliente.setDni(InputUtil.leerEntero("Ingrese DNI del cliente:"));
                    nuevoCliente.setDomicilio(InputUtil.leerTexto("Ingrese domicilio del cliente:"));
                    clienteDao.guardarCliente(nuevoCliente);
                    System.out.println("Cliente guardado exitosamente.");
                    break;

                case 2:
                    Producto nuevoProducto = new Producto();
                    nuevoProducto.setDescripcion(InputUtil.leerTexto("Ingrese descripción del producto:"));
                    nuevoProducto.setPrecioUnitario(InputUtil.leerDouble("Ingrese precio unitario:"));
                    productoDao.guardarProducto(nuevoProducto);
                    System.out.println("Producto guardado exitosamente.");
                    break;

                case 3:
                    Factura nuevaFactura = new Factura();
                    nuevaFactura.setCliente(clienteDao.buscarPorDni(InputUtil.leerTexto("Ingrese DNI del cliente:")));
                    nuevaFactura.setTotal(InputUtil.leerDouble("Ingrese total de la factura:"));
                    facturaDao.guardarFactura(nuevaFactura);
                    System.out.println("Factura registrada exitosamente.");
                    break;

                case 4:
                    int numeroFactura = InputUtil.leerEntero("Ingrese número de factura a buscar:");
                    Factura f = facturaDao.obtenerFacturaPorId((long) numeroFactura);
                    if (f != null) {
                        System.out.println("Factura encontrada:\n" + f);
                    } else {
                        System.out.println("Factura no encontrada.");
                    }
                    break;

                case 5:
                    int numFacturaEliminar = InputUtil.leerEntero("Ingrese número de factura a eliminar:");
                    facturaDao.eliminacionLogica(numFacturaEliminar);
                    System.out.println("Factura eliminada lógicamente.");
                    break;

                case 6:
                case 9:
                    String descProdEliminar = InputUtil.leerTexto("Ingrese descripción del producto a eliminar:");
                    productoDao.eliminacionLogica(descProdEliminar);
                    System.out.println("Producto eliminado lógicamente.");
                    break;

                case 7:
                    String dniModificar = InputUtil.leerTexto("Ingrese DNI del cliente a modificar:");
                    Cliente clienteMod = clienteDao.buscarPorDni(dniModificar);
                    if (clienteMod != null) {
                        clienteMod.setNombre(InputUtil.leerTexto("Nuevo nombre:"));
                        clienteMod.setDomicilio(InputUtil.leerTexto("Nueva domicilio:"));
                        clienteDao.modificarCliente(clienteMod);
                        System.out.println("Cliente modificado.");
                    } else {
                        System.out.println("Cliente no encontrado.");
                    }
                    break;

                case 8:
                    Long idProdModificar = InputUtil.leerLong("Ingrese ID del producto a modificar:");
                    Producto prodMod = productoDao.buscarPorId(idProdModificar);
                    if (prodMod != null && prodMod.isEstado()) {
                        double nuevoPrecio = InputUtil.leerDouble("Nuevo precio unitario:");
                        productoDao.modificarPrecio(idProdModificar, nuevoPrecio);
                        System.out.println("Producto modificado.");
                    } else {
                            System.out.println("Producto no encontrado o está inactivo.");
                    }
                break;


                case 10:
                    List<Factura> facturas = facturaDao.obtenerFacturas();
                    facturas.forEach(System.out::println);
                    break;

                case 11:
                    List<Cliente> clientes = clienteDao.obtenerClientes();
                    clientes.forEach(System.out::println);
                    break;

                case 12:
                    List<Factura> facturasAltas = facturaDao.obtenerFacturasConMontoMayorA(500000);
                    facturasAltas.forEach(System.out::println);
                    break;

                case 0:
                    System.out.println("¡Hasta luego!");
                    break;

                default:
                    System.out.println("Opción inválida.");
                    break;
            }
        } while (opcion != 0);
    }
}
