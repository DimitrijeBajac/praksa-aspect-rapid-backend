package rs.rapidinvest.rapid.model;

import jakarta.persistence.*;

@Entity
public class Garaza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "objekat_id")
    private Objekat objekat;

    private String naziv;

    private String tip;
    private Double kvadratura;

    private String opis;

    private Boolean dostupnost;

    private String pdfPutanja;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
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


    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public Boolean getDostupnost() {
        return dostupnost;
    }

    public void setDostupnost(Boolean dostupnost) {
        this.dostupnost = dostupnost;
    }

    public String getPdfPutanja() {
        return pdfPutanja;
    }

    public void setPdfPutanja(String pdfPutanja) {
        this.pdfPutanja = pdfPutanja;
    }
}
