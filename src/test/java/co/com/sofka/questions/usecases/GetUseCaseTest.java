package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.Answer;
import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.reposioties.AnswerRepository;
import co.com.sofka.questions.reposioties.QuestionRepository;
import co.com.sofka.questions.utils.MapperUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

class GetUseCaseTest {

    QuestionRepository questionRepository;
    AnswerRepository answerRepository;
    GetUseCase getQuestionByIdUseCase;

    @BeforeEach
    public void setup() {
        MapperUtils mapperUtils = new MapperUtils();
        questionRepository = mock(QuestionRepository.class);
        answerRepository = mock(AnswerRepository.class);
        getQuestionByIdUseCase = new GetUseCase(mapperUtils, questionRepository, answerRepository);
    }

    @Test
    void GetQuestionByIdUseCaseValidation() {
        String questionId = "asd123";
        Question question = new Question();
        question.setId(questionId);
        question.setType("OPEN");
        question.setCategory("SCIENCES");
        question.setQuestion("¿Pregunta?");

        Answer answer = new Answer();
        answer.setId(questionId);
        answer.setUserId("asd");
        answer.setQuestionId(questionId);
        answer.setAnswer("answer");

        Mono<Question> resultQuestion = Mono.just(question);
        Flux<Answer> resultAnswer = Flux.just(answer);

        when(questionRepository.findById(questionId)).thenReturn(resultQuestion);
        when(answerRepository.findAllByQuestionId(questionId)).thenReturn(resultAnswer);

        StepVerifier.create(getQuestionByIdUseCase.apply(questionId))
                .expectNextMatches(questionDTO -> {
                    assert questionDTO.getId().equals(questionId);
                    assert questionDTO.getCategory().equals("SCIENCES");
                    assert questionDTO.getQuestion().equals("¿Pregunta?");
                    assert questionDTO.getType().equals("OPEN");
                    assert questionDTO.getAnswers().get(0).getAnswer().equals(answer.getAnswer());
                    assert questionDTO.getAnswers().get(0).getQuestionId().equals(answer.getQuestionId());
                    assert questionDTO.getAnswers().get(0).getUserId().equals(answer.getUserId());
                    return true;
                })
                .verifyComplete();

        verify(questionRepository).findById(questionId);
        verify(answerRepository).findAllByQuestionId(questionId);
    }

}