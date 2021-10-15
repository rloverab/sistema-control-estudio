/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Esta clase define objetos que contienen información relacionada con datos
 * personales
 *
 * @author Roger Lovera
 */
public class Persona {        
    private int personaId;
    private String cedula;
    private String nombre1;
    private String nombre2;
    private String apellido1;
    private String apellido2;
    private String sexo;
    private Date fechaNacimiento;
    private String lugarNacimiento;
    private int edad;
    private String estadoCivil;
    private String etnia;
    private String estado;
    private String municipio;
    private String parroquia;
    private String direccion;
    private String telefonoLocal;
    private String telefonoMovil;
    private String correoElectronico;

    //Contructores
    /**
     * Este constructor permite definir los valores iniciales de los atributos
     * del objeto de clase Persona
     *
     * @param personaId Id del registro en la base de datos
     * @param cedula Cédula de identidad (Ej: V12345678)
     * @param nombre1 Primer nombre
     * @param nombre2 Segundo nombre
     * @param apellido1 Primer apellido
     * @param apellido2 Segundo apellido
     * @param sexo Sexo
     * @param fechaNacimiento Fecha de nacimiento
     * @param lugarNacimiento Lugar de nacimiento
     * @param edad Edad;
     * @param estadoCivil Estado civil
     * @param etnia Etnia
     * @param estado Estado donde reside
     * @param municipio Municipio en el estado donde reside
     * @param parroquia Parroquia del municipio donde reside
     * @param direccion Dirección de ubicación
     * @param telefonoLocal Teléfono local de contacto
     * @param telefonoMovil Teléfono movil de contacto
     * @param correoElectronico Correo electrónico de contacto
     */
    public Persona(
            int personaId, 
            String cedula, 
            String nombre1, 
            String nombre2, 
            String apellido1, 
            String apellido2, 
            String sexo, 
            Date fechaNacimiento, 
            String lugarNacimiento,
            int edad, 
            String estadoCivil, 
            String etnia, 
            String estado, 
            String municipio, 
            String parroquia, 
            String direccion, 
            String telefonoLocal, 
            String telefonoMovil, 
            String correoElectronico) {        
        this.personaId = personaId;
        this.cedula = cedula;
        this.nombre1 = nombre1;
        this.nombre2 = nombre2;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.sexo = sexo;
        this.fechaNacimiento = fechaNacimiento;
        this.lugarNacimiento = lugarNacimiento;
        this.edad = edad;
        this.estadoCivil = estadoCivil;
        this.etnia = etnia;
        this.estado = estado;
        this.municipio = municipio;
        this.parroquia = parroquia;
        this.direccion = direccion;
        this.telefonoLocal = telefonoLocal;
        this.telefonoMovil = telefonoMovil;
        this.correoElectronico = correoElectronico;
    }

    /**
     * Constructor que inicializa los atributos del objeto de clase Persona con
     * valores vacios
     */
    public Persona() {
        this(0, "", "", "", "", "", "", null, "", 0, "", "", "", "", "", "", "", "", "");
    }

    //Setters
    /**
     * Establece el id del registro en la base de datos
     *
     * @param persona_id Id del registro en la base de datos
     */
    public void setPersonaId(int persona_id) {
        this.personaId = persona_id;
    }

    /**
     * Establece la cédula de identidad
     *
     * @param cedula Cédula de identidad (Ej. V12345678)
     */
    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    /**
     * Establece el primer nombre
     *
     * @param nombre1 Primer nombre
     */
    public void setNombre1(String nombre1) {
        this.nombre1 = nombre1;
    }

    /**
     * Establece el segundo nombre
     *
     * @param nombre2 Segundo nombre
     */
    public void setNombre2(String nombre2) {
        this.nombre2 = nombre2;
    }

    /**
     * Establece el primer apellido
     *
     * @param apellido1 Primer apellido
     */
    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    /**
     * Establece el segundo apellido
     *
     * @param apellido2 Segundo apellido
     */
    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    /**
     * Establece el sexo de la persona
     *
     * @param sexo Sexo
     */
    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    /**
     * Establece la fecha de nacimiento
     *
     * @param fechaNacimiento Fecha de nacimiento
     */
    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    /**
     * Establece el lugar de nacimiento
     * 
     * @param lugarNacimiento Lugar de nacimiento
     */
    public void setLugarNacimiento(String lugarNacimiento) {
        this.lugarNacimiento = lugarNacimiento;
    }

    /**
     * Establece la edad de la persona
     *
     * @param edad Edad de la persona
     */
    public void setEdad(int edad) {
        this.edad = edad;
    }

    /**
     * Establece el estado civil
     *
     * @param estadoCivil Estado civil
     */
    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    /**
     * Establece la grupo étnico al que pertenece
     *
     * @param etnia Etnia
     */
    public void setEtnia(String etnia) {
        this.etnia = etnia;
    }

    /**
     * Establece el estado en donde reside
     *
     * @param estado Estado
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * Establece el municipio del estado en donde reside
     *
     * @param municipio Municipio
     */
    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    /**
     * Establece la parroquia del municipio en donde reside
     *
     * @param parroquia Parroquia
     */
    public void setParroquia(String parroquia) {
        this.parroquia = parroquia;
    }

