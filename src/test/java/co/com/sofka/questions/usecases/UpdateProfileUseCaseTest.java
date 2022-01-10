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

class UpdateProfileUseCaseTest {

    ProfileRepository profileRepository;
    UpdateProfileUseCase updateProfileUseCase;

    @BeforeEach
    public void setup() {
        MapperUtils mapperUtils = new MapperUtils();
        profileRepository = mock(ProfileRepository.class);
        updateProfileUseCase = new UpdateProfileUseCase(profileRepository, mapperUtils);
    }

    @Test
    void UpdateProfileUseCaseValidation() {
        Profile profileSave = new Profile();
        profileSave.setId("asd");
        profileSave.setUserId("123");
        profileSave.setNombres("juanito");
        profileSave.setApellidos("peres");
        profileSave.setCorreo("email");


        ProfileDTO profileUpdateDTO = new ProfileDTO(
                profileSave.getId(),
                profileSave.getUserId(),
                profileSave.getNombres(),
                profileSave.getApellidos(),
                profileSave.getCorreo());

        when(profileRepository.save(any())).thenReturn(Mono.just(profileSave));

        StepVerifier.create(updateProfileUseCase.apply(profileUpdateDTO))
                .expectNextMatches(profileDTO -> {
                    assert profileDTO.getId().equals("asd");
                    assert profileDTO.getNombres().equals("juanito");
                    assert profileDTO.getApellidos().equals("peres");
                    assert profileDTO.getCorreo().equals("email");
                    assert profileDTO.getUserId().equals("123");
                    return true;
                })
                .verifyComplete();

        verify(profileRepository).save(any());
    }

}