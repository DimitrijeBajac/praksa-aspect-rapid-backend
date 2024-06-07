package rs.rapidinvest.rapid.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Lokal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String naziv;
    private Boolean prodaja;
    private Boolean izdavanje;
    private Double kvadratura;

    private String opis;

    private String naslovnaSlka;

    private String slikaPutanja1;
    private String slikaPutanja2;
    private String slikaPutanja3;

    private String pdfPutanja;

    @ManyToOne
    @JoinColumn(name = "objekat_id")
    private Objekat objekat;


    @JsonIgnore
    @OneToMany(mappedBy = "lokal", cascade = CascadeType.PERSIST,
            orphanRemoval = true)
    private List<Prostorija> prostorije;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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


    public Objekat getObjekat() {
        return objekat;
    }

    public void setObjekat(Objekat objekat) {
        this.objekat = objekat;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getNaslovnaSlka() {
        return naslovnaSlka;
    }

    public void setNaslovnaSlka(String naslovnaSlka) {
        this.naslovnaSlka = naslovnaSlka;
    }

    public String getSlikaPutanja1() {
        return slikaPutanja1;
    }

    public void setSlikaPutanja1(String slikaPutanja1) {
        this.slikaPutanja1 = slikaPutanja1;
    }

    public String getSlikaPutanja2() {
        return slikaPutanja2;
    }

    public void setSlikaPutanja2(String slikaPutanja2) {
        this.slikaPutanja2 = slikaPutanja2;
    }

    public String getSlikaPutanja3() {
        return slikaPutanja3;
    }

    public void setSlikaPutanja3(String slikaPutanja3) {
        this.slikaPutanja3 = slikaPutanja3;
    }

    public List<Prostorija> getProstorije() {
        return prostorije;
    }

    public void setProstorije(List<Prostorija> prostorije) {
        this.prostorije = prostorije;
    }

    public String getPdfPutanja() {
        return pdfPutanja;
    }

    public void setPdfPutanja(String pdfPutanja) {
        this.pdfPutanja = pdfPutanja;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }
}
