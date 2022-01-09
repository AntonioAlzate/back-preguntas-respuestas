package co.com.sofka.questions.utils;

import co.com.sofka.questions.collections.Answer;
import co.com.sofka.questions.collections.Profile;
import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.model.ProfileDTO;
import co.com.sofka.questions.model.QuestionDTO;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class MapperUtils {

    public Function<AnswerDTO, Answer> mapperToAnswer() {
        return updateAnswer -> {
            Answer answer = new Answer();
            answer.setPosition(updateAnswer.getPosition());
            answer.setQuestionId(updateAnswer.getQuestionId());
            answer.setUserId(updateAnswer.getUserId());
            answer.setAnswer(updateAnswer.getAnswer());
            return answer;
        };
    }

    public Function<QuestionDTO, Question> mapperToQuestion(String id) {
        return updateQuestion -> {
            Question question = new Question();
            question.setId(id);
            question.setUserId(updateQuestion.getUserId());
            question.setCategory(updateQuestion.getCategory());
            question.setQuestion(updateQuestion.getQuestion());
            question.setUserId(updateQuestion.getUserId());
            question.setType(updateQuestion.getType());
            question.setOwnerEmail(updateQuestion.getOwnerEmail());
            return question;
        };
    }

    public Function<Question, QuestionDTO> mapEntityToQuestion() {
        return entity -> new QuestionDTO(
                entity.getId(),
                entity.getUserId(),
                entity.getQuestion(),
                entity.getType(),
                entity.getCategory(),
                entity.getOwnerEmail()
        );
    }

    public Function<Answer, AnswerDTO> mapEntityToAnswer() {
        return entity -> new AnswerDTO(
                entity.getId(),
                entity.getQuestionId(),
                entity.getUserId(),
                entity.getAnswer()
        );
    }

    public Function<Profile, ProfileDTO> mapEntityToProfileDTO(){
        return profile -> new ProfileDTO(
                profile.getId(),
                profile.getUserId(),
                profile.getNombres(),
                profile.getApellidos(),
                profile.getCorreo()
        );
    }

    public Function<ProfileDTO, Profile> mapperToProfile() {
        return profileDTO -> {
            Profile profile = new Profile();
            profile.setId(profileDTO.getId());
            profile.setUserId(profileDTO.getUserId());
            profile.setNombres(profileDTO.getNombres());
            profile.setApellidos(profileDTO.getApellidos());
            profile.setCorreo(profileDTO.getCorreo());
            return profile;
        };
    }
}
