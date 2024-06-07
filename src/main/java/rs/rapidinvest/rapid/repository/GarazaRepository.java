package rs.rapidinvest.rapid.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.rapidinvest.rapid.model.Garaza;

import java.util.List;
import java.util.Optional;

public interface GarazaRepository extends JpaRepository<Garaza,Long> {
    void deleteById(Optional<Garaza> garaza);
    Optional<Garaza> findById(Long id);

    Garaza findGarazaById(Long garazaId);

    List<Garaza> findByDostupnostTrue();
}
