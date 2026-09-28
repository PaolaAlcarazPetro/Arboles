import java.util.Scanner;

/**
 * Clase principal del programa Tree-Stock.
 *
 * Contiene el menú y permite al usuario
 * interactuar con el inventario.
 */
public class Main {

    /**
     * Punto de entrada del programa.
     *
     * Muestra el menú en un ciclo hasta que
     * el usuario elige la opción 0 (Salir).
     *
     * @param args Argumentos de línea de comandos (no se usan).
     */
    public static void main(String[] args) {

        // Lector para capturar lo que el usuario escribe en consola
        Scanner teclado = new Scanner(System.in);

        // Árbol donde se guardan todos los productos
        ArbolInventario inventario = new ArbolInventario();

        int opcion;

        // El menú se repite mientras la opción sea distinta de 0
        do {

            System.out.println("\n==============================");
            System.out.println("       TREE-STOCK");
            System.out.println("==============================");
            System.out.println("1. Registrar Producto");
            System.out.println("2. Mostrar Inventario");
            System.out.println("3. Buscar Producto");
            System.out.println("0. Salir");
            System.out.println("==============================");

            System.out.print("Seleccione una opción: ");
            opcion = teclado.nextInt();

            switch (opcion) {

                // Registrar un nuevo producto en el árbol
                case 1:

                    System.out.print("\nIngrese el ID del producto: ");
                    int id = teclado.nextInt();

                    // Limpia el salto de línea que deja nextInt()
                    // para que nextLine() lea correctamente el nombre
                    teclado.nextLine();

                    System.out.print("Ingrese el nombre del producto: ");
                    String nombre = teclado.nextLine();

                    // insertar() devuelve false si el ID ya estaba registrado
                    if (inventario.insertar(id, nombre)) {
                        System.out.println("Producto registrado correctamente.");
                    } else {
                        System.out.println("El ID ya existe. El producto no se registró.");
                    }

                    break;

                // Mostrar todos los productos ordenados por ID
                case 2:

                    System.out.println("\n--- INVENTARIO ---");

                    inventario.recorridoInorden();

                    break;

                // Buscar un producto por su ID
                case 3:

                    System.out.print("\nIngrese el ID que desea buscar: ");
                    int idBuscar = teclado.nextInt();

                    Producto producto = inventario.buscar(idBuscar);

                    // buscar() devuelve null cuando el ID no existe
                    if (producto != null) {

                        System.out.println("Producto encontrado.");
                        System.out.println(
                            "ID: " + producto.id +
                            " | Nombre: " + producto.nombre
                        );

                    } else {

                        System.out.println("El producto no existe.");

                    }

                    break;

                // Terminar el programa
                case 0:

                    System.out.println("\nPrograma finalizado.");

                    break;

                // Cualquier número que no esté en el menú
                default:

                    System.out.println("\nOpción inválida.");

            }

        } while (opcion != 0);

        // Se cierra el Scanner para liberar el recurso
        teclado.close();
    }
}
