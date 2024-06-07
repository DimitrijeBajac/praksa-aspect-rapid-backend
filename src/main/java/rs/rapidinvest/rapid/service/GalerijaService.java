package rs.rapidinvest.rapid.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import rs.rapidinvest.rapid.DTO.GalerijaDTO;
import rs.rapidinvest.rapid.model.Galerija;
import rs.rapidinvest.rapid.repository.GalerijaRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

@Service
public class GalerijaService {
    @Autowired
    private GalerijaRepository galerijaRepository;
    private final String FOLDER_PATH = "C:\\Users\\dimit\\Desktop\\Rapid Invest\\SavedPhotos";
    private String sacuvajSliku(MultipartFile slika) throws IOException {
        String extension = StringUtils.substringAfterLast(slika.getOriginalFilename(), ".");
        String fileName = UUID.randomUUID().toString() + "." + extension;
        String filePath = FOLDER_PATH + File.separator + fileName;
        slika.transferTo(new File(filePath));
        return filePath;
    }

    public void saveGallery(GalerijaDTO galerijaDTO) throws IOException {

        List<MultipartFile> slike = galerijaDTO.getListaSlika();
        List<String> putanjeSlike = new ArrayList<>();
        if(slike != null && !slike.isEmpty()){
            for(MultipartFile slika: slike){
                putanjeSlike.add(sacuvajSliku(slika));
            }
        }
        Galerija postojecaGalerija = galerijaRepository.findByTip(galerijaDTO.getTip());
        if(postojecaGalerija == null){
            Galerija galerija = new Galerija();
            galerija.setTip(galerijaDTO.getTip());
            galerija.setPutanjeDoSlika(putanjeSlike);
            galerijaRepository.save(galerija);
        }else {
            List<String> postojecaLista = postojecaGalerija.getPutanjeDoSlika();
            if (postojecaLista == null) {
                postojecaLista = new ArrayList<>();
            }
            postojecaLista.addAll(putanjeSlike);
            postojecaGalerija.setPutanjeDoSlika(postojecaLista);
            galerijaRepository.save(postojecaGalerija);
        }

    }


    public List<String> getExistingImages(String tip) {
        Galerija galerija = galerijaRepository.findByTip(tip);
        List<String> slike = new ArrayList<>();
        if (galerija != null){
            slike = galerija.getPutanjeDoSlika();
        }
        return slike;
    }

    public void deleteImage(String imageUrl, String tip) throws IOException {
        // Delete the file from the file system
        Path filePath = Paths.get(imageUrl);
        Files.deleteIfExists(filePath);

        Galerija galerija = galerijaRepository.findByTip(tip);
        if (galerija != null) {
            List<String> galerijaSlika = galerija.getPutanjeDoSlika();
            galerijaSlika.remove(imageUrl);
            galerija.setPutanjeDoSlika(galerijaSlika);
           galerijaRepository.save(galerija);
        }
    }
}
