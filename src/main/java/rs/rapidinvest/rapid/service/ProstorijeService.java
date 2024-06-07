package rs.rapidinvest.rapid.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import rs.rapidinvest.rapid.DTO.ProstorijaDTO;
import rs.rapidinvest.rapid.model.Lokal;
import rs.rapidinvest.rapid.model.Prostorija;
import rs.rapidinvest.rapid.model.Stan;
import rs.rapidinvest.rapid.repository.LokalRepository;
import rs.rapidinvest.rapid.repository.ProstorijaRepository;
import rs.rapidinvest.rapid.repository.StanRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProstorijeService {
    @Autowired
    private ProstorijaRepository prostorijaRepository;

    @Autowired
    private StanRepository stanRepository;

    @Autowired
    private LokalRepository lokalRepository;


    public Prostorija addProstorija(ProstorijaDTO prostorijaDTO, Long stanId){
        Optional<Stan> opStan = stanRepository.findById(stanId);
        Stan stan = opStan.get();
        Prostorija novaProstorija = new Prostorija();
        novaProstorija.setStan(stan);
        novaProstorija.setNaziv(prostorijaDTO.getNaziv());
        novaProstorija.setKvadraturaProstorije(prostorijaDTO.getKvadraturaProstorije());
        novaProstorija.setNivo(prostorijaDTO.getNivo());

        return prostorijaRepository.save(novaProstorija);
    }

    public List<Prostorija> listaProstorijaZaObjekat(Long stanId){
        return prostorijaRepository.findByStanId(stanId);
    }

    public List<Prostorija> listaProstorijaZaLokal(Long lokalId){
        return prostorijaRepository.findByLokalId(lokalId);
    }

    public Prostorija updateProstorija(ProstorijaDTO prostorijaDTO, Long id){
        Prostorija prostorijaToUpdate = prostorijaRepository.findProstorijaById(id);
        prostorijaToUpdate.setNaziv(prostorijaDTO.getNaziv());
        prostorijaToUpdate.setKvadraturaProstorije(prostorijaDTO.getKvadraturaProstorije());
        prostorijaToUpdate.setNivo(prostorijaDTO.getNivo());

        return prostorijaRepository.save(prostorijaToUpdate);
    }

    public ResponseEntity<?> deleteProstorija(Long id) {
        try {
            prostorijaRepository.deleteById(id);
            return ResponseEntity.ok("Prostorija uspešno obrisana");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Greška prilikom brisanja prostorije: " + e.getMessage());
        }
    }


    public Prostorija addProstorijaLokal(ProstorijaDTO prostorijaDTO, Long lokalId) {
        Lokal lokal = lokalRepository.findLokalById(lokalId);
        Prostorija novaProstorija = new Prostorija();
        novaProstorija.setLokal(lokal);
        novaProstorija.setNaziv(prostorijaDTO.getNaziv());
        novaProstorija.setKvadraturaProstorije(prostorijaDTO.getKvadraturaProstorije());
        novaProstorija.setNivo(null);

        return prostorijaRepository.save(novaProstorija);
    }
}
