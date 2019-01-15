package Domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Conturi", schema = "dbo", catalog = "Catalog")
public class ConturiEntity implements HasID<Integer>{
    private Integer idCont;
    private String username;
    private String password;
    private String drepturi;
    private Integer idUtilizator;

    @Override
    @Id
    @Column(name = "idCont")
    public Integer getID() {
        return idCont;
    }

    public void setID(Integer idCont) {
        this.idCont = idCont;
    }

    @Basic
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "drepturi")
    public String getDrepturi() {
        return drepturi;
    }

    public void setDrepturi(String drepturi) {
        this.drepturi = drepturi;
    }

    @Basic
    @Column(name = "id_utilizator")
    public Integer getIdUtilizator() {
        return idUtilizator;
    }

    public void setIdUtilizator(Integer idUtilizator) {
        this.idUtilizator = idUtilizator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConturiEntity that = (ConturiEntity) o;
        return idCont == that.idCont &&
                idUtilizator == that.idUtilizator &&
                Objects.equals(username, that.username) &&
                Objects.equals(password, that.password) &&
                Objects.equals(drepturi, that.drepturi);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCont, username, password, drepturi, idUtilizator);
    }
}
