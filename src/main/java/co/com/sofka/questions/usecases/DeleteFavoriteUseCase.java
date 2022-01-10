package co.com.sofka.questions.usecases;

import co.com.sofka.questions.reposioties.FavoriteRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.function.Function;

@Service
@Validated
public class DeleteFavoriteUseCase implements Function<String, Mono<Void>> {

    private final FavoriteRepository favoriteRepository;

    public DeleteFavoriteUseCase(FavoriteRepository favoriteRepository) {
        this.favoriteRepository = favoriteRepository;
    }

    @Override
    public Mono<Void> apply(String id) {
        Objects.requireNonNull(id, "Id is required");
        return favoriteRepository.deleteById(id);
    }
}
