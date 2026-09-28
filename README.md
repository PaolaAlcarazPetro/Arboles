# Tree-Stock

Tree-Stock es un programa de consola en Java para administrar un inventario de productos. Los productos se guardan en un **Árbol Binario de Búsqueda (ABB)** y se ordenan por su ID.

Con el programa puedes:

1. Registrar un producto (ID y nombre).
2. Mostrar el inventario ordenado por ID.
3. Buscar un producto por su ID.

## Estructura del proyecto

| Archivo | Qué hace |
|---|---|
| [src/Producto.java](src/Producto.java) | Es el **nodo** del árbol. Guarda el `id`, el `nombre` y dos referencias: `izquierdo` y `derecho`. |
| [src/ArbolInventario.java](src/ArbolInventario.java) | Tiene la **raíz** del árbol y las operaciones recursivas para insertar, recorrer y buscar. |
| [src/Main.java](src/Main.java) | Muestra el menú y atiende lo que el usuario elige. |

---

## ¿Qué es un Árbol Binario de Búsqueda (ABB)?

Un **árbol** es una estructura de datos formada por **nodos** unidos entre sí. El nodo de arriba se llama **raíz**. Cada nodo puede tener hijos, y un nodo sin hijos se llama **hoja**.

El árbol es **binario** porque cada nodo tiene **como máximo dos hijos**: uno a la izquierda y otro a la derecha.

Es **de búsqueda** porque todos sus nodos cumplen esta regla de orden:

> Para cualquier nodo:
> - todos los nodos de su **subárbol izquierdo** tienen una clave **menor**;
> - todos los nodos de su **subárbol derecho** tienen una clave **mayor**.

En Tree-Stock la clave es el **ID del producto**, y no se permiten IDs repetidos.

### Ejemplo

Si registramos productos con los IDs `50, 30, 70, 20, 40, 60, 80`, en ese orden, el árbol queda así:

```
              50
            /    \
          30      70
         /  \    /  \
       20   40  60   80
```

- `50` es la raíz porque fue el primero en registrarse.
- `30` es menor que 50, así que va a la izquierda. `70` es mayor, así que va a la derecha.
- `40` es menor que 50, así que baja a la izquierda. Luego es mayor que 30, así que queda a la derecha de 30.

### ¿Por qué usar un ABB?

En cada comparación sabemos si hay que seguir por la izquierda o por la derecha, así que **descartamos una rama completa del árbol**. En el ejemplo, para encontrar el `60` solo se revisan 3 nodos (`50 → 70 → 60`) y no los 7. En una lista, en cambio, podría ser necesario revisar todos los elementos.

---

## ¿Qué es la recursividad?

Un método es **recursivo** cuando **se llama a sí mismo** para resolver una versión más pequeña del mismo problema. Todo método recursivo tiene dos partes:

1. **Caso base:** la situación en la que el método se detiene y ya no se vuelve a llamar.
2. **Caso recursivo:** el método se llama otra vez con un problema más pequeño.

La recursividad encaja muy bien con los árboles, porque **cada hijo de un nodo es, a su vez, la raíz de un árbol más pequeño** (un subárbol). Lo que sirve para el árbol completo sirve también para cada subárbol.

---

## Cómo se aplica la recursividad en el inventario

Las tres operaciones de [ArbolInventario.java](src/ArbolInventario.java) tienen la misma forma: un **método público** que empieza desde la raíz y un **método privado recursivo** que hace el trabajo.

| Método público | Método recursivo |
|---|---|
| `insertar(id, nombre)` | `insertarRecursivo(actual, nuevo)` |
| `recorridoInorden()` | `inorden(actual)` |
| `buscar(id)` | `buscarRecursivo(actual, id)` |

### 1. Insertar un producto

`insertar` crea el producto. Si el árbol está vacío, el producto se convierte en la raíz. Si no, se llama a `insertarRecursivo` empezando desde la raíz.

