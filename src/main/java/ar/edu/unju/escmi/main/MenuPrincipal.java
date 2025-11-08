package ar.edu.unju.escmi.main;

import ar.edu.unju.escmi.dao.imp.ClienteDaoImp;
import ar.edu.unju.escmi.dao.imp.ProductoDaoImp;
import ar.edu.unju.escmi.dao.imp.FacturaDaoImp;
import ar.edu.unju.escmi.entities.Cliente;
import ar.edu.unju.escmi.entities.Producto;
import ar.edu.unju.escmi.entities.Factura;
import ar.edu.unju.escmi.utils.InputUtil;
import ar.edu.unju.escmi.entities.DetalleFactura;

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

            opcion = InputUtil.inputInt("Seleccione una opción:");

            try {
                switch (opcion) {
                    case 1:
                        String dniNuevo = InputUtil.inputString("Ingrese DNI del cliente:");
                        if (clienteDao.buscarPorDni(dniNuevo) != null) {
                            throw new Exception("Ya existe un cliente con ese DNI.");
                        }

                        Cliente nuevoCliente = new Cliente();
                        nuevoCliente.setDni(Integer.parseInt(dniNuevo));
                        nuevoCliente.setNombre(InputUtil.inputString("Ingrese nombre del cliente:"));
                        nuevoCliente.setDomicilio(InputUtil.inputString("Ingrese domicilio del cliente:"));
                        clienteDao.guardarCliente(nuevoCliente);
                        System.out.println("Cliente guardado exitosamente.");
                        break;

                    case 2:
                        String descNuevo = InputUtil.inputString("Ingrese descripción del producto:");
                        if (productoDao.existeDescripcion(descNuevo)) {
                            throw new Exception("Ya existe un producto con esa descripción.");
                        }

                        Producto nuevoProducto = new Producto();
                        nuevoProducto.setDescripcion(descNuevo);
                        nuevoProducto.setPrecioUnitario(InputUtil.inputDouble("Ingrese precio unitario:"));
                        productoDao.guardarProducto(nuevoProducto);
                        System.out.println("Producto guardado exitosamente.");
                        break;

                    case 3: {
                    String dniFactura = InputUtil.inputString("Ingrese DNI del cliente:");
                    Cliente clienteFactura = clienteDao.buscarPorDni(dniFactura);
                    if (clienteFactura == null) {
                        System.out.println("Cliente no encontrado.");
                        break;
                        }

                    Factura factura = new Factura();
                    factura.setCliente(clienteFactura);

                String respuesta = "";
                do { 
                    Long idProd = InputUtil.inputLong("Ingrese ID del producto:");
                    Producto producto = productoDao.buscarPorId(idProd);

                    if (producto == null || !producto.isEstado()) {
                        System.out.println("El producto no existe o está inactivo.");
                        continue;
                }

                    int cantidad = InputUtil.inputInt("Ingrese cantidad:");
                    factura.agregarDetalle(producto, cantidad);

                    respuesta = InputUtil.inputString("¿Desea agregar otro producto? (si/no):");
                } while (respuesta.equalsIgnoreCase("si"));


                    if (factura.getDetalles() != null && !factura.getDetalles().isEmpty()) {
                        factura.calcularTotal(); 
                        facturaDao.guardarFactura(factura);
                        System.out.println("✅ Factura registrada exitosamente.");
                    } else {
                        System.out.println("⚠️ Compra no realizada.");
                    }

                    break;
                }


                    case 4:
                        int numeroFactura = InputUtil.inputInt("Ingrese número de factura a buscar:");
                        Factura f = facturaDao.obtenerFacturaPorId((long) numeroFactura);
                        if (f != null) {
                            System.out.println("Factura encontrada:\n" + f);
                        } else {
                            System.out.println("Factura no encontrada.");
                        }
                        break;

                    case 5:
                    int numFacturaEliminar = InputUtil.inputInt("Ingrese número de factura a eliminar:");
                    Factura facturaEliminar = facturaDao.obtenerFacturaPorId((long) numFacturaEliminar);

                    if (facturaEliminar != null && facturaEliminar.isEstado()) {
                        facturaDao.borrarFactura(facturaEliminar);
                        System.out.println("Factura eliminada lógicamente.");
                    } else {
                        System.out.println("Factura no encontrada o ya está inactiva.");
                    }
                    break;

                    case 6:
                    case 9:
                        Long idProdEliminar = InputUtil.inputLong("Ingrese ID del producto a eliminar:");
                        Producto productoAEliminar = productoDao.buscarPorId(idProdEliminar);

                        if (productoAEliminar != null && productoAEliminar.isEstado()) {
                            productoDao.borrarProducto(productoAEliminar);
                        } else {
                            System.out.println("Producto no encontrado o ya está inactivo.");
                        }
                    break;


                    case 7:
                        String dniModificar = InputUtil.inputString("Ingrese DNI del cliente a modificar:");
                        Cliente clienteMod = clienteDao.buscarPorDni(dniModificar);
                        if (clienteMod != null) {
                            clienteMod.setNombre(InputUtil.inputString("Nuevo nombre:"));
                            clienteMod.setDomicilio(InputUtil.inputString("Nueva domicilio:"));
                            clienteDao.modificarCliente(clienteMod);
                            System.out.println("Cliente modificado.");
                        } else {
                            throw new Exception("Cliente no encontrado.");
                        }
                        break;

                    case 8:
                        Long idProdModificar = InputUtil.inputLong("Ingrese ID del producto a modificar:");
                        Producto prodMod = productoDao.buscarPorId(idProdModificar);
                        if (prodMod != null && prodMod.isEstado()) {
                            double nuevoPrecio = InputUtil.inputDouble("Nuevo precio unitario:");
                            productoDao.modificarPrecio(idProdModificar, nuevoPrecio);
                            System.out.println("Producto modificado.");
                        } else {
                            throw new Exception("Producto no encontrado o está inactivo.");
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

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

        } while (opcion != 0);
    }
}
