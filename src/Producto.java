/**
 * Clase Producto.
 *
 * Representa un nodo del árbol de inventario.
 * Cada producto tiene un ID, un nombre
 * y dos referencias: izquierda y derecha.
 */
public class Producto {

    /** Identificador único del producto (clave del árbol). */
    int id;

    /** Nombre descriptivo del producto. */
    String nombre;

    /** Hijo izquierdo: productos con ID menor a este. */
    Producto izquierdo;

    /** Hijo derecho: productos con ID mayor a este. */
    Producto derecho;

    /**
     * Constructor del producto.
     *
     * @param id Identificador del producto.
     * @param nombre Nombre del producto.
     */
    public Producto(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;

        // Un nodo nuevo siempre empieza como hoja (sin hijos)
        izquierdo = null;
        derecho = null;
    }
}
