package rs.rapidinvest.rapid.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.rapidinvest.rapid.model.Objekat;

import java.util.List;

public interface ObjekatRepository extends JpaRepository<Objekat, Long> {

    Objekat findObjekatById(Long id);
    Objekat findByNaziv(String naziv);

    List<Objekat> findByAktuelanTrue();

}
