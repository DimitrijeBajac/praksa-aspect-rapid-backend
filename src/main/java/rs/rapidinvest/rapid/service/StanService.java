package rs.rapidinvest.rapid.service;


import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import rs.rapidinvest.rapid.DTO.StanDTO;
import rs.rapidinvest.rapid.DTO.StanFIlterDTO;
import rs.rapidinvest.rapid.model.Objekat;
import rs.rapidinvest.rapid.model.Stan;
import rs.rapidinvest.rapid.repository.ObjekatRepository;
import rs.rapidinvest.rapid.repository.StanRepository;

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
public class StanService {

    @Autowired
    private StanRepository stanRepository;

    @Autowired
    private ObjekatRepository objekatRepository;


    private final String FOLDER_PATH = "C:\\Users\\dimit\\Desktop\\Rapid Invest\\SavedPhotos";

    private static final String PDF_DIRECTORY = "C:\\Users\\dimit\\Desktop\\Rapid Invest\\SavedPdf";


    public Stan addStan (StanDTO stanDTO) throws IOException {
        String putanjaDoSlike1 = sacuvajSliku(stanDTO.getSlika1());
        String putanjaDoSlike2 = sacuvajSliku(stanDTO.getSlika2());
        String putanjaDoRendera = sacuvajSliku(stanDTO.getRender());

        String putanjaDoSlikeDupleks1 = (stanDTO.getSlikaDupleks1() != null) ? sacuvajSliku(stanDTO.getSlikaDupleks1()) : null;
        String putanjaDoSlikeDupleks2 = (stanDTO.getSlikaDupleks2() != null) ? sacuvajSliku(stanDTO.getSlikaDupleks2()) : null;
        String putanjaDoRenderaDupleks = (stanDTO.getRenderDupleks() != null) ? sacuvajSliku(stanDTO.getRenderDupleks()) : null;


        Objekat objekat = objekatRepository.findObjekatById(stanDTO.getObjekatId());

        Stan noviStan = new Stan();
        noviStan.setObjekat(objekat);
        noviStan.setSobnost(stanDTO.getSobnost());
        noviStan.setKvadratura(stanDTO.getKvadratura());
        noviStan.setOpis(stanDTO.getOpis());

        noviStan.setSlikaPutanja1(putanjaDoSlike1);
        noviStan.setSlikaPutanja2(putanjaDoSlike2);
        noviStan.setRenderPutanja(putanjaDoRendera);

        noviStan.setDostupnost(stanDTO.isDostupnost());
        noviStan.setBrojStana(stanDTO.getBrojStana());
        noviStan.setSprat(stanDTO.getSprat());
        noviStan.setTip(stanDTO.getTip());
        noviStan.setSpratnost(stanDTO.getSpratnost());

        //dupleks
        noviStan.setSlikaDupleksPutanja1(putanjaDoSlikeDupleks1);
        noviStan.setSlikaDupleksPutanja2(putanjaDoSlikeDupleks2);
        noviStan.setRenderDupleksPutanja(putanjaDoRenderaDupleks);


        stanRepository.save(noviStan);
        return noviStan;
    }

    private String sacuvajSliku(MultipartFile slika) throws IOException {
        String filePath = FOLDER_PATH + File.separator + slika.getOriginalFilename();
        slika.transferTo(new File(filePath));
        return filePath;
    }

    public Stan findById(Long stanId){
        return stanRepository.findStanById(stanId);
    }

    public List<Stan> getAllStanovi(){
        return stanRepository.findAll();
    }

    public void deleteStan(Long id){
        Optional<Stan> stan = stanRepository.findById(id);
        if (stan.isPresent())
            stanRepository.deleteById(id);
    }

    public Stan updateStan(Long stanId, StanDTO stanDTO) throws IOException {
        Stan stan = stanRepository.findStanById(stanId);
        stan.setSobnost(stanDTO.getSobnost());
        stan.setKvadratura(stanDTO.getKvadratura());
        stan.setDostupnost(stanDTO.isDostupnost());
        stan.setBrojStana(stanDTO.getBrojStana());
        stan.setSprat(stanDTO.getSprat());
        stan.setSpratnost(stanDTO.getSpratnost());

        return stanRepository.save(stan);
    }



    public void deleteImage(String imageUrl, Long id) throws IOException {
        // Delete the file from the file system
        Path filePath = Paths.get(imageUrl);
        Files.deleteIfExists(filePath);
        Stan stan = stanRepository.findStanById(id);
        if (stan != null) {
            if (imageUrl.equals(stan.getSlikaPutanja1())) {
                stan.setSlikaPutanja1(null);
            } else if (imageUrl.equals(stan.getSlikaPutanja2())) {
                stan.setSlikaPutanja2(null);
            } else if (imageUrl.equals(stan.getRenderPutanja())) {
                stan.setRenderPutanja(null);
            } else if (imageUrl.equals(stan.getSlikaDupleksPutanja1())) {
                stan.setSlikaDupleksPutanja1(null);
            } else if (imageUrl.equals(stan.getSlikaDupleksPutanja2())) {
                stan.setSlikaDupleksPutanja2(null);
            } else if (imageUrl.equals(stan.getRenderDupleksPutanja())) {
                stan.setRenderDupleksPutanja(null);
            }

            // Zatim ažurirajte entitet u bazi podataka
            stanRepository.save(stan);
        }
    }

