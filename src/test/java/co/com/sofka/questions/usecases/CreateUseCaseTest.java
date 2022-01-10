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

class CreateUseCaseTest {

    QuestionRepository questionRepository;
    CreateUseCase createUseCase;

    @BeforeEach
    public void setup() {
        MapperUtils mapperUtils = new MapperUtils();
        questionRepository = mock(QuestionRepository.class);
        createUseCase = new CreateUseCase(mapperUtils, questionRepository);
    }

    @Test
    void createUseCaseValidation() {
        String id = "1";
        Question question = new Question();
        question.setId(id);
        question.setUserId("123");
        question.setCategory("category");
        question.setQuestion("question");
        question.setType("type");
        question.setOwnerEmail("email");

        QuestionDTO questionDTO = new QuestionDTO(
                question.getId(),
                question.getUserId(),
                question.getQuestion(),
                question.getType(),
                question.getCategory(),
                question.getOwnerEmail()
        );

        when(questionRepository.save(any())).thenReturn(Mono.just(question));

        StepVerifier.create(createUseCase.apply(questionDTO))
                .expectNextMatches(value -> {
                    assert value.equals(id);
                    return true;
                }).verifyComplete();

        verify(questionRepository).save(any());
    }
}