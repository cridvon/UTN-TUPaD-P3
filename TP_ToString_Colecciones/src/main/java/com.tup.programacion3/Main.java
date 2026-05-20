package com.tup.programacion3;

import com.tup.programacion3.enums.Estado;
import com.tup.programacion3.enums.FormaPago;
import com.tup.programacion3.enums.Rol;
import com.tup.programacion3.entities.Categoria;
import com.tup.programacion3.entities.Pedido;
import com.tup.programacion3.entities.Producto;
import com.tup.programacion3.entities.Usuario;

import java.util.HashSet;
import java.util.Set;

public class Main {

    public static void main(String[] args) {

        // ====================================================
        // PUNTO 3 - INSTANCIACIÓN
        // ====================================================

        // --- 3.c) 3 Categorías ---
        Categoria catBebidas = new Categoria(1L, "Bebidas", "Bebidas frías y calientes");
        Categoria catComidas = new Categoria(2L, "Comidas", "Platos principales");
        Categoria catPostres = new Categoria(3L, "Postres", "Postres y dulces");

        // --- 3.d) 10 Productos ---
        Producto p1 = new Producto(1L, "Coca Cola", 1200.0, "Gaseosa 500ml", 50, "coca.jpg", true);
        Producto p2 = new Producto(2L, "Agua Mineral", 800.0, "Agua sin gas 500ml", 40, "agua.jpg", true);
        Producto p3 = new Producto(3L, "Café", 1500.0, "Café americano", 30, "cafe.jpg", true);
        Producto p4 = new Producto(4L, "Hamburguesa", 4500.0, "Hamburguesa completa", 20, "hamb.jpg", true);
        Producto p5 = new Producto(5L, "Pizza", 6000.0, "Pizza muzzarella", 15, "pizza.jpg", true);
        Producto p6 = new Producto(6L, "Empanada", 900.0, "Empanada de carne", 100, "emp.jpg", true);
        Producto p7 = new Producto(7L, "Flan", 1800.0, "Flan con dulce de leche", 25, "flan.jpg", true);
        Producto p8 = new Producto(8L, "Helado", 2200.0, "Helado de chocolate", 18, "helado.jpg", true);
        Producto p9 = new Producto(9L, "Tarta", 2500.0, "Tarta de jamón y queso", 12, "tarta.jpg", true);
        Producto p10 = new Producto(10L, "Cerveza", 1600.0, "Cerveza artesanal 500ml", 35, "cerv.jpg", true);

        // Asignar categorías a productos (relación 1..m)
        p1.addCategoria(catBebidas);
        p2.addCategoria(catBebidas);
        p3.addCategoria(catBebidas);
        p4.addCategoria(catComidas);
        p5.addCategoria(catComidas);
        p6.addCategoria(catComidas);
        p7.addCategoria(catPostres);
        p8.addCategoria(catPostres);
        p9.addCategoria(catComidas);
        p10.addCategoria(catBebidas);

        // Colección de productos cargados (Set)
        Set<Producto> productos = new HashSet<>();
        productos.add(p1);
        productos.add(p2);
        productos.add(p3);
        productos.add(p4);
        productos.add(p5);
        productos.add(p6);
        productos.add(p7);
        productos.add(p8);
        productos.add(p9);
        productos.add(p10);

        // --- 3.a) 2 Usuarios ---
        Usuario user1 = new Usuario(1L, "Cristian", "Pérez", "cristian@mail.com",
                "3411111111", "1234", Rol.ADMIN);
        Usuario user2 = new Usuario(2L, "Lucía", "Gómez", "lucia@mail.com",
                "3412222222", "abcd", Rol.USUARIO);

        // --- 3.b) 3 Pedidos (con al menos 2 detalles cada uno) ---
        Pedido pedido1 = new Pedido(1L, Estado.CONFIRMADO, FormaPago.TARJETA);
        pedido1.addDetallePedido(2, p1);   // 2 Coca Cola
        pedido1.addDetallePedido(1, p4);   // 1 Hamburguesa

        Pedido pedido2 = new Pedido(2L, Estado.PENDIENTE, FormaPago.EFECTIVO);
        pedido2.addDetallePedido(3, p6);   // 3 Empanadas
        pedido2.addDetallePedido(1, p5);   // 1 Pizza

        Pedido pedido3 = new Pedido(3L, Estado.TERMINADO, FormaPago.TRANSFERENCIA);
        pedido3.addDetallePedido(2, p7);   // 2 Flanes
        pedido3.addDetallePedido(1, p3);   // 1 Café

        // user1 tendrá 2 pedidos, user2 tendrá 1 -> user1 es el de más pedidos
        user1.addPedido(pedido1);
        user1.addPedido(pedido2);
        user2.addPedido(pedido3);

        Set<Usuario> usuarios = new HashSet<>();
        usuarios.add(user1);
        usuarios.add(user2);

        // ====================================================
        // PUNTO 4 - MOSTRAR POR CONSOLA
        // ====================================================

        System.out.println("====================================================");
        System.out.println("PUNTO 4.a - UN PRODUCTO");
        System.out.println("====================================================");
        System.out.println(p4);

        System.out.println();
        System.out.println("====================================================");
        System.out.println("PUNTO 4.b - LISTADO COMPLETO DE PRODUCTOS");
        System.out.println("====================================================");
        for (Producto prod : productos) {
            System.out.println(prod);
        }

        System.out.println();
        System.out.println("====================================================");
        System.out.println("PUNTO 4.c - PEDIDOS DEL USUARIO CON MÁS PEDIDOS");
        System.out.println("====================================================");
        Usuario usuarioConMasPedidos = obtenerUsuarioConMasPedidos(usuarios);
        System.out.println("Usuario: " + usuarioConMasPedidos.getNombre()
                + " " + usuarioConMasPedidos.getApellido()
                + " (" + usuarioConMasPedidos.getPedidos().size() + " pedidos)");
        for (Pedido ped : usuarioConMasPedidos.getPedidos()) {
            System.out.println(ped);
        }

        // ====================================================
        // PUNTO 5 - PRODUCTO DUPLICADO Y COMPARACIÓN
        // ====================================================

        System.out.println();
        System.out.println("====================================================");
        System.out.println("PUNTO 5 - PRODUCTO DUPLICADO (equals por 'nombre')");
        System.out.println("====================================================");

        // Producto nuevo con el MISMO nombre que p4 ("Hamburguesa").
        // Aunque tiene otro id, precio, stock, etc., equals() compara por nombre,
        // por lo tanto este objeto es "igual" a p4 según el contrato.
        Producto productoDuplicado = new Producto(99L, "Hamburguesa", 9999.0,
                "Otra hamburguesa distinta", 5, "otra.jpg", false);

        System.out.println("Producto original  : " + p4);
        System.out.println("Producto duplicado : " + productoDuplicado);
        System.out.println();

        System.out.println("¿productoDuplicado.equals(p4)? -> "
                + productoDuplicado.equals(p4));
        System.out.println("¿Mismo hashCode? -> "
                + (productoDuplicado.hashCode() == p4.hashCode()));
        System.out.println();

        // Comparación contra toda la colección
        System.out.println("--- Comparación contra la colección de productos ---");
        boolean encontrado = false;
        for (Producto prod : productos) {
            if (prod.equals(productoDuplicado)) {
                System.out.println("COINCIDENCIA encontrada con: " + prod.getNombre()
                        + " (id=" + prod.getId() + ")");
                encontrado = true;
            }
        }
        if (!encontrado) {
            System.out.println("No se encontró ninguna coincidencia.");
        }

        System.out.println();
        System.out.println("--- Intento de agregar el duplicado al Set ---");
        System.out.println("Tamaño del Set ANTES  : " + productos.size());
        boolean seAgrego = productos.add(productoDuplicado);
        System.out.println("¿Se agregó al Set?    : " + seAgrego
                + "  (false = el Set lo rechazó por ser duplicado)");
        System.out.println("Tamaño del Set DESPUÉS: " + productos.size());
        System.out.println();
        System.out.println("Conclusión: el Set usa equals()/hashCode() para garantizar");
        System.out.println("unicidad. Como 'Hamburguesa' ya existía, NO se duplicó.");
    }

    /**
     * Recorre la colección de usuarios y devuelve el que tenga más pedidos.
     */
    private static Usuario obtenerUsuarioConMasPedidos(Set<Usuario> usuarios) {
        Usuario resultado = null;
        int maxPedidos = -1;
        for (Usuario u : usuarios) {
            if (u.getPedidos().size() > maxPedidos) {
                maxPedidos = u.getPedidos().size();
                resultado = u;
            }
        }
        return resultado;
    }
}
