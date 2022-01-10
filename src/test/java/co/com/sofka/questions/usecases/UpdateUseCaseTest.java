package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.reposioties.QuestionRepository;
import co.com.sofka.questions.utils.MapperUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UpdateUseCaseTest {

    QuestionRepository questionRepository;
    UpdateUseCase updateQuestionUseCase;

    @BeforeEach
    public void setup() {
        MapperUtils mapperUtils = new MapperUtils();
        questionRepository = mock(QuestionRepository.class);
        updateQuestionUseCase = new UpdateUseCase(mapperUtils, questionRepository);
    }

    @Test
    void updateQuestionValidation() {
        Question question = new Question();
        question.setId("asd123");
        question.setUserId("123");
        question.setType("OPEN");
        question.setCategory("SCIENCES");
        question.setQuestion("¿Pregunta?");

        QuestionDTO questionDTOP = new QuestionDTO("asd123", "123", "¿Pregunta?", "OPEN", "SCIENCES", "email");
        when(questionRepository.save(any())).thenReturn(Mono.just(question));

        StepVerifier.create(updateQuestionUseCase.apply(questionDTOP))
                .expectNextMatches(value -> {
                    assert value.equals(question.getId());
                    return true;
                })
                .verifyComplete();

        verify(questionRepository).save(any());
    }
}