# ChangeLog
Registros de cambios realizados en el proyecto Sistema de Control de Estudios.
## [0.0.2] - 2021-08-26
### Añadido
- Generación de reportes en la clase VentanaPlanesEstudio.
- Método isValidFieldsCheckFind en clase VentanaEstudiantes.
- Método isValidFieldsSave en clase VentanaEstudiantes.
- Plantilla de reporte plan_estudio.jrxml.
- Tabla grados en scedb.sql.
- Tabla resoluciones en scedb.sql.
- Procedimiento insert_grado en scedb.sql.
- Procedimiento get_grado en scedb.sql.
- Procedimiento get_grado_id en scedb.sql.
- Procedimiento insert_resolucion en scedb.sql.
- Procedimiento get_resolucion_id en scedb.sql.
- Procedimiento get_prelaciones en scedb.sql.
- Procedimimento update_persona en scedb.sql.
- Procedimimento get_docente_id en scedb.sql.
- Procedimimento insert_docente en scedb.sql.
- Procedimimento update_docente en scedb.sql.
- Clase Resolucion.
- Clase Controls.
- Clase Consultas.
- Clase Reportes.
- Clase Docente.

### Modificado
- README.md.
- Tabla unidades en scedb.sql.
- Vista view_modulos_ultimas_resoluciones en scedb.sql.
- Tabla planes_estudio en scedb.sql.
- Tabla planes_estudio_modulos en scedb.sql.
- Procedimiento insert_carrera en scedb.sql.
- Procedimiento select_periodos en scedb.sql.
- Procedimiento select_plan_estudio en scedb.sql.
- Procedimiento insert_plan_estudio en scedb.sql.
- Procedimiento insert_plan_estudio_modulo en scedb.sql.
- Procedimiento select_plan_estudio_fechas_aprobacion en scedb.sql.
- Renombrado select_plan_estudio_fechas_aprobacion por select_plan_estudio_resoluciones en scedb.sql.
- Procedimiento select_planes_estudio_niveles en scedb.sql.
- Procedimiento get_plan_estudio_id en scedb.sql.
- Procedimiento get_unidad_id en scedb.sql.
- Procedimiento update_estudiante en scedb.sql.
- Constructor en la clase VentanaPlanesEstudio.
- Método fillComboBoxCarrera en la clase VentanaPlanesEstudio.
- Método fillComboBoxNiveles en la clase VentanaPlanesEstudio.
- Método fillComboBoxFechaAprobacion en la clase VentanaPlanesEstudio.
- Método fillTablePlanEstudio en la clase VentanaPlanesEstudio.
- Método fillComboBoxFechaAprobacion renombrado a fillComboBoxResoluciones en la clase VentanaPlanesEstudio.
- Modificadores de acceso de los atributos de la clase Personas.

### Corregido
- Método executeStoredProcedure de la clase ConnectionDB.
- Método executeStoredProcedureWithResultSet de la clase ConnectionDB.

### Eliminado
- Método fillComboBox de la clase VentanaEstudiantes.
- Método fillList de la clase VentanaEstudiantes.
- Método updateComboBoxTooltip de la clase VentanaEstudiantes.

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
