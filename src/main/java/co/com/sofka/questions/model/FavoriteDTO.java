package co.com.sofka.questions.model;

import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

public class FavoriteDTO {

    private String id;
    @NotBlank(message = "Debe contener un id de usuario")
    private String userId;
    @NotBlank(message = "Debe contener un id de la pregunta")
    private String questionId;

    public FavoriteDTO() {
    }

    public FavoriteDTO(String id, String userId, String questionId) {
        this.id = id;
        this.userId = userId;
        this.questionId = questionId;
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

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FavoriteDTO that = (FavoriteDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(userId, that.userId) && Objects.equals(questionId, that.questionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, questionId);
    }
}
