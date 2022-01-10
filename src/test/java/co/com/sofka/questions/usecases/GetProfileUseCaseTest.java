package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.Profile;
import co.com.sofka.questions.reposioties.ProfileRepository;
import co.com.sofka.questions.utils.MapperUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

class GetProfileUseCaseTest {

    ProfileRepository profileRepository;
    GetProfileUseCase getProfileUseCase;

    @BeforeEach
    public void setup() {
        MapperUtils mapperUtils = new MapperUtils();
        profileRepository = mock(ProfileRepository.class);
        getProfileUseCase = new GetProfileUseCase(profileRepository, mapperUtils);
    }

    @Test
    void GetProfileUseCaseValidation() {
        String userId = "123";

        Profile profile = new Profile();
        profile.setId("asd");
        profile.setUserId(userId);
        profile.setNombres("juanito");
        profile.setApellidos("peres");
        profile.setCorreo("email");

        when(profileRepository.findAllByUserId(userId)).thenReturn(Flux.just(profile));

        StepVerifier.create(getProfileUseCase.apply(userId))
                .expectNextMatches(profileDTO -> {
                    assert profileDTO.getId().equals("asd");
                    assert profileDTO.getUserId().equals("123");
                    assert profileDTO.getNombres().equals("juanito");
                    assert profileDTO.getApellidos().equals("peres");
                    assert profileDTO.getCorreo().equals("email");
                    return true;
                })
                .verifyComplete();

        verify(profileRepository).findAllByUserId(userId);
    }
}