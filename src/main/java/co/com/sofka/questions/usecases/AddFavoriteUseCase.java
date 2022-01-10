package co.com.sofka.questions.usecases;

import co.com.sofka.questions.model.FavoriteDTO;
import co.com.sofka.questions.reposioties.FavoriteRepository;
import co.com.sofka.questions.utils.MapperUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

@Service
@Validated
public class AddFavoriteUseCase implements SaveFavorite {

    private final FavoriteRepository favoriteRepository;
    private final MapperUtils mapperUtils;

    public AddFavoriteUseCase(FavoriteRepository favoriteRepository, MapperUtils mapperUtils) {
        this.favoriteRepository = favoriteRepository;
        this.mapperUtils = mapperUtils;
    }

    @Override
    public Mono<FavoriteDTO> apply(FavoriteDTO favoriteDTO) {

        return favoriteRepository.existsByUserIdAndQuestionId(favoriteDTO.getUserId(), favoriteDTO.getQuestionId())
                .flatMap(result -> {
                    if (result) {
                        return Mono.error(new RuntimeException("Esta pregunta ya esta registrada como favorita de este usuario"));
                    }
                    return favoriteRepository.save(mapperUtils.mapperToFavorite().apply(favoriteDTO))
                            .flatMap(favorite -> Mono.just(mapperUtils.mapEntityToFavoriteDTO().apply(favorite)));
                });

    }
}
