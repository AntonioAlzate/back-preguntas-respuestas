package co.com.sofka.questions.reposioties;

import co.com.sofka.questions.collections.Profile;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProfileRepository extends ReactiveCrudRepository<Profile, String> {
    Flux<Profile> findAllByUserId(String userId);
}
