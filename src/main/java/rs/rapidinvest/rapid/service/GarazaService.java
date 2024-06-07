package rs.rapidinvest.rapid.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import rs.rapidinvest.rapid.DTO.GarazaDTO;
import rs.rapidinvest.rapid.DTO.GarazaFilterDTO;
import rs.rapidinvest.rapid.model.Garaza;
import rs.rapidinvest.rapid.model.Objekat;
import rs.rapidinvest.rapid.model.Stan;
import rs.rapidinvest.rapid.repository.GarazaRepository;
import rs.rapidinvest.rapid.repository.ObjekatRepository;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GarazaService {

    @Autowired
    private GarazaRepository garazaRepository;

    @Autowired
    private ObjekatRepository objekatRepository;

    private static final String PDF_DIRECTORY = "C:\\Users\\dimit\\Desktop\\Rapid Invest\\SavedPdf";


    public Garaza addGaraza (GarazaDTO garazaDTO){
        Garaza newGazara = new Garaza();
        Objekat objekat = objekatRepository.findObjekatById(garazaDTO.getObjekatId());
        if (objekat == null){
            throw new EntityNotFoundException("Objekat nije pronadjen");
        }
        newGazara.setObjekat(objekat);
        newGazara.setNaziv(garazaDTO.getNaziv());
        newGazara.setOpis(garazaDTO.getOpis());
        newGazara.setKvadratura(garazaDTO.getKvadratura());
        newGazara.setTip(garazaDTO.getTip());
        newGazara.setDostupnost(garazaDTO.getDostupnost());

        garazaRepository.save(newGazara);
        return newGazara;
    }

    public List<Garaza> getAllGaraze(){
        return garazaRepository.findAll();
    }

    public List<Garaza> getAllGarazeDostupne(){
        return garazaRepository.findByDostupnostTrue();
    }

    public void deleteGaraza(Long id){
        Optional<Garaza> garaza = garazaRepository.findById(id);
        if (garaza != null)
            garazaRepository.deleteById(id);
    }

    public Garaza updateGaraza(GarazaDTO garazaDTO, Long garazaId) {
        Garaza garazaToUpdate = garazaRepository.findGarazaById(garazaId);
        if(garazaToUpdate == null)
            throw new EntityNotFoundException("Garaza nije pronadjena");
        garazaToUpdate.setNaziv(garazaDTO.getNaziv());
        garazaToUpdate.setOpis(garazaDTO.getOpis());
        garazaToUpdate.setKvadratura(garazaDTO.getKvadratura());
        garazaToUpdate.setTip(garazaDTO.getTip());
        garazaToUpdate.setDostupnost(garazaDTO.getDostupnost());

        garazaRepository.save(garazaToUpdate);
        return garazaToUpdate;

    }

    public Garaza getGarazaById(Long garazaId) {
        return garazaRepository.findGarazaById(garazaId);
    }

    public String savePdf(Long id, MultipartFile file) throws IOException {
        Garaza garaza = garazaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid Stan ID"));

        if(garaza.getPdfPutanja() != null){
            throw new IllegalArgumentException("PDF fajl već postoji za ovu garažu.");
        }

        String fileName = id + File.separator + file.getOriginalFilename();
        Path pdfPath = Paths.get(PDF_DIRECTORY, fileName);
        Files.createDirectories(pdfPath.getParent());
        Files.write(pdfPath, file.getBytes());

        garaza.setPdfPutanja(pdfPath.toString());
        garazaRepository.save(garaza);

        return pdfPath.toString();
    }

    public String loadPdfPath(Long id) {
        Garaza garaza = garazaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Garaza ID"));
        return garaza.getPdfPutanja();
    }

    public Resource loadPdfResource(Long id) {
        String pdfPath = loadPdfPath(id);
        Path path = Paths.get(pdfPath);

        if (Files.exists(path) && Files.isReadable(path)) {
            try {
                return new UrlResource(path.toUri());
            } catch (MalformedURLException e) {
                throw new RuntimeException("Error: " + e.getMessage());
            }
        } else {
            throw new RuntimeException("Could not read the file!");
        }
    }

    public void deletePdf(String pdfUrl, Long id) throws IOException {
        // Delete the file from the file system
        Path filePath = Paths.get(pdfUrl);
        Files.deleteIfExists(filePath);
        Garaza garaza = garazaRepository.findGarazaById(id);
        if (garaza != null) {
            garaza.setPdfPutanja(null);
        }
        garazaRepository.save(garaza);
    }

    public List<Garaza> filterGaraze(GarazaFilterDTO filterDTO) {

        List<Garaza> sveGaraze = garazaRepository.findByDostupnostTrue();

        sveGaraze = applyObjektiFilter(sveGaraze, filterDTO.getObjekat());
        sveGaraze = applyTipFilter(sveGaraze, filterDTO.getTip());

        return sveGaraze;
    }

    private List<Garaza> applyTipFilter(List<Garaza> sveGaraze, List<String> tip) {
        if (tip != null && !tip.isEmpty()) {
            sveGaraze = sveGaraze.stream()
                    .filter(garaza -> tip.contains(garaza.getTip()))
                    .collect(Collectors.toList());
        }
        return sveGaraze;
    }

    private List<Garaza> applyObjektiFilter(List<Garaza> sveGaraze, List<String> objekti) {
        if (objekti != null && !objekti.isEmpty()) {
            sveGaraze = sveGaraze.stream()
                    .filter(garaza -> objekti.contains(garaza.getObjekat().getNaziv()))
                    .collect(Collectors.toList());
        }
        return sveGaraze;
    }
}
