package rs.rapidinvest.rapid.DTO;

import java.util.List;

public class GarazaFilterDTO {

    private List<String> objekat;
    private List<String> tip;

    public List<String> getObjekat() {
        return objekat;
    }

    public void setObjekat(List<String> objekat) {
        this.objekat = objekat;
    }

    public List<String> getTip() {
        return tip;
    }

    public void setTip(List<String> tip) {
        this.tip = tip;
    }
}
