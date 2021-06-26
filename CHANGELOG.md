# ChangeLog
Registros de cambios realizados en el proyecto Sistema de Control de Estudios.

## [0.0.1] - 2021-06-26
### Añadido
- CHANGELOG.md para registro de cambios.
- Versionado tradicional (mayor.menor.micro[-fase]). Version inicial 0.0.1.
- Método saveChanges en VentanaPeriodo.java.

### Modificado
- Sustituido JTextBox txtFechaNacimiento por JDateChooser dateFechaNacimiento en VentanaEstudiantes.java.
- Actualizado identificador de un procedimiento almacenado en VentanaEstudiantes.java.
- Eliminación de comentarios innecesarios en VentanaEstudiantes.java.
- Actualizado parámetros de procedimientos almacenados invocados en VentanaPeriodos.java.
- Optimización de código en VentanaPeriodos.java.
- Eliminación de comentarios innecesarios en VentanaPeriodos.java.
- Renombrado el archivo scedb-v6.sql a scedb.sql.
- Cambio de tipo de VARCHAR a DATE en fecha_nacimiento del procedimiento insert_estudiante de scedb.sql.
- Cambio de tipo de VARCHAR a DATE en fecha_nacimiento del procedimiento insert_persona de scedb.sql.
- Cambio de tipo de VARCHAR a DATE en fecha_inicial del procedmiento insert_periodo de scedb.sql.
- Cambio de tipo de VARCHAR a DATE en fecha_final del procedmiento insert_periodo de scedb.sql.
- Cambio de tipo de VARCHAR a DATE en fecha_nacimiento del procedimiento update_estudiante de scedb.sql.
- Cambio de tipo de VARCHAR a DATE en fecha_inicial en update_periodo de scedb.sql.
- Cambio de tipo de VARCHAR a DATE en fecha_final en update_periodo de scedb.sql.
- Aplicación de formato de fecha "dd/MM/yyyy" al JComboBox cbxFechaAprobacion en VentanaPlanesEstudio.java
- Optimización de código en VentanaPlanesEstudio.java.

### Corregido
- Error en el método getTelefonoSinFormato de la clase Persona.

### Eliminado
- Método addPeriodo por ya no ser necesario en VentanaPeriodos.java.
- Método modifyPeriodo por ya no ser necesario en VentanaPeriodos.java.