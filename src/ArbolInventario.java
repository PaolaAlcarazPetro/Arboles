/**
 * Clase ArbolInventario.
 *
 * Contiene la lógica para administrar
 * el árbol binario de búsqueda.
 *
 * Los productos se ordenan por su ID: los menores
 * quedan a la izquierda y los mayores a la derecha.
 */
public class ArbolInventario {

    /** Nodo raíz del árbol; es null cuando el inventario está vacío. */
    Producto raiz;

    /**
     * Constructor del árbol.
     *
     * Crea un inventario vacío.
     */
    public ArbolInventario() {
        raiz = null;
    }

    /**
     * Método para insertar un producto.
     *
     * La inserción se realiza de forma recursiva.
     *
     * @param id ID del producto.
     * @param nombre Nombre del producto.
     * @return true si se registró, false si el ID ya existía.
     */
    public boolean insertar(int id, String nombre) {

        Producto nuevo = new Producto(id, nombre);

        // Si el árbol está vacío, el nuevo producto se convierte en la raíz
        if (raiz == null) {
            raiz = nuevo;
            return true;
        } else {
            // Si no, se busca su posición empezando desde la raíz
            return insertarRecursivo(raiz, nuevo);
        }
    }

    /**
     * Método auxiliar recursivo para insertar.
     *
     * Si el ID es menor, el producto va a la izquierda.
     * Si el ID es mayor, el producto va a la derecha.
     * Si el ID es igual, no se inserta (no se permiten duplicados).
     *
     * @param actual Nodo que se está comparando en este paso.
     * @param nuevo Producto que se desea insertar.
     * @return true si se insertó, false si el ID ya existía.
     */
    private boolean insertarRecursivo(Producto actual, Producto nuevo) {

        if (nuevo.id < actual.id) {

            // Si hay espacio a la izquierda, se coloca ahí;
            // si no, se sigue bajando por el subárbol izquierdo
            if (actual.izquierdo == null) {
                actual.izquierdo = nuevo;
                return true;
            } else {
                return insertarRecursivo(actual.izquierdo, nuevo);
            }

        } else if (nuevo.id > actual.id) {

            // Si hay espacio a la derecha, se coloca ahí;
            // si no, se sigue bajando por el subárbol derecho
            if (actual.derecho == null) {
                actual.derecho = nuevo;
                return true;
            } else {
                return insertarRecursivo(actual.derecho, nuevo);
            }

        } else {
            // IDs iguales: el producto ya está registrado, no se inserta
            return false;
        }
    }

    /**
     * Método para mostrar el inventario
     * utilizando un recorrido inorden.
     *
     * Como es un árbol binario de búsqueda, el recorrido
     * inorden muestra los productos ordenados por ID
     * de menor a mayor.
     */
    public void recorridoInorden() {

        if (raiz == null) {
            System.out.println("El inventario está vacío.");
        } else {
            inorden(raiz);
        }
    }

    /**
     * Recorrido inorden.
     *
     * Orden:
     * Izquierda -> Raíz -> Derecha
     *
     * @param actual Nodo desde el cual se realiza el recorrido.
     */
    private void inorden(Producto actual) {

        // Caso base: si el nodo es null, no hay nada que mostrar
        if (actual != null) {

            // 1. Visitar primero el subárbol izquierdo (IDs menores)
            inorden(actual.izquierdo);

            // 2. Mostrar el nodo actual
            System.out.println(
                "ID: " + actual.id +
                " | Nombre: " + actual.nombre
            );

            // 3. Visitar al final el subárbol derecho (IDs mayores)
            inorden(actual.derecho);
        }
    }

    /**
     * Método para buscar un producto por ID.
     *
     * @param id ID que se desea buscar.
     * @return El producto encontrado o null si no existe.
     */
    public Producto buscar(int id) {

        return buscarRecursivo(raiz, id);
    }

    /**
     * Método auxiliar recursivo para realizar la búsqueda.
     *
     * En cada paso descarta la mitad del árbol que no
     * puede contener el ID buscado.
     *
     * @param actual Nodo que se está revisando en este paso.
     * @param id ID que se desea buscar.
     * @return El producto encontrado o null si no existe.
     */
    private Producto buscarRecursivo(Producto actual, int id) {

        // Se llegó al final de una rama sin encontrar el ID
        if (actual == null) {
            return null;
        }

        // El nodo actual es el buscado
        if (actual.id == id) {
            return actual;
        }

        // Si el ID es menor se busca a la izquierda; si es mayor, a la derecha
        if (id < actual.id) {
            return buscarRecursivo(actual.izquierdo, id);
        } else {
            return buscarRecursivo(actual.derecho, id);
        }
    }
}
