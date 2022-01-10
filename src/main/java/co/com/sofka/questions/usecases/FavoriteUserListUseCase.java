package co.com.sofka.questions.usecases;

import co.com.sofka.questions.model.FavoriteDTO;
import co.com.sofka.questions.reposioties.FavoriteRepository;
import co.com.sofka.questions.utils.MapperUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;

import java.util.function.Function;

@Service
@Validated
public class FavoriteUserListUseCase implements Function<String, Flux<FavoriteDTO>> {

    private final FavoriteRepository favoriteRepository;
    private final MapperUtils mapperUtils;

    public FavoriteUserListUseCase(FavoriteRepository favoriteRepository, MapperUtils mapperUtils) {
        this.favoriteRepository = favoriteRepository;
        this.mapperUtils = mapperUtils;
    }

    @Override
    public Flux<FavoriteDTO> apply(String userId) {
        return favoriteRepository.findAllByUserId(userId)
                .map(favorite -> mapperUtils.mapEntityToFavoriteDTO().apply(favorite));
    }
}
