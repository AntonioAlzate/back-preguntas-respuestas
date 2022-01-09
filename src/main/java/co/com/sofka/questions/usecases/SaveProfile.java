package co.com.sofka.questions.usecases;

import co.com.sofka.questions.model.ProfileDTO;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@FunctionalInterface
public interface SaveProfile {
    Mono<ProfileDTO> apply(@Valid ProfileDTO profileDTO);
}
