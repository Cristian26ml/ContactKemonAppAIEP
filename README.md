# 📇 ContactkemonappAiep

Aplicación Android para la gestión de contactos, desarrollada en Android Studio. Permite agregar, editar, buscar y visualizar contactos de forma eficiente, utilizando componentes modernos como Room y RecyclerView.

## 🚀 Características principales

- Visualización de contactos en lista con diseño personalizado.
- Búsqueda en tiempo real mediante barra de búsqueda.
- Agregado y edición de contactos con validación de campos.
- Persistencia de datos local usando Room (SQLite).
- Marcado de contactos como favoritos.

## 🧱 Arquitectura del proyecto

El proyecto está organizado en paquetes que siguen buenas prácticas de separación de responsabilidades:

## 📦 Clases destacadas

| Clase                  | Descripción                                                                 |
|------------------------|------------------------------------------------------------------------------|
| `MainActivity`         | Muestra la lista de contactos y permite buscar o agregar nuevos.             |
| `AddEditContactActivity` | Formulario para agregar o editar contactos.                              |
| `ContactoAdapter`      | Adaptador para el RecyclerView. Maneja la visualización y clics.            |
| `ContactoDao`          | Interfaz DAO con métodos para insertar, actualizar, eliminar y consultar.   |
| `AppDatabase`          | Clase abstracta que extiende RoomDatabase. Proporciona acceso al DAO.       |
| `Contacto`             | Entidad Room que representa un contacto con sus atributos.                  |

## 🖼️ Diseños visuales (`res/layout`)

- `activity_main.xml`: Pantalla principal con lista y barra de búsqueda.
- `activity_formulario.xml`: Formulario para agregar o editar contactos.
- `item_contacto.xml`: Diseño individual de cada contacto en la lista.

## 🛠️ Tecnologías utilizadas

- **Android Studio**
- **Room (Jetpack)**
- **RecyclerView**
- **Java**
- **XML para layouts**

## ✅ Validaciones implementadas

La aplicación incluye múltiples validaciones para garantizar que los datos ingresados por el usuario sean correctos, seguros y útiles. Estas validaciones se aplican tanto en el formulario como en la lógica interna de la app:

### 🧪 Validaciones en el formulario (`AddEditContactActivity`)

- **Campos obligatorios**: El nombre y el número de teléfono son requeridos. Si están vacíos, se muestra un mensaje de error y no se permite guardar.
- **Formato de teléfono**: Se valida que el número tenga una longitud mínima (por ejemplo, 8 dígitos) y que contenga solo caracteres numéricos.
- **Evitar duplicados**: Se verifica si ya existe un contacto con el mismo nombre y número antes de agregar uno nuevo.
- **Límites de caracteres**: Se restringe la cantidad máxima de caracteres en campos como nombre, teléfono y descripción para evitar desbordes visuales.
- **Confirmación visual**: Al guardar o editar un contacto exitosamente, se muestra un mensaje de confirmación (por ejemplo, Toast o Snackbar).

### 🔐 Validaciones internas y de seguridad

- **Control de favoritos**: Se evita que un contacto sea marcado como favorito más de una vez.
- **Actualización segura**: Antes de editar un contacto, se recuperan sus datos actuales para evitar sobrescrituras accidentales.
- **Eliminación con confirmación**: Al eliminar un contacto, se solicita confirmación del usuario para evitar borrados involuntarios.
- **Persistencia confiable**: Room maneja las operaciones de base de datos con transacciones seguras, evitando inconsistencias.

### 🎯 Validaciones en la búsqueda

- **Filtro en tiempo real**: La barra de búsqueda filtra los contactos mientras el usuario escribe, ignorando mayúsculas/minúsculas.
- **Resultados relevantes**: Se muestra solo lo que coincide con el nombre o número, evitando resultados vacíos o irrelevantes.

## 📲 Cómo ejecutar la app

1. Clona el repositorio:
   ```bash
   git clone [https://github.com/Cristian26ml/ContactKemonAppAIEP.git]