    /**
     * Establece la dirección de ubicación de la residencia
     *
     * @param direccion Dirección de residencia
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * Establece el teléfono local de contacto
     *
     * @param telefonoLocal
     */
    public void setTelefonoLocal(String telefonoLocal) {
        this.telefonoLocal = telefonoLocal;
    }

    /**
     * Establece el teléfono móvil de contacto
     *
     * @param telefonoMovil Teléfono móvil
     */
    public void setTelefonoMovil(String telefonoMovil) {
        this.telefonoMovil = telefonoMovil;
    }

    /**
     * Establece el correo electrónico de contacto
     *
     * @param correoElectronico Correo electrónico
     */
    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    //Getters    
    /**
     * Obtiene el id del registro en la base de datos
     *
     * @return Id del registro en la base de datos
     */
    public int getPersonaId() {
        return personaId;
    }

    /**
     * Obtiene la cédula de identidad
     *
     * @return Cédula de identidad (Ej. V12345678)
     */
    public String getCedula() {
        return cedula;
    }

    /**
     * Obtiene la letra contenida en la cédula de identidad
     *
     * @return Letra
     */
    public String getCedulaLetra() {
        return cedula.substring(0, 1);
    }

    /**
     * Obtiene sólo el número de cédula de identidad
     *
     * @return Cadena de caracteres numéricos
     */
    public String getCedulaNumero() {
        return cedula.substring(1);
    }

    /**
     * Obtiene el primer nombre
     *
     * @return Primer nombre
     */
    public String getNombre1() {
        return nombre1;
    }

    /**
     * Obtiene el segundo nombre
     *
     * @return Segundo nombre
     */
    public String getNombre2() {
        return nombre2;
    }

    /**
     * Obtiene el primer apellido
     *
     * @return Primer apellido
     */
    public String getApellido1() {
        return apellido1;
    }

    /**
     * Obtiene el segundo apellido
     *
     * @return Segundo apellido
     */
    public String getApellido2() {
        return apellido2;
    }

    /**
     * Obtiene el sexo
     *
     * @return Sexo
     */
    public String getSexo() {
        return sexo;
    }

    /**
     * Obtiene la fecha de nacimiento
     *
     * @return Fecha de nacimiento
     */
    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    /**
     * Obtiene la fecha de nacimiento en el formato indicado en el parámetro
     *
     * @param formato Formato de fecha
     * @return Fecha de nacimiento en el formato indicado
     */
    public String getFechaNacimiento(String formato) {
        SimpleDateFormat sdf = new SimpleDateFormat(formato);

        return sdf.format(fechaNacimiento);
    }

    /**
     * Obtiene el lugar de nacimiento
     * 
     * @return Lugar de nacimiento
     */
    public String getLugarNacimiento() {
        return lugarNacimiento;
    }
    
    /**
     * Obtiene la edad
     *
     * @return Edad
     */
    public int getEdad() {
        return edad;
    }

    /**
     * Obtiene el estado civil
     *
     * @return Estado civil
     */
    public String getEstadoCivil() {
        return estadoCivil;
    }

    /**
     * Obtiene el grupo étnico
     *
     * @return Etnia
     */
    public String getEtnia() {
        return etnia;
    }

    /**
     * Obtiene el estado
     *
     * @return Estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Obtiene el municipio
     *
     * @return Municipio
     */
    public String getMunicipio() {
        return municipio;
    }

    /**
     * Obtiene la parroquia
     *
     * @return Parroquia
     */
    public String getParroquia() {
        return parroquia;
    }

    /**
     * Obtiene la dirección de residencia
     *
     * @return Dirección de residencia
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Obtiene el teléfono local de contacto
     *
     * @param conFormato Indica si se quiere con formato y solo números
     * @return Teléfono local
     */
    public String getTelefonoLocal(boolean conFormato) {
        String telefono;

        if (conFormato) {
            telefono = telefonoLocal;
        } else {
            telefono = getTelefonoSinFormato(telefonoLocal);
        }

        return telefono;
    }

    /**
     * Obtiene el teléfono móvil de contacto
     *
     * @param conFormato Indica si se quiere con formato y solo números
     * @return Teléfono móvil
     */
    public String getTelefonoMovil(boolean conFormato) {
        String telefono;

        if (conFormato) {
            telefono = telefonoMovil;
        } else {
            telefono = getTelefonoSinFormato(telefonoMovil);
        }

        return telefono;
    }

    /**
     * Obtiene el correo electrónico de contacto
     *
     * @return Correo electrónico
     */
    public String getCorreoElectronico() {
        return correoElectronico;
    }

    private String getTelefonoSinFormato(String telefono) {
        if(telefono != null){
            return telefono
                .replace("(", "")
                .replace(")", "")
                .replace("-", "")
                .replace(".", "");
        }
        return telefono;
    }
    
    @Override
    public String toString() {
        return String.format(
                "%s%s, %s%s (%s)",
                getApellido1(),
                getApellido2() != null ? " " + getApellido2() : "",
                getNombre1(),
                getNombre2() != null ? " " + getNombre2() : "",
                getCedula());
    }
}
