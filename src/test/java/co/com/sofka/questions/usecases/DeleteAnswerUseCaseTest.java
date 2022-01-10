package co.com.sofka.questions.usecases;

import co.com.sofka.questions.reposioties.AnswerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

class DeleteAnswerUseCaseTest {

    AnswerRepository answerRepository;
    DeleteAnswerUseCase deleteAnswerUseCase;

    @BeforeEach
    public void setup() {
        answerRepository = mock(AnswerRepository.class);
        deleteAnswerUseCase = new DeleteAnswerUseCase(answerRepository);
    }

    @Test
    void deleteAnswerValidation() {
        String id = "123";
        when(answerRepository.deleteById(id)).thenReturn(Mono.empty());

        StepVerifier.create(deleteAnswerUseCase.apply(id))
                .verifyComplete();

        verify(answerRepository).deleteById(id);
    }
}