package no.nav.registre.core.database.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import no.udi.mt_1067_nav_data.v1.JaNeiUavklart;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.sql.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@Table(name = "personer")
public class Person {

    @Id
    private String fnr;

    @Embedded
    private PersonNavn navn;

    @Embedded
    private MangelfullDato foedselsDato;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "person")
    @JsonManagedReference
    private List<Alias> aliaser;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "person")
    @JsonManagedReference
    private List<Avgjoerelse> avgjoerelser;

    @OneToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "person"
    )
    @Builder.Default
    @JsonManagedReference
    private ArbeidsAdgang arbeidsAdgang = null;

    @OneToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "person"
    )
    @Builder.Default
    @JsonManagedReference
    private OppholdsStatus oppholdsStatus = null;

    private boolean avgjoerelseUavklart;

    private Boolean oppholdsTilatelse;
    private Boolean flyktning;

    private JaNeiUavklart soeksnadOmBeskyttelseUnderBehandling;
    private Date soeknadsDato;

    private void setSoeknadsDato(Date date) {
        soeknadsDato = new Date(date.toInstant().getEpochSecond());
    }

    private Date getSoeknadsDato() {
        return new Date(soeknadsDato.toInstant().getEpochSecond());
    }
}
