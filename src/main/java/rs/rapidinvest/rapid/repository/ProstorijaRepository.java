package rs.rapidinvest.rapid.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.rapidinvest.rapid.model.Prostorija;

import java.util.List;

public interface ProstorijaRepository extends JpaRepository <Prostorija, Long> {
    List<Prostorija> findByStanId(Long stanId);
    Prostorija findProstorijaById(Long prostorijaId);
    List<Prostorija> findByLokalId(Long lokalId);

}
