package rs.rapidinvest.rapid.DTO;

import org.springframework.web.multipart.MultipartFile;

public class LokalDTO {
    private Long id;

    private Long objekatId;
    private String naziv;
    private Boolean prodaja;
    private Boolean izdavanje;
    private Double kvadratura;
    private String opisLokal;
    private MultipartFile naslovnaSlikaLokal;
    private MultipartFile slika1Lokal;
    private MultipartFile slika2Lokal;
    private MultipartFile slika3Lokal;

    // Konstruktor, getteri i setteri

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getObjekatId() {
        return objekatId;
    }

    public void setObjekatId(Long objekatId) {
        this.objekatId = objekatId;
    }

    public Boolean getProdaja() {
        return prodaja;
    }

    public void setProdaja(Boolean prodaja) {
        this.prodaja = prodaja;
    }

    public Boolean getIzdavanje() {
        return izdavanje;
    }

    public void setIzdavanje(Boolean izdavanje) {
        this.izdavanje = izdavanje;
    }

    public Double getKvadratura() {
        return kvadratura;
    }

    public void setKvadratura(Double kvadratura) {
        this.kvadratura = kvadratura;
    }

    public String getOpisLokal() {
        return opisLokal;
    }

    public void setOpisLokal(String opisLokal) {
        this.opisLokal = opisLokal;
    }

    public MultipartFile getNaslovnaSlikaLokal() {
        return naslovnaSlikaLokal;
    }

    public void setNaslovnaSlikaLokal(MultipartFile naslovnaSlikaLokal) {
        this.naslovnaSlikaLokal = naslovnaSlikaLokal;
    }

    public MultipartFile getSlika1Lokal() {
        return slika1Lokal;
    }

    public void setSlika1Lokal(MultipartFile slika1Lokal) {
        this.slika1Lokal = slika1Lokal;
    }

    public MultipartFile getSlika2Lokal() {
        return slika2Lokal;
    }

    public void setSlika2Lokal(MultipartFile slika2Lokal) {
        this.slika2Lokal = slika2Lokal;
    }

    public MultipartFile getSlika3Lokal() {
        return slika3Lokal;
    }

    public void setSlika3Lokal(MultipartFile slika3Lokal) {
        this.slika3Lokal = slika3Lokal;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }
}

