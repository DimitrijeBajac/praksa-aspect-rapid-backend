package rs.rapidinvest.rapid.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rs.rapidinvest.rapid.model.Lokal;

import java.util.List;
import java.util.Optional;

public interface LokalRepository extends JpaRepository<Lokal, Long> {
    void deleteById(Optional<Lokal> lokal);
    Lokal findLokalById(Long id);

    // Custom JPQL query
    @Query("SELECT l FROM Lokal l WHERE l.prodaja = true OR l.izdavanje = true")
    List<Lokal> findLokaliByProdajaOrIzdavanje();
}
