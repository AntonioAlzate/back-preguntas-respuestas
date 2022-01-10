package co.com.sofka.questions.usecases;

import co.com.sofka.questions.reposioties.FavoriteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

class DeleteFavoriteUseCaseTest {

    FavoriteRepository favoriteRepository;
    DeleteFavoriteUseCase deleteFavoriteUseCase;

    @BeforeEach
    public void setup() {
        favoriteRepository = mock(FavoriteRepository.class);
        deleteFavoriteUseCase = new DeleteFavoriteUseCase(favoriteRepository);
    }

    @Test
    void deleteAnswerValidation() {
        String id = "123";
        when(favoriteRepository.deleteById(id)).thenReturn(Mono.empty());

        StepVerifier.create(deleteFavoriteUseCase.apply(id))
                .verifyComplete();

        verify(favoriteRepository).deleteById(id);
    }
}