package rs.rapidinvest.rapid.DTO;

import java.util.List;

public class LokalFilterDTO {

    private List<String> izdavanje;
    private List<String> prodaja;

    private List<String> objekat;
    private List<String> kvadratura;

    public List<String> getIzdavanje() {
        return izdavanje;
    }

    public void setIzdavanje(List<String> izdavanje) {
        this.izdavanje = izdavanje;
    }

    public List<String> getProdaja() {
        return prodaja;
    }

    public void setProdaja(List<String> prodaja) {
        this.prodaja = prodaja;
    }

    public List<String> getObjekat() {
        return objekat;
    }

    public void setObjekat(List<String> objekat) {
        this.objekat = objekat;
    }

    public List<String> getKvadratura() {
        return kvadratura;
    }

    public void setKvadratura(List<String> kvadratura) {
        this.kvadratura = kvadratura;
    }
}
