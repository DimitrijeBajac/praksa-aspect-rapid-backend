package rs.rapidinvest.rapid.DTO;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class GalerijaDTO {

    private String tip;

    private List<MultipartFile> listaSlika;



    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public List<MultipartFile> getListaSlika() {
        return listaSlika;
    }

    public void setListaSlika(List<MultipartFile> listaSlika) {
        this.listaSlika = listaSlika;
    }
}
