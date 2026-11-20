# Sistema de Gestión de Biblioteca Universitaria

Proyecto Java de consola según `proyecto-final-biblioteca.md`.

## Estructura
- `src/com/biblioteca/modelo`: clases del dominio, validaciones y colecciones.
- `src/com/biblioteca/excepciones`: excepciones personalizadas.
- `src/com/biblioteca/app/App.java`: menú interactivo.
- `diagrama-biblioteca.puml`: diagrama PlantUML proporcionado en las instrucciones.

## Compilar y ejecutar

Desde la raíz del proyecto, en Linux/macOS/Git Bash:

```bash
javac -encoding UTF-8 -d out $(find src -name "*.java")
java -cp out com.biblioteca.app.App
```

En Windows PowerShell:

```powershell
$archivos = Get-ChildItem -Recurse -Filter *.java src | Select-Object -ExpandProperty FullName
javac -encoding UTF-8 -d out $archivos
java -cp out com.biblioteca.app.App
```

## Flujo de uso
1. Registrar un libro o recurso digital (opción 1).
2. Ubicarlo en una sección (opción 3).
3. Registrar préstamo con un bibliotecario real (opción 4).
4. Ver préstamos (opción 6), recursos y reportes (opciones 2, 5, 8 y 9).

## Decisión de diseño
Para registrar un préstamo, el recurso **debe estar ubicado en una sección**: `Biblioteca.buscarRecurso()` no busca en la lista temporal `recursosRegistrados`. Esta lista solo permite consultar lo registrado antes de ubicarlo.

## Reglas adicionales
- Se rechazan códigos duplicados al registrar recursos y no se puede ubicar el mismo recurso en más de una sección.
- Las secciones se crean desde `Biblioteca`, tanto al iniciar como por la opción 10.
- Los tamaños de los recursos digitales se expresan en MB enteros según el enunciado.
- Los recursos se mantienen en memoria: al cerrar el programa se pierden los datos (no se solicita persistencia).
- La baja de una sección no borra el recurso de la lista de registro temporal.

## Conceptos de POO
- **Abstracción:** `Personal`, `Recurso`.
- **Herencia:** `Bibliotecario`/`Auxiliar`, `Libro`/`RecursoDigital`.
- **Sobrecarga:** dos métodos `Bibliotecario.registrarPrestamo(...)`.
- **Sobreescritura:** `generarReporte()` y `obtenerInfo()`.
- **Composición:** `Biblioteca` crea sus `Seccion`.
- **Agregación:** `Seccion` almacena objetos `Recurso` sin crearlos.
- **Asociación:** `Prestamo` tiene referencias a `Recurso` y `Bibliotecario`.
