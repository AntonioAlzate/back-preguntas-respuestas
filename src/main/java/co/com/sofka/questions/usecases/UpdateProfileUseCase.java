package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.model.ProfileDTO;
import co.com.sofka.questions.reposioties.ProfileRepository;
import co.com.sofka.questions.utils.MapperUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
@Validated
public class UpdateProfileUseCase implements SaveProfile{

    private final ProfileRepository profileRepository;
    private final MapperUtils mapperUtils;

    public UpdateProfileUseCase(ProfileRepository profileRepository, MapperUtils mapperUtils) {
        this.profileRepository = profileRepository;
        this.mapperUtils = mapperUtils;
    }

    @Override
    public Mono<ProfileDTO> apply(ProfileDTO profileDTO) {
        Objects.requireNonNull(profileDTO.getId(), "Id of the profile is required");
        return profileRepository
                .save(mapperUtils.mapperToProfile().apply(profileDTO))
                .map(profile -> mapperUtils.mapEntityToProfileDTO().apply(profile));
    }
}
