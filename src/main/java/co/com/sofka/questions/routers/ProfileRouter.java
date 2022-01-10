package co.com.sofka.questions.routers;

import co.com.sofka.questions.model.ProfileDTO;
import co.com.sofka.questions.usecases.CreateProfileUseCase;
import co.com.sofka.questions.usecases.GetProfileUseCase;
import co.com.sofka.questions.usecases.UpdateProfileUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class ProfileRouter {

    @Bean
    public RouterFunction<ServerResponse> createProfile(CreateProfileUseCase createProfileUseCase) {
        return route(
                POST("/create/profile").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(ProfileDTO.class).flatMap(profileDTO ->
                        createProfileUseCase.apply(profileDTO)
                                .flatMap(result -> ServerResponse.ok()
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result)))
        );
    }

    @Bean
    public RouterFunction<ServerResponse> getProfile(GetProfileUseCase getProfileUseCase) {
        return route(
                GET("/get/profile/{userId}").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getProfileUseCase.apply(
                                        request.pathVariable("userId")),
                                ProfileDTO.class
                        ))
        );
    }

    @Bean
    public RouterFunction<ServerResponse> updateProfile(UpdateProfileUseCase updateProfileUseCase) {
        return route(
                PUT("/profile/{id}").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(ProfileDTO.class)
                        .flatMap(profileDTO -> updateProfileUseCase.apply(profileDTO))
                        .flatMap(result -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(result))
        );
    }
}
