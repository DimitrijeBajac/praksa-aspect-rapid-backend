package rs.rapidinvest.rapid.DTO;

import org.springframework.web.multipart.MultipartFile;

public class StanDTO {
    private Long objekatId;
    private Integer sobnost;
    private Double kvadratura;
    private String opis;
    private Integer sprat;
    private Integer brojStana;
    private  String tip;

    private  String spratnost;
    private boolean dostupnost;
    private MultipartFile slika1;
    private MultipartFile slika2;
    private MultipartFile render;
    private MultipartFile slikaDupleks1;
    private MultipartFile slikaDupleks2;
    private MultipartFile renderDupleks;


    // Getters and setters


    public Long getObjekatId() {
        return objekatId;
    }

    public void setObjekatId(Long objekatId) {
        this.objekatId = objekatId;
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

    public boolean isDostupnost() {
        return dostupnost;
    }

    public void setDostupnost(boolean dostupnost) {
        this.dostupnost = dostupnost;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public MultipartFile getSlika1() {
        return slika1;
    }

    public void setSlika1(MultipartFile slika1) {
        this.slika1 = slika1;
    }

    public MultipartFile getSlika2() {
        return slika2;
    }

    public void setSlika2(MultipartFile slika2) {
        this.slika2 = slika2;
    }

    public MultipartFile getRender() {
        return render;
    }

    public void setRender(MultipartFile render) {
        this.render = render;
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

    public MultipartFile getSlikaDupleks1() {
        return slikaDupleks1;
    }

    public void setSlikaDupleks1(MultipartFile slikaDupleks1) {
        this.slikaDupleks1 = slikaDupleks1;
    }

    public MultipartFile getSlikaDupleks2() {
        return slikaDupleks2;
    }

    public void setSlikaDupleks2(MultipartFile slikaDupleks2) {
        this.slikaDupleks2 = slikaDupleks2;
    }

    public MultipartFile getRenderDupleks() {
        return renderDupleks;
    }

    public void setRenderDupleks(MultipartFile renderDupleks) {
        this.renderDupleks = renderDupleks;
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

