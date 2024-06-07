package rs.rapidinvest.rapid.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Objekat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String naziv;

    private String adresa;

    private String mesto;

    private String tekst1;

    private String tekst2;

    private Boolean aktuelan;

    private String putanjaCoverSlika;

    @JsonIgnore
    @OneToMany(mappedBy = "objekat",
            cascade = CascadeType.PERSIST,
            orphanRemoval = true)
    private List<Stan> stanovi;

    @JsonIgnore
    @OneToMany(mappedBy = "objekat",
            cascade = CascadeType.PERSIST,
            orphanRemoval = true)
    private List<Lokal> lokali;

    @JsonIgnore
    @OneToMany(mappedBy = "objekat",
            cascade = CascadeType.PERSIST,
            orphanRemoval = true)
    private List<Garaza> garaze;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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


    public List<Stan> getStanovi() {
        return stanovi;
    }

    public void setStanovi(List<Stan> stanovi) {
        this.stanovi = stanovi;
    }

    public List<Lokal> getLokali() {
        return lokali;
    }

    public void setLokali(List<Lokal> lokali) {
        this.lokali = lokali;
    }

    public List<Garaza> getGaraze() {
        return garaze;
    }

    public void setGaraze(List<Garaza> garaze) {
        this.garaze = garaze;
    }

    public Boolean getAktuelan() {
        return aktuelan;
    }

    public void setAktuelan(Boolean aktuelan) {
        this.aktuelan = aktuelan;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getPutanjaCoverSlika() {
        return putanjaCoverSlika;
    }

    public void setPutanjaCoverSlika(String putanjaCoverSlika) {
        this.putanjaCoverSlika = putanjaCoverSlika;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }
}
