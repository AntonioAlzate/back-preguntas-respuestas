package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.Answer;
import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.reposioties.AnswerRepository;
import co.com.sofka.questions.utils.MapperUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class AddAnswerUseCaseTest {

    AnswerRepository answerRepository;
    GetUseCase getQuestionByIdUseCase;
    AddAnswerUseCase createAndAddAnswerUseCase;

    @BeforeEach
    public void setup() {
        MapperUtils mapperUtils = new MapperUtils();
        answerRepository = mock(AnswerRepository.class);
        getQuestionByIdUseCase = mock(GetUseCase.class);
        createAndAddAnswerUseCase = new AddAnswerUseCase(mapperUtils, getQuestionByIdUseCase, answerRepository);
    }

    @Test
    void createAndAddValidation() {

        QuestionDTO questionDTOP = new QuestionDTO("asd123", "asd", "¿Pregunta?", "OPEN", "SCIENCES", "email");

        Answer answer = new Answer();
        answer.setId("123");
        answer.setUserId("asd");
        answer.setAnswer("answer");
        answer.setQuestionId("asd123");

        AnswerDTO answerDTO = new AnswerDTO("123", "asd123", "asd", "answer");

        Mockito.when(answerRepository.save(any())).thenReturn(Mono.just(answer));
        Mockito.when(getQuestionByIdUseCase.apply("asd123")).thenReturn(Mono.just(questionDTOP));

        StepVerifier.create(createAndAddAnswerUseCase.apply(answerDTO))
                .expectNextMatches(questionDTO -> {
                    assert questionDTO.getId().equals("asd123");
                    assert questionDTO.getCategory().equals("SCIENCES");
                    assert questionDTO.getQuestion().equals("¿Pregunta?");
                    assert questionDTO.getType().equals("OPEN");
                    assert questionDTO.getAnswers().get(0).getAnswer().equals(answerDTO.getAnswer());
                    assert questionDTO.getAnswers().get(0).getUserId().equals(answerDTO.getUserId());
                    assert questionDTO.getAnswers().get(0).getQuestionId().equals(answerDTO.getQuestionId());
                    return true;
                })
                .verifyComplete();

        verify(answerRepository).save(any());
        verify(getQuestionByIdUseCase).apply("asd123");
    }

}