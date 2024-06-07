package rs.rapidinvest.rapid.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rs.rapidinvest.rapid.model.Stan;

import java.util.List;
import java.util.Optional;

public interface StanRepository extends JpaRepository<Stan, Long> {
    void deleteById(Optional<Stan> stan);
    Optional<Stan> findById(Long id);
    Stan findStanById(Long id);

    List<Stan> findByDostupnostTrue();



}
