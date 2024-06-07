package rs.rapidinvest.rapid.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.rapidinvest.rapid.model.Galerija;

public interface GalerijaRepository extends JpaRepository<Galerija, Long> {

    Galerija findByTip(String tip);

}
