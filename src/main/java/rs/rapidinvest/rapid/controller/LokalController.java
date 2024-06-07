package rs.rapidinvest.rapid.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import rs.rapidinvest.rapid.DTO.LokalDTO;
import rs.rapidinvest.rapid.DTO.LokalFilterDTO;
import rs.rapidinvest.rapid.DTO.ObjekatDTO;
import rs.rapidinvest.rapid.DTO.StanFIlterDTO;
import rs.rapidinvest.rapid.model.Lokal;
import rs.rapidinvest.rapid.model.Objekat;
import rs.rapidinvest.rapid.model.Stan;
import rs.rapidinvest.rapid.service.LokalService;
import rs.rapidinvest.rapid.service.StanService;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/lokal")
public class LokalController {

    @Autowired
    private LokalService lokalService;

    @PostMapping("/addLokal")
    public ResponseEntity<Lokal> addLokal(@ModelAttribute LokalDTO lokalDTO) throws IOException {
        Lokal newLokal = lokalService.dodajLokal(lokalDTO);
        return new ResponseEntity<>(newLokal, HttpStatus.CREATED);
    }
    @GetMapping("/getAllLokali")
    public ResponseEntity<List<Lokal>> getAllLokali(){
        List<Lokal> lokali = lokalService.getAllLokali();
        return new ResponseEntity<>(lokali, HttpStatus.OK);
    }
    @DeleteMapping("/deleteLokal/{id}")
    public void deleteLokal(@PathVariable Long id) {
        lokalService.deleteLokal(id);
    }

    @PutMapping("updateLokal/{lokalId}")
    public ResponseEntity<Lokal> updateLokal(@PathVariable Long lokalId, @ModelAttribute LokalDTO lokalDTO) {
        Lokal updatedLokal = lokalService.updateLokal(lokalDTO, lokalId);
        return ResponseEntity.ok(updatedLokal);
    }

    @GetMapping("/lokalInfo/{lokalId}")
    public Lokal getLokalInfo(@PathVariable Long lokalId){
        return lokalService.getLokalById(lokalId);
    }


    @DeleteMapping("/deleteImage")
    public ResponseEntity<?> deleteImage(@RequestParam("imageUrl") String imageUrl, @RequestParam("id") Long id) {
        try {
            lokalService.deleteImage(imageUrl, id);
            return ResponseEntity.ok("Image deleted successfully!");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error deleting image");
        }
    }

    @PostMapping("/updateImage")
    public ResponseEntity<String> updateImage(@RequestParam("image") MultipartFile image,
                                              @RequestParam("imageId") String imageId,
                                              @RequestParam("lokalId") Long stanId) {
        try {
            lokalService.updateImg(image, imageId, stanId);
            return new ResponseEntity<>("Image uploaded successfully", HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("Failed to upload image", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/uploadPdf/{id}")
    public ResponseEntity<String> uploadPdf(@PathVariable Long id, @RequestParam("pdf") MultipartFile file) {
        try {
            String pdfPath = lokalService.savePdf(id, file);
            return ResponseEntity.ok(pdfPath);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/downloadPdf/{id}")
    public ResponseEntity<Resource> downloadPdf(@PathVariable Long id) throws IOException {
        Resource file = lokalService.loadPdfResource(id);
        String filename = Paths.get(file.getURI()).getFileName().toString();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .body(file);
    }

    @DeleteMapping("/deletePdf")
    public ResponseEntity<?> deletePdf(@RequestParam("pdfUrl") String pdfUrl, @RequestParam("id") Long id) {
        try {
            lokalService.deletePdf(pdfUrl, id);
            return ResponseEntity.ok("Image deleted successfully!");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error deleting image");
        }
    }


    @PostMapping("/filter")
    public List<Lokal> filterLokal(@RequestBody LokalFilterDTO filterDTO) {
        return lokalService.filterLokal(filterDTO);
    }




}
