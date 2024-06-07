package rs.rapidinvest.rapid.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Galerija {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tip;

    @ElementCollection
    private List<String> putanjeDoSlika;

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

    public List<String> getPutanjeDoSlika() {
        return putanjeDoSlika;
    }

    public void setPutanjeDoSlika(List<String> putanjeDoSlika) {
        this.putanjeDoSlika = putanjeDoSlika;
    }
}
