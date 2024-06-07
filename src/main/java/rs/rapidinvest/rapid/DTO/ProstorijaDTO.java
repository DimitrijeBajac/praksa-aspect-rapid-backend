package rs.rapidinvest.rapid.DTO;

public class ProstorijaDTO {
    private String naziv;

    private Integer nivo;
    private Double kvadraturaProstorije;

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

    public Integer getNivo() {
        return nivo;
    }

    public void setNivo(Integer nivo) {
        this.nivo = nivo;
    }
}
