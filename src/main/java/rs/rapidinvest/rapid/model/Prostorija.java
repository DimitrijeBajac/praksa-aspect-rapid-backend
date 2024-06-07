package rs.rapidinvest.rapid.model;

import jakarta.persistence.*;

@Entity
public class Prostorija {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String naziv;
    private Integer nivo;
    private Double kvadraturaProstorije;

    @ManyToOne
    @JoinColumn(name = "stan_id")
    private Stan stan;

    @ManyToOne
    @JoinColumn(name = "lokal_id")
    private Lokal lokal;

    // Getteri i setteri izostavljeni radi preglednosti

    public Stan getStan() {
        return stan;
    }

    public void setStan(Stan stan) {
        this.stan = stan;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Double getKvadraturaProstorije() {
        return kvadraturaProstorije;
    }

    public void setKvadraturaProstorije(Double kvadraturaProstorije) {
        this.kvadraturaProstorije = kvadraturaProstorije;
    }

    public Lokal getLokal() {
        return lokal;
    }

    public void setLokal(Lokal lokal) {
        this.lokal = lokal;
    }

    public Integer getNivo() {
        return nivo;
    }

    public void setNivo(Integer nivo) {
        this.nivo = nivo;
    }
}

