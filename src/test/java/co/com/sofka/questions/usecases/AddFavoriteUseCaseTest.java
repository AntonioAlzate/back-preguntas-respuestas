package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.Favorite;
import co.com.sofka.questions.model.FavoriteDTO;
import co.com.sofka.questions.reposioties.FavoriteRepository;
import co.com.sofka.questions.utils.MapperUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AddFavoriteUseCaseTest {

    FavoriteRepository favoriteRepository;
    AddFavoriteUseCase addFavoriteUseCase;

    @BeforeEach
    public void setup() {
        MapperUtils mapperUtils = new MapperUtils();
        favoriteRepository = mock(FavoriteRepository.class);
        addFavoriteUseCase = new AddFavoriteUseCase(favoriteRepository, mapperUtils);
    }

    @Test
    void addFavoriteUseCaseValidation() {
        String id = "1";
        Favorite favorite = new Favorite();
        favorite.setId(id);
        favorite.setUserId("123");
        favorite.setQuestionId("asd");

        FavoriteDTO favoriteDTO =
                new FavoriteDTO(favorite.getId(), favorite.getUserId(), favorite.getQuestionId());

        when(favoriteRepository.save(any())).thenReturn(Mono.just(favorite));
        when(favoriteRepository.existsByUserIdAndQuestionId("123", "asd")).thenReturn(Mono.just(false));

        StepVerifier.create(addFavoriteUseCase.apply(favoriteDTO))
                .expectNextMatches(result -> {
                    assert result.getId().equals(id);
                    assert result.getUserId().equals("123");
                    assert result.getQuestionId().equals("asd");
                    return true;
                }).verifyComplete();

        verify(favoriteRepository).save(any());
    }

    @Test
    void addFavoriteError_alreadyExist() {
        String id = "1";
        Favorite favorite = new Favorite();
        favorite.setId(id);
        favorite.setUserId("123");
        favorite.setQuestionId("asd");

        FavoriteDTO favoriteDTO =
                new FavoriteDTO(favorite.getId(), favorite.getUserId(), favorite.getQuestionId());

        when(favoriteRepository.existsByUserIdAndQuestionId("123", "asd")).thenReturn(Mono.just(true));

        StepVerifier.create(addFavoriteUseCase.apply(favoriteDTO))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage()
                                .equals("Esta pregunta ya esta registrada como favorita de este usuario"))
                .verify();

        verify(favoriteRepository).existsByUserIdAndQuestionId("123", "asd");
    }

}