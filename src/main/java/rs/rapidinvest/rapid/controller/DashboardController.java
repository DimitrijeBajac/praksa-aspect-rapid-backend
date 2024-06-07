package rs.rapidinvest.rapid.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import rs.rapidinvest.rapid.DTO.GalerijaDTO;
import rs.rapidinvest.rapid.DTO.ObjekatDTO;
import rs.rapidinvest.rapid.DTO.ProstorijaDTO;
import rs.rapidinvest.rapid.DTO.StanDTO;
import rs.rapidinvest.rapid.model.Objekat;
import rs.rapidinvest.rapid.model.Prostorija;
import rs.rapidinvest.rapid.model.Stan;
import rs.rapidinvest.rapid.service.GalerijaService;
import rs.rapidinvest.rapid.service.ObjekatService;
import rs.rapidinvest.rapid.service.ProstorijeService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/dashboard")
@CrossOrigin("http://127.0.0.1:5500")
public class DashboardController {

    @Autowired
    private ProstorijeService prostorijeService;

    @Autowired
    private ObjekatService objekatService;

    @Autowired
    private GalerijaService galerijaService;

    @PostMapping("/addObjekat")
    public ResponseEntity<Objekat> dodajObjekat(@ModelAttribute ObjekatDTO objekatDTO) throws IOException {
        Objekat objekat = objekatService.addObjekat(objekatDTO);
        return new ResponseEntity<>(objekat, HttpStatus.CREATED);
    }

    @GetMapping("/getObjekti")
    public List<Objekat> sviObjekti(){
        return objekatService.sviObjekti();
    }

    @GetMapping("/getAktuelniObjekti")
    public List<Objekat> getAktuelniObjekti(){
        return objekatService.sviAktruelniObjekti();
    }

    @PutMapping("updateObjekat/{objekatId}")
    public ResponseEntity<Objekat> updateObjekat(@PathVariable Long objekatId, @ModelAttribute ObjekatDTO objekatDTO) throws IOException {
        Objekat updatedObj = objekatService.updateObjekat(objekatId, objekatDTO);
        return ResponseEntity.ok(updatedObj);
    }

    @GetMapping("objekatInfo/{objekatId}")
    public Objekat objekatInfo(@PathVariable Long objekatId){
        return objekatService.findObjById(objekatId);
    }

    @DeleteMapping("/obrisiObjekat/{id}")
    public ResponseEntity<String> deleteObjekat(@PathVariable Long id) {
        objekatService.deleteObjekat(id);
        return ResponseEntity.ok("Objekat je uspešno obrisan.");
    }

    @DeleteMapping("/deleteImageObjekat")
    public ResponseEntity<?> deleteImage(@RequestParam("imageUrl") String imageUrl, @RequestParam("id") Long id) {
        try {
            objekatService.deleteImage(imageUrl, id);
            return ResponseEntity.ok("Image deleted successfully!");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error deleting image");
        }
    }

    @PostMapping("/updateImage")
    public ResponseEntity<String> updateImage(@RequestParam("image") MultipartFile image,
                                              @RequestParam("imageId") String imageId,
                                              @RequestParam("objekatId") Long objekatId) {
        try {
            objekatService.updateImg(image, imageId, objekatId);
            return new ResponseEntity<>("Image uploaded successfully", HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("Failed to upload image", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }








    @PostMapping("/addProstorija/{stanId}")
    public ResponseEntity<Prostorija> dodajProstoriju(@ModelAttribute ProstorijaDTO prostorijaDTO, @PathVariable Long stanId) {
        Prostorija novaProstorija = prostorijeService.addProstorija(prostorijaDTO, stanId);
        return new ResponseEntity<>(novaProstorija, HttpStatus.CREATED);
    }

    @PostMapping("/addProstorijaLokal/{lokalId}")
    public ResponseEntity<Prostorija> dodajProstorijuZaLokal(@ModelAttribute ProstorijaDTO prostorijaDTO, @PathVariable Long lokalId) {
        Prostorija novaProstorija = prostorijeService.addProstorijaLokal(prostorijaDTO, lokalId);
        return new ResponseEntity<>(novaProstorija, HttpStatus.CREATED);
    }

    @GetMapping("/getProstorije/{stanId}")
    public List<Prostorija> getProstorije(@PathVariable Long stanId){
        return prostorijeService.listaProstorijaZaObjekat(stanId);
    }

    @GetMapping("/getProstorijeLokal/{lokalId}")
    public List<Prostorija> getProstorijeZaLokal(@PathVariable Long lokalId){
        return prostorijeService.listaProstorijaZaLokal(lokalId);
    }

    @PutMapping("/updateProstorija/{prostorijaId}")
    public ResponseEntity<Prostorija> updateProstorija(@PathVariable Long prostorijaId, @ModelAttribute ProstorijaDTO prostorijaDTO){
        Prostorija prostorijaUpdated = prostorijeService.updateProstorija(prostorijaDTO, prostorijaId);
        if(prostorijaUpdated == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(prostorijaUpdated);
    }

    @DeleteMapping("/deleteProstorija/{prostorijaId}")
    public ResponseEntity<?> deleteProstorija(@PathVariable Long prostorijaId){
        return prostorijeService.deleteProstorija(prostorijaId);
    }



    @PostMapping("/uploadGallery")
    public ResponseEntity<?> uploadGallery(@ModelAttribute GalerijaDTO galerijaDTO) {
        try {
            galerijaService.saveGallery(galerijaDTO);
            return ResponseEntity.ok("Images uploaded successfully!");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error uploading images");
        }
    }


    @GetMapping("/getExistingImages")
    public ResponseEntity<List<String>> getExistingImages(@RequestParam("tip") String tip) {
        List<String> imageUrls = galerijaService.getExistingImages(tip);
        return ResponseEntity.ok(imageUrls);
    }

    @DeleteMapping("/deleteImage")
    public ResponseEntity<?> deleteImage(@RequestParam("imageUrl") String imageUrl, @RequestParam("tip") String tip) {
        try {
            galerijaService.deleteImage(imageUrl, tip);
            return ResponseEntity.ok("Image deleted successfully!");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error deleting image");
        }
    }








}
