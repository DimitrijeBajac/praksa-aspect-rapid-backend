package rs.rapidinvest.rapid.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Stan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "objekat_id")
    private Objekat objekat;

    private Integer sobnost;
    private Double kvadratura;
    private String opis;
    private Integer sprat;
    private Integer brojStana;

    private String spratnost;

    private String slikaPutanja;

    private Boolean dostupnost;

    private String slikaPutanja1;
    private String slikaPutanja2;
    private String renderPutanja;

    private String slikaDupleksPutanja1;

    private String slikaDupleksPutanja2;

    private String renderDupleksPutanja;

    private String tip;

    private String pdfPutanja;

    @JsonIgnore
    @OneToMany(mappedBy = "stan", cascade = CascadeType.PERSIST,
            orphanRemoval = true)
    private List<Prostorija> prostorije;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Objekat getObjekat() {
        return objekat;
    }

    public void setObjekat(Objekat objekat) {
        this.objekat = objekat;
    }

    public Integer getSobnost() {
        return sobnost;
    }

    public void setSobnost(Integer sobnost) {
        this.sobnost = sobnost;
    }

    public Double getKvadratura() {
        return kvadratura;
    }

    public void setKvadratura(Double kvadratura) {
        this.kvadratura = kvadratura;
    }

    public String getSlikaPutanja() {
        return slikaPutanja;
    }

    public void setSlikaPutanja(String slikaPutanja) {
        this.slikaPutanja = slikaPutanja;
    }

    public Boolean getDostupnost() {
        return dostupnost;
    }

    public void setDostupnost(Boolean dostupnost) {
        this.dostupnost = dostupnost;
    }

    public List<Prostorija> getProstorije() {
        return prostorije;
    }

    public void setProstorije(List<Prostorija> prostorije) {
        this.prostorije = prostorije;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
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

    public String getRenderPutanja() {
        return renderPutanja;
    }

    public void setRenderPutanja(String renderPutanja) {
        this.renderPutanja = renderPutanja;
    }

    public Integer getSprat() {
        return sprat;
    }

    public void setSprat(Integer sprat) {
        this.sprat = sprat;
    }

    public Integer getBrojStana() {
        return brojStana;
    }

    public void setBrojStana(Integer brojStana) {
        this.brojStana = brojStana;
    }

    public String getPdfPutanja() {
        return pdfPutanja;
    }

    public void setPdfPutanja(String pdfPutanja) {
        this.pdfPutanja = pdfPutanja;
    }


    public String getSlikaDupleksPutanja1() {
        return slikaDupleksPutanja1;
    }

    public void setSlikaDupleksPutanja1(String slikaDupleksPutanja1) {
        this.slikaDupleksPutanja1 = slikaDupleksPutanja1;
    }

    public String getSlikaDupleksPutanja2() {
        return slikaDupleksPutanja2;
    }

    public void setSlikaDupleksPutanja2(String slikaDupleksPutanja2) {
        this.slikaDupleksPutanja2 = slikaDupleksPutanja2;
    }

    public String getRenderDupleksPutanja() {
        return renderDupleksPutanja;
    }

    public void setRenderDupleksPutanja(String renderDupleksPutanja) {
        this.renderDupleksPutanja = renderDupleksPutanja;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getSpratnost() {
        return spratnost;
    }

    public void setSpratnost(String spratnost) {
        this.spratnost = spratnost;
    }
}
