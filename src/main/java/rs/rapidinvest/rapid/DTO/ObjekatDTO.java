package rs.rapidinvest.rapid.DTO;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class ObjekatDTO {

    private String naziv;

    private String mesto;

    private String adresa;

    private String tekst1;

    private String tekst2;

    private MultipartFile coverSlika;

    private boolean aktuelan;

    public String getMesto() {
        return mesto;
    }

    public void setMesto(String mesto) {
        this.mesto = mesto;
    }

    public String getTekst1() {
        return tekst1;
    }

    public void setTekst1(String tekst1) {
        this.tekst1 = tekst1;
    }

    public String getTekst2() {
        return tekst2;
    }

    public void setTekst2(String tekst2) {
        this.tekst2 = tekst2;
    }

    public boolean isAktuelan() {
        return aktuelan;
    }

    public void setAktuelan(boolean aktuelan) {
        this.aktuelan = aktuelan;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public MultipartFile getCoverSlika() {
        return coverSlika;
    }

    public void setCoverSlika(MultipartFile coverSlika) {
        this.coverSlika = coverSlika;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }
}
