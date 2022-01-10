package co.com.sofka.questions.usecases;

import co.com.sofka.questions.model.ProfileDTO;
import co.com.sofka.questions.reposioties.ProfileRepository;
import co.com.sofka.questions.utils.MapperUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
@Validated
public class GetProfileUseCase implements Function<String, Mono<ProfileDTO>> {

    private final ProfileRepository profileRepository;
    private final MapperUtils mapperUtils;

    public GetProfileUseCase(ProfileRepository profileRepository, MapperUtils mapperUtils) {
        this.profileRepository = profileRepository;
        this.mapperUtils = mapperUtils;
    }

    @Override
    public Mono<ProfileDTO> apply(String userId) {
        return profileRepository.findAllByUserId(userId)
                .map(profile -> mapperUtils.mapEntityToProfileDTO().apply(profile))
                .elementAt(0).onErrorReturn(new ProfileDTO());
    }
}