```java
private boolean insertarRecursivo(Producto actual, Producto nuevo) {
    if (nuevo.id < actual.id) {
        if (actual.izquierdo == null) {
            actual.izquierdo = nuevo;                            // caso base: hay espacio
            return true;
        } else {
            return insertarRecursivo(actual.izquierdo, nuevo);   // caso recursivo
        }
    } else if (nuevo.id > actual.id) {
        if (actual.derecho == null) {
            actual.derecho = nuevo;                              // caso base: hay espacio
            return true;
        } else {
            return insertarRecursivo(actual.derecho, nuevo);     // caso recursivo
        }
    } else {
        return false;                                            // caso base: ID repetido
    }
}
```

- **Casos base:**
  - Encontramos un espacio vacío (`null`) donde colocar el producto.
  - El ID ya existe. En ese caso se devuelve `false` y `Main` muestra el mensaje "El ID ya existe".
- **Caso recursivo:** el lugar todavía está ocupado, así que el método se llama otra vez con el hijo izquierdo o el derecho y baja un nivel en el árbol.

**Ejemplo:** insertar el ID `65` en el árbol del ejemplo.

```
insertarRecursivo(50, 65)  → 65 > 50, baja a la derecha
insertarRecursivo(70, 65)  → 65 < 70, baja a la izquierda
insertarRecursivo(60, 65)  → 65 > 60, el derecho de 60 está vacío → se inserta ahí
```

### 2. Mostrar el inventario (recorrido inorden)

El recorrido **inorden** visita los nodos en este orden:

> **Izquierda → Nodo actual → Derecha**

```java
private void inorden(Producto actual) {
    if (actual != null) {                 // caso base: si es null, no hace nada
        inorden(actual.izquierdo);        // 1. recorre todo el subárbol izquierdo
        System.out.println(...);          // 2. muestra el producto actual
        inorden(actual.derecho);          // 3. recorre todo el subárbol derecho
    }
}
```

- **Caso base:** el nodo es `null`, es decir, se llegó al final de una rama.
- **Caso recursivo:** el método se llama **dos veces**, una para cada hijo.

Como en un ABB los nodos de la izquierda siempre son menores y los de la derecha mayores, este recorrido **muestra los productos ordenados por ID de menor a mayor**, sin necesidad de ordenarlos aparte. En el ejemplo imprime:

```
20, 30, 40, 50, 60, 70, 80
```

### 3. Buscar un producto

```java
private Producto buscarRecursivo(Producto actual, int id) {
    if (actual == null) return null;          // caso base: no existe
    if (actual.id == id) return actual;       // caso base: encontrado
    if (id < actual.id)
        return buscarRecursivo(actual.izquierdo, id);   // caso recursivo
    else
        return buscarRecursivo(actual.derecho, id);     // caso recursivo
}
```

- **Casos base:**
  - Se encuentra el producto y se devuelve.
  - Se llega a un `null`, lo que significa que el producto no existe.
- **Caso recursivo:** se compara el ID con el del nodo actual y el método se llama otra vez **solo con la mitad que puede contenerlo**.

**Ejemplo:** buscar el ID `40`.

```
buscarRecursivo(50, 40)  → 40 < 50, busca a la izquierda
buscarRecursivo(30, 40)  → 40 > 30, busca a la derecha
buscarRecursivo(40, 40)  → ¡encontrado!
```

**Ejemplo:** buscar el ID `45`, que no existe.

```
buscarRecursivo(50, 45)   → izquierda
buscarRecursivo(30, 45)   → derecha
buscarRecursivo(40, 45)   → derecha
buscarRecursivo(null, 45) → devuelve null: "El producto no existe."
```

---

## Cómo ejecutar el programa

Desde la carpeta del proyecto:

```bash
javac -d out src/*.java
java -cp out Main
```

### Ejemplo de uso

```
==============================
       TREE-STOCK
==============================
1. Registrar Producto
2. Mostrar Inventario
3. Buscar Producto
0. Salir
==============================
Seleccione una opción: 1

Ingrese el ID del producto: 5
Ingrese el nombre del producto: Lapiz
Producto registrado correctamente.
```
## Rapo en Git:
https://github.com/PaolaAlcarazPetro/Arboles

