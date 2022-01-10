package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.Favorite;
import co.com.sofka.questions.reposioties.FavoriteRepository;
import co.com.sofka.questions.utils.MapperUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

class FavoriteUserListUseCaseTest {

    FavoriteRepository repository;
    FavoriteUserListUseCase listUseCase;


    @BeforeEach
    public void setup() {
        MapperUtils mapperUtils = new MapperUtils();
        repository = mock(FavoriteRepository.class);
        listUseCase = new FavoriteUserListUseCase(repository, mapperUtils);
    }

    @Test
    void getValidationTest() {
        Favorite favorite = new Favorite();
        favorite.setId("123");
        favorite.setUserId("asd");
        favorite.setQuestionId("123asd");

        when(repository.findAllByUserId(favorite.getUserId())).thenReturn(Flux.just(favorite));

        StepVerifier.create(listUseCase.apply("asd"))
                .expectNextMatches(favoriteDTO -> {
                    assert favoriteDTO.getId().equals("123");
                    assert favoriteDTO.getUserId().equals("asd");
                    assert favoriteDTO.getQuestionId().equals("123asd");
                    return true;
                })
                .verifyComplete();

        verify(repository).findAllByUserId("asd");
    }

}