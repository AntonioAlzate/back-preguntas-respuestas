package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.Profile;
import co.com.sofka.questions.model.ProfileDTO;
import co.com.sofka.questions.reposioties.ProfileRepository;
import co.com.sofka.questions.utils.MapperUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CreateProfileUseCaseTest {

    ProfileRepository profileRepository;
    CreateProfileUseCase createProfileUseCase;

    @BeforeEach
    public void setup() {
        MapperUtils mapperUtils = new MapperUtils();
        profileRepository = mock(ProfileRepository.class);
        createProfileUseCase = new CreateProfileUseCase(profileRepository, mapperUtils);
    }

    @Test
    void createProfileUseCaseValidation() {
        String id = "1";
        Profile profile = new Profile();
        profile.setId(id);
        profile.setUserId("123");
        profile.setNombres("juanito");
        profile.setApellidos("perez");
        profile.setCorreo("email");

        ProfileDTO profileDTO =
                new ProfileDTO(
                        profile.getId(),
                        profile.getUserId(),
                        profile.getNombres(),
                        profile.getApellidos(),
                        profile.getCorreo()
                );

        when(profileRepository.save(any())).thenReturn(Mono.just(profile));

        StepVerifier.create(createProfileUseCase.apply(profileDTO))
                .expectNextMatches(result -> {
                    assert result.getId().equals(id);
                    assert result.getUserId().equals("123");
                    assert result.getNombres().equals("juanito");
                    assert result.getApellidos().equals("perez");
                    assert result.getCorreo().equals("email");
                    return true;
                }).verifyComplete();

        verify(profileRepository).save(any());
    }

}