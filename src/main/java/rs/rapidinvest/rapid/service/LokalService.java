package rs.rapidinvest.rapid.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import rs.rapidinvest.rapid.DTO.LokalDTO;
import rs.rapidinvest.rapid.DTO.LokalFilterDTO;
import rs.rapidinvest.rapid.DTO.StanFIlterDTO;
import rs.rapidinvest.rapid.model.Lokal;
import rs.rapidinvest.rapid.model.Objekat;
import rs.rapidinvest.rapid.model.Stan;
import rs.rapidinvest.rapid.repository.LokalRepository;
import rs.rapidinvest.rapid.repository.ObjekatRepository;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LokalService {

    @Autowired
    private LokalRepository lokalRepository;

    @Autowired
    private ObjekatRepository objekatRepository;

    private final String FOLDER_PATH = "C:\\Users\\dimit\\Desktop\\Rapid Invest\\SavedPhotos";

    private static final String PDF_DIRECTORY = "C:\\Users\\dimit\\Desktop\\Rapid Invest\\SavedPdf";


    public Lokal addLokal (Lokal lokal){
        return lokalRepository.save(lokal);
    }

    public List<Lokal> getAllLokali(){
        return lokalRepository.findLokaliByProdajaOrIzdavanje();
    }

    public void deleteLokal(Long id){
        Optional<Lokal> lokal = lokalRepository.findById(id);
        if (lokal != null)
            lokalRepository.deleteById(id);
    }


    public Lokal dodajLokal(LokalDTO lokalDTO) throws IOException {
        String putanjaNaslovnaSLika = sacuvajSliku(lokalDTO.getNaslovnaSlikaLokal());
        String putanjaSlika1 = sacuvajSliku(lokalDTO.getSlika1Lokal());
        String putanjaSlika2 = sacuvajSliku(lokalDTO.getSlika2Lokal());
        String putanjaSlika3 = sacuvajSliku(lokalDTO.getSlika3Lokal());
        Lokal lokal = new Lokal();
        Objekat lokalObjekat = objekatRepository.findObjekatById(lokalDTO.getObjekatId());
        lokal.setObjekat(lokalObjekat);
        lokal.setNaziv(lokalDTO.getNaziv());
        lokal.setProdaja(lokalDTO.getProdaja());
        lokal.setIzdavanje(lokalDTO.getIzdavanje());
        lokal.setKvadratura(lokalDTO.getKvadratura());
        lokal.setOpis(lokalDTO.getOpisLokal());
        lokal.setNaslovnaSlka(putanjaNaslovnaSLika);
        lokal.setSlikaPutanja1(putanjaSlika1);
        lokal.setSlikaPutanja2(putanjaSlika2);
        lokal.setSlikaPutanja3(putanjaSlika3);

        lokalRepository.save(lokal);
        return lokal;
    }

    private String sacuvajSliku(MultipartFile slika) throws IOException {
        String filePath = FOLDER_PATH + File.separator + slika.getOriginalFilename();
        slika.transferTo(new File(filePath));
        return filePath;
    }

    public  Lokal updateLokal(LokalDTO lokalDTO, Long lokalId){
        Lokal lokalToUpdate = lokalRepository.findLokalById(lokalId);
        if(lokalToUpdate == null){
            throw new EntityNotFoundException("Objekat nije pronadjen");
        }
        lokalToUpdate.setNaziv(lokalDTO.getNaziv());
        lokalToUpdate.setProdaja(lokalDTO.getProdaja());
        lokalToUpdate.setIzdavanje(lokalDTO.getIzdavanje());
        lokalToUpdate.setKvadratura(lokalDTO.getKvadratura());
        lokalToUpdate.setOpis(lokalDTO.getOpisLokal());

        lokalRepository.save(lokalToUpdate);
        return lokalToUpdate;
    }


    public Lokal getLokalById(Long lokalId){
        return lokalRepository.findLokalById(lokalId);
    }

    public void deleteImage(String imageUrl, Long id) throws IOException {
        // Delete the file from the file system
        Path filePath = Paths.get(imageUrl);
        Files.deleteIfExists(filePath);
        Lokal lokal = lokalRepository.findLokalById(id);
        if (lokal != null) {
            if (imageUrl.equals(lokal.getNaslovnaSlka())) {
                lokal.setNaslovnaSlka(null);
            } else if (imageUrl.equals(lokal.getSlikaPutanja1())) {
                lokal.setSlikaPutanja1(null);
            } else if (imageUrl.equals(lokal.getSlikaPutanja2())) {
                lokal.setSlikaPutanja2(null);
            }else if(imageUrl.equals(lokal.getSlikaPutanja3())){
                lokal.setSlikaPutanja3(null);
            }

            // Zatim ažurirajte entitet u bazi podataka
            lokalRepository.save(lokal);
        }
    }

    public void updateImg(MultipartFile image, String imageId, Long lokalId) throws IOException {

        String putanjaSlike = sacuvajSliku(image);
        Lokal lokal = lokalRepository.findLokalById(lokalId);

        if (lokal == null)
            throw new EntityNotFoundException("Stan nije pronadjen");

        if (imageId.equals("naslovnaSlikaLokal")){
            lokal.setNaslovnaSlka(putanjaSlike);
        }else if(imageId.equals("slika1Lokal")){
            lokal.setSlikaPutanja1(putanjaSlike);
        }else if(imageId.equals(("slika2Lokal"))){
            lokal.setSlikaPutanja2(putanjaSlike);
        }else {
            lokal.setSlikaPutanja3(putanjaSlike);
        }
        lokalRepository.save(lokal);
    }

    public String savePdf(Long id, MultipartFile file) throws IOException {
        Lokal lokal = lokalRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid Lokal ID"));

        if(lokal.getPdfPutanja() != null){
            throw new IllegalArgumentException("PDF fajl već postoji za ovaj lokal.");
        }

        String fileName = id + File.separator + file.getOriginalFilename();
        Path pdfPath = Paths.get(PDF_DIRECTORY, fileName);
        Files.createDirectories(pdfPath.getParent());
        Files.write(pdfPath, file.getBytes());

        lokal.setPdfPutanja(pdfPath.toString());
        lokalRepository.save(lokal);

        return pdfPath.toString();
    }

    public String loadPdfPath(Long id) {
        Lokal lokal = lokalRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Lokal ID"));
        return lokal.getPdfPutanja();
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
        Lokal lokal = lokalRepository.findLokalById(id);
        if (lokal != null) {
            lokal.setPdfPutanja(null);
        }
        lokalRepository.save(lokal);
    }

    public List<Lokal> filterLokal(LokalFilterDTO filterDTO) {
        List<Lokal> sviLokali = lokalRepository.findAll();

        sviLokali = applyObjektiFilter(sviLokali, filterDTO.getObjekat());
        sviLokali = applyKvadraturaFilter(sviLokali, filterDTO.getKvadratura());
        sviLokali = applyProdajaFilter(sviLokali, filterDTO.getProdaja());
        sviLokali = applyIzdavanjeFilter(sviLokali, filterDTO.getIzdavanje());

        return sviLokali;
    }

    private List<Lokal> applyIzdavanjeFilter(List<Lokal> lokals, List<String> izdavanje) {
        if (izdavanje != null && !izdavanje.isEmpty()) {
            lokals = lokals.stream()
                    .filter(lokal -> lokal.getIzdavanje())
                    .collect(Collectors.toList());
        }
        return lokals;
    }

    private List<Lokal> applyProdajaFilter(List<Lokal> lokals, List<String> prodaja) {
        if (prodaja != null && !prodaja.isEmpty()) {
            lokals = lokals.stream()
                    .filter(lokal -> lokal.getProdaja())
                    .collect(Collectors.toList());
        }
        return lokals;
    }

    private List<Lokal> applyKvadraturaFilter(List<Lokal> sviLokali, List<String> kvadratura) {
        if (kvadratura != null && !kvadratura.isEmpty()) {
            List<Double[]> transformedKvadratura = transformKvadratura(kvadratura);

            sviLokali = sviLokali.stream()
                    .filter(lokal -> {
                        double stanKvadratura = lokal.getKvadratura();
                        for (Double[] range : transformedKvadratura) {
                            double min = range[0];
                            double max = range[1];
                            if (stanKvadratura >= min && stanKvadratura <= max) {
                                return true;
                            }
                        }
                        return false;
                    })
                    .collect(Collectors.toList());
        }
        return sviLokali;
    }

    private List<Lokal> applyObjektiFilter(List<Lokal> lokals, List<String> objekti) {
        if (objekti != null && !objekti.isEmpty()) {
            lokals = lokals.stream()
                    .filter(lokal -> objekti.contains(lokal.getObjekat().getNaziv()))
                    .collect(Collectors.toList());
        }
        return lokals;
    }



    private List<Double[]> transformKvadratura(List<String> kvadratura) {
        List<Double[]> result = new ArrayList<>();
        for (String k : kvadratura) {
            switch (k) {
                case "Do 50m2":
                    result.add(new Double[]{0.0, 50.0});
                    break;
                case "50 - 1000m2":
                    result.add(new Double[]{50.0, 100.0});
                    break;
                case "100 - 200m2":
                    result.add(new Double[]{100.0, 200.0});
                    break;
                case "200+m2":
                    result.add(new Double[]{200.0, Double.MAX_VALUE});
                    break;
                default:
                    break;
            }
        }
        return result;
    }

}
