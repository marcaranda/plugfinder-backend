package backend.plugfinder.models;

import jakarta.persistence.*;

@Entity
@Table(name = "Usuario")
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private long user_id;
    @Column (unique = true, nullable = false)
    private String nombre_usuario;
    @Column (nullable = false)
    private String nombre_real;
    @Column (unique = true)
    private String telefono;
    @Column (unique = true, nullable = false)
    private String correo;
    @Column (nullable = false)
    private String contrasena;
    @Column (nullable = false)
    private String fecha_nacimiento;

    public long getId_usuario() {
        return user_id;
    }

    public void setId_usuario(long user_id) {
        this.user_id = user_id;
    }

    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }

    public String getNombre_real() {
        return nombre_real;
    }

    public void setNombre_real(String nombre_real) {
        this.nombre_real = nombre_real;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(String fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }
}
