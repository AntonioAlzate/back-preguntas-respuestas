package co.com.sofka.questions.reposioties;

import co.com.sofka.questions.collections.Favorite;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FavoriteRepository extends ReactiveCrudRepository<Favorite, String> {
    Mono<Boolean> existsByUserIdAndQuestionId(String userId, String questionId);

    Flux<Favorite> findAllByUserId(String userId);
}
