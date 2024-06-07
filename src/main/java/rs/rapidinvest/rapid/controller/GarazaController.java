package rs.rapidinvest.rapid.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import rs.rapidinvest.rapid.DTO.GarazaDTO;
import rs.rapidinvest.rapid.DTO.GarazaFilterDTO;
import rs.rapidinvest.rapid.DTO.LokalDTO;
import rs.rapidinvest.rapid.DTO.StanFIlterDTO;
import rs.rapidinvest.rapid.model.Garaza;
import rs.rapidinvest.rapid.model.Lokal;
import rs.rapidinvest.rapid.model.Stan;
import rs.rapidinvest.rapid.service.GarazaService;
import rs.rapidinvest.rapid.service.StanService;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/garaza")
public class GarazaController {

    @Autowired
    private GarazaService garazaService;

    @PostMapping("/addGaraza")
    public ResponseEntity<Garaza> addGaraza(@ModelAttribute GarazaDTO garazaDTO){
        Garaza newGaraza = garazaService.addGaraza(garazaDTO);
        return new ResponseEntity<>(newGaraza, HttpStatus.CREATED);
    }
    @GetMapping("/getAllGaraze")
    public ResponseEntity<List<Garaza>> getAllGaraze(){
        List<Garaza> garaze = garazaService.getAllGaraze();
        return new ResponseEntity<>(garaze, HttpStatus.OK);
    }
    @GetMapping("/getAllGarazeDostupne")
    public ResponseEntity<List<Garaza>> getAllGarazeDostupne(){
        List<Garaza> garaze = garazaService.getAllGarazeDostupne();
        return new ResponseEntity<>(garaze, HttpStatus.OK);
    }
    @DeleteMapping("/deleteGaraza/{id}")
    public void deleteStan(@PathVariable Long id){
        garazaService.deleteGaraza(id);
    }

    @PutMapping("/updateGaraza/{garazaId}")
    public ResponseEntity<Garaza> updateGaraza(@PathVariable Long garazaId, @ModelAttribute GarazaDTO garazaDTO) throws IOException {
        Garaza updatedGaraza = garazaService.updateGaraza(garazaDTO, garazaId);
        return ResponseEntity.ok(updatedGaraza);
    }

    @GetMapping("/garazaInfo/{garazaId}")
    public Garaza getGarazaInfo(@PathVariable Long garazaId){
        return garazaService.getGarazaById(garazaId);
    }

    @PostMapping("/uploadPdf/{id}")
    public ResponseEntity<String> uploadPdf(@PathVariable Long id, @RequestParam("pdf") MultipartFile file) {
        try {
            String pdfPath = garazaService.savePdf(id, file);
            return ResponseEntity.ok(pdfPath);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/downloadPdf/{id}")
    public ResponseEntity<Resource> downloadPdf(@PathVariable Long id) throws IOException {
        Resource file = garazaService.loadPdfResource(id);
        String filename = Paths.get(file.getURI()).getFileName().toString();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .body(file);
    }

    @DeleteMapping("/deletePdf")
    public ResponseEntity<?> deletePdf(@RequestParam("pdfUrl") String pdfUrl, @RequestParam("id") Long id) {
        try {
            garazaService.deletePdf(pdfUrl, id);
            return ResponseEntity.ok("Image deleted successfully!");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error deleting image");
        }
    }

    @PostMapping("/filter")
    public List<Garaza> filterGaraze(@RequestBody GarazaFilterDTO filterDTO) {
        return garazaService.filterGaraze(filterDTO);
    }




}