    public void updateImg(MultipartFile image, String imageId, Long stanId) throws IOException {
        String putanjaSlike = sacuvajSliku(image);
        Stan stan = stanRepository.findStanById(stanId);

        if (stan == null)
            throw new EntityNotFoundException("Stan nije pronadjen");

        if (imageId.equals("slika1")){
            stan.setSlikaPutanja1(putanjaSlike);
        }else if(imageId.equals("slika2")){
            stan.setSlikaPutanja2(putanjaSlike);
        }else if(imageId.equals("render")){
            stan.setRenderPutanja(putanjaSlike);
        } else if (imageId.equals("slikaDupleks1")) {
            stan.setSlikaDupleksPutanja1(putanjaSlike);
        } else if (imageId.equals("slikaDupleks2")) {
            stan.setSlikaDupleksPutanja2(putanjaSlike);
        }else {
            stan.setRenderDupleksPutanja(putanjaSlike);
        }

        stanRepository.save(stan);
    }

    public String savePdf(Long id, MultipartFile file) throws IOException {
        Stan stan = stanRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid Stan ID"));

        if(stan.getPdfPutanja() != null){
            throw new IllegalArgumentException("PDF fajl već postoji za ovaj stan.");
        }

        String fileName = id + File.separator + file.getOriginalFilename();
        Path pdfPath = Paths.get(PDF_DIRECTORY, fileName);
        Files.createDirectories(pdfPath.getParent());
        Files.write(pdfPath, file.getBytes());

        stan.setPdfPutanja(pdfPath.toString());
        stanRepository.save(stan);

        return pdfPath.toString();
    }

    public String loadPdfPath(Long id) {
        Stan stan = stanRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Stan ID"));
        return stan.getPdfPutanja();
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
        Stan stan = stanRepository.findStanById(id);
        if (stan != null) {
            stan.setPdfPutanja(null);
        }
        stanRepository.save(stan);
    }


    public List<Stan> sviDostupniStanovi(){
        return stanRepository.findByDostupnostTrue();
    }

    public List<Stan> filterStans(StanFIlterDTO filterDTO) {
        List<Stan> allStans = stanRepository.findAll();

        System.out.println(transformSobnost(filterDTO.getSobnost()));
        allStans = applyObjektiFilter(allStans, filterDTO.getObjekat());
        allStans = applySobnostFilter(allStans, transformSobnost(filterDTO.getSobnost()));
        allStans = applyKvadraturaFilter(allStans, filterDTO.getKvadratura());
        allStans = applySpratnostFilter(allStans, filterDTO.getSpratnost());

        return allStans;
    }

    private List<Stan> applyObjektiFilter(List<Stan> stans, List<String> objekti) {
        if (objekti != null && !objekti.isEmpty()) {
            stans = stans.stream()
                    .filter(stan -> objekti.contains(stan.getObjekat().getNaziv()))
                    .collect(Collectors.toList());
        }
        return stans;
    }

    private List<Stan> applySpratnostFilter(List<Stan> stans, List<String> spratnost) {
        if (spratnost != null && !spratnost.isEmpty()) {
            stans = stans.stream()
                    .filter(stan -> spratnost.contains(stan.getSpratnost()))
                    .collect(Collectors.toList());
        }
        return stans;
    }



    private List<Stan> applyKvadraturaFilter(List<Stan> stans, List<String> kvadratura) {
        if (kvadratura != null && !kvadratura.isEmpty()) {
            List<Double[]> transformedKvadratura = transformKvadratura(kvadratura);

            stans = stans.stream()
                    .filter(stan -> {
                        double stanKvadratura = stan.getKvadratura();
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
        return stans;
    }

    private List<Stan> applySobnostFilter(List<Stan> stans, List<Integer> sobnost) {
        if (sobnost != null && !sobnost.isEmpty()) {
            List<Stan> filteredStans = stans.stream()
                    .filter(stan -> sobnost.contains(stan.getSobnost()))
                    .collect(Collectors.toList());
            return filteredStans;
        }
        return stans;
    }

    private List<Double[]> transformKvadratura(List<String> kvadratura) {
        List<Double[]> result = new ArrayList<>();
        for (String k : kvadratura) {
            switch (k) {
                case "Do 40m2":
                    result.add(new Double[]{0.0, 40.0});
                    break;
                case "40 - 50m2":
                    result.add(new Double[]{40.0, 50.0});
                    break;
                case "50 - 70m2":
                    result.add(new Double[]{50.0, 70.0});
                    break;
                case "70 - 90m2":
                    result.add(new Double[]{70.0, 90.0});
                    break;
                case "90 - 100m2":
                    result.add(new Double[]{90.0, 100.0});
                    break;
                case "100+m2":
                    result.add(new Double[]{100.0, Double.MAX_VALUE});
                    break;
                default:
                    break;
            }
        }
        return result;
    }

    private List<Integer> transformSobnost(List<String> sobnost) {
        List<Integer> result = new ArrayList<>();
        for (String s : sobnost) {
            switch (s) {
                case "Jednosobni i dvosobni":
                    result.add(1);
                    result.add(2);
                    break;
                case "Trosobni i četvorosobni":
                    result.add(3);
                    result.add(4);
                    break;
                case "5+":
                    result.add(5);
                    break;
                default:
                    result.add(Integer.parseInt(s));
                    break;
            }
        }
        return result;
    }
}




