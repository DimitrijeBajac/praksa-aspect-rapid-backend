package rs.rapidinvest.rapid.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import rs.rapidinvest.rapid.DTO.StanDTO;
import rs.rapidinvest.rapid.DTO.StanFIlterDTO;
import rs.rapidinvest.rapid.model.Stan;
import rs.rapidinvest.rapid.service.StanService;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/stan")
@CrossOrigin("http://127.0.0.1:5500")
public class StanController {

    @Autowired
    private StanService stanService;

    @RequestMapping("/addStan")
    public ResponseEntity<Stan> addStan(@ModelAttribute StanDTO stanDTO) throws IOException {
        Stan noviStan = stanService.addStan(stanDTO);
        return new ResponseEntity<>(noviStan, HttpStatus.CREATED);
    }
    @RequestMapping("/getAllStanovi")
    public ResponseEntity<List<Stan>> getAllStanovi(){
        List<Stan> stanovi = stanService.getAllStanovi();
        return new ResponseEntity<>(stanovi, HttpStatus.OK);
    }

    @RequestMapping("/getAllDostupniStanovi")
    public ResponseEntity<List<Stan>> getAllDostupniStanovi(){
        List<Stan> stanovi = stanService.sviDostupniStanovi();
        return new ResponseEntity<>(stanovi, HttpStatus.OK);
    }


    @DeleteMapping("/deleteStan/{id}")
    public void deleteStan(@PathVariable Long id){
        stanService.deleteStan(id);
    }

    @GetMapping("stanInfo/{stanId}")
    public Stan stanInfo(@PathVariable Long stanId){
        return stanService.findById(stanId);
    }

    @PutMapping("updateStan/{stanId}")
    public ResponseEntity<Stan> updateStan(@PathVariable Long stanId, @ModelAttribute StanDTO stanDTO) throws IOException {
        Stan updatedStan = stanService.updateStan(stanId, stanDTO);
        return ResponseEntity.ok(updatedStan);
    }

    @DeleteMapping("/deleteImage")
    public ResponseEntity<?> deleteImage(@RequestParam("imageUrl") String imageUrl, @RequestParam("id") Long id) {
        try {
            stanService.deleteImage(imageUrl, id);
            return ResponseEntity.ok("Image deleted successfully!");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error deleting image");
        }
    }

    @PostMapping("/updateImage")
    public ResponseEntity<String> updateImage(@RequestParam("image") MultipartFile image,
                                              @RequestParam("imageId") String imageId,
                                              @RequestParam("stanId") Long stanId) {
        try {
            stanService.updateImg(image, imageId, stanId);
            return new ResponseEntity<>("Image uploaded successfully", HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("Failed to upload image", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/uploadPdf/{id}")
    public ResponseEntity<String> uploadPdf(@PathVariable Long id, @RequestParam("pdf") MultipartFile file) {
        try {
            String pdfPath = stanService.savePdf(id, file);
            return ResponseEntity.ok(pdfPath);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/downloadPdf/{id}")
    public ResponseEntity<Resource> downloadPdf(@PathVariable Long id) throws IOException {
        Resource file = stanService.loadPdfResource(id);
        String filename = Paths.get(file.getURI()).getFileName().toString();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .body(file);
    }

    @DeleteMapping("/deletePdf")
    public ResponseEntity<?> deletePdf(@RequestParam("pdfUrl") String pdfUrl, @RequestParam("id") Long id) {
        try {
            stanService.deletePdf(pdfUrl, id);
            return ResponseEntity.ok("Image deleted successfully!");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error deleting image");
        }
    }

    @PostMapping("/filter")
    public List<Stan> filterStans(@RequestBody StanFIlterDTO filterDTO) {
        return stanService.filterStans(filterDTO);
    }


}
