package co.com.sofka.questions.model;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

public class ProfileDTO {

    private String id;
    @NotBlank(message = "Debe existir el userId para este objeto")
    private String userId;
    private String nombres;
    private String apellidos;
    @NotBlank
    private String correo;

    public ProfileDTO() {
    }

    public ProfileDTO(String id, String userId, String nombres, String apellidos, String correo) {
        this.id = id;
        this.userId = userId;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.correo = correo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProfileDTO that = (ProfileDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(userId, that.userId) && Objects.equals(nombres, that.nombres) && Objects.equals(apellidos, that.apellidos) && Objects.equals(correo, that.correo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, nombres, apellidos, correo);
    }
}
