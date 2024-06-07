package rs.rapidinvest.rapid.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import rs.rapidinvest.rapid.DTO.ObjekatDTO;
import rs.rapidinvest.rapid.model.Objekat;
import rs.rapidinvest.rapid.model.Stan;
import rs.rapidinvest.rapid.repository.ObjekatRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class ObjekatService {


    @Autowired
    private ObjekatRepository objekatRepository;

    private final String FOLDER_PATH = "C:\\Users\\dimit\\Desktop\\Rapid Invest\\SavedPhotos";
    public Objekat addObjekat (ObjekatDTO objekatDTO) throws IOException {
        String coverSlikaPutanja = sacuvajSliku(objekatDTO.getCoverSlika());
        Objekat noviObjekat = new Objekat();
        Objekat existingObjekatWithSameName = objekatRepository.findByNaziv(objekatDTO.getNaziv());
        if (existingObjekatWithSameName != null) {
            throw new IllegalStateException("Objekat sa istim nazivom već postoji.");
        }else {
            noviObjekat.setNaziv(objekatDTO.getNaziv());
            noviObjekat.setMesto(objekatDTO.getMesto());
            noviObjekat.setAdresa(objekatDTO.getAdresa());
            noviObjekat.setTekst1(objekatDTO.getTekst1());
            noviObjekat.setTekst2(objekatDTO.getTekst2());
            noviObjekat.setAktuelan(objekatDTO.isAktuelan());
            noviObjekat.setPutanjaCoverSlika(coverSlikaPutanja);
        }
        return objekatRepository.save(noviObjekat);
    }

    private String sacuvajSliku(MultipartFile slika) throws IOException {
        String filePath = FOLDER_PATH + File.separator + slika.getOriginalFilename();
        slika.transferTo(new File(filePath));
        return filePath;
    }


    public List<Objekat> sviObjekti(){
        return objekatRepository.findAll();
    }

    public Objekat updateObjekat(Long objekatId, ObjekatDTO objekatDTO) throws IOException {
        Objekat objekatToUpdate = objekatRepository.findObjekatById(objekatId);
        objekatToUpdate.setNaziv(objekatDTO.getNaziv());
        objekatToUpdate.setMesto(objekatDTO.getMesto());
        objekatToUpdate.setTekst1(objekatDTO.getTekst1());
        objekatToUpdate.setTekst2(objekatDTO.getTekst2());
        objekatToUpdate.setAktuelan(objekatDTO.isAktuelan());
        return objekatRepository.save(objekatToUpdate);

    }


    public void deleteObjekat(Long id){
        Objekat objekat = objekatRepository.findObjekatById(id);
        if (objekat == null){
            throw new EntityNotFoundException("Objekat nije pronadjen");
        }
        objekatRepository.delete(objekat);
    }

    public Objekat findObjById(Long id){
        return objekatRepository.findObjekatById(id);
    }

    public void deleteImage(String imageUrl, Long id) throws IOException {
        // Delete the file from the file system
        Path filePath = Paths.get(imageUrl);
        Files.deleteIfExists(filePath);
        Objekat objekat = objekatRepository.findObjekatById(id);
        if (objekat != null) {
            if (imageUrl.equals(objekat.getPutanjaCoverSlika())) {
                objekat.setPutanjaCoverSlika(null);
            }

            // Zatim ažurirajte entitet u bazi podataka
            objekatRepository.save(objekat);
        }
    }

    public void updateImg(MultipartFile image, String imageId, Long objekatId) throws IOException {
        String putanjaSlike = sacuvajSliku(image);
        Objekat objekat = objekatRepository.findObjekatById(objekatId);

        if (objekat == null)
            throw new EntityNotFoundException("Stan nije pronadjen");

        if (imageId.equals("coverSlika")) {
            objekat.setPutanjaCoverSlika(putanjaSlike);
        }
        objekatRepository.save(objekat);
    }

    public List<Objekat> sviAktruelniObjekti() {
        return objekatRepository.findByAktuelanTrue();
    }
}
