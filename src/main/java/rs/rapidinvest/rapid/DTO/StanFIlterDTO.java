package rs.rapidinvest.rapid.DTO;

import java.util.List;

public class StanFIlterDTO {

    private List<String> objekat;
    private List<String> sobnost;
    private List<String> kvadratura;
    private List<String> spratnost;

    public List<String> getObjekat() {
        return objekat;
    }

    public void setObjekat(List<String> objekat) {
        this.objekat = objekat;
    }

    public List<String> getSobnost() {
        return sobnost;
    }

    public void setSobnost(List<String> sobnost) {
        this.sobnost = sobnost;
    }

    public List<String> getKvadratura() {
        return kvadratura;
    }

    public void setKvadratura(List<String> kvadratura) {
        this.kvadratura = kvadratura;
    }

    public List<String> getSpratnost() {
        return spratnost;
    }

    public void setSpratnost(List<String> spratnost) {
        this.spratnost = spratnost;
    }
}
