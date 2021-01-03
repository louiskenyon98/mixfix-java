package co.uk.kenyoncomputing.mixfix.repositories;

import co.uk.kenyoncomputing.mixfix.entities.SpotifyTokens;
import org.springframework.data.repository.CrudRepository;

public interface SpotifyTokensRepository extends CrudRepository<SpotifyTokens, Long> {
}
