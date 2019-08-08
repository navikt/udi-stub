package no.nav.registre.core.database.model.opphold;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import no.nav.registre.core.database.model.Periode;
import no.udi.mt_1067_nav_data.v1.OppholdstillatelseKategori;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class OppholdSammeVilkaar {

    @Embedded
    @AttributeOverrides(
            value = {
                    @AttributeOverride(name = "fra", column = @Column(name = "opphold_samme_vilkaar_fra")),
                    @AttributeOverride(name = "til", column = @Column(name = "opphold_samme_vilkaar_til")),
            }
    )
    private Periode oppholdSammeVilkaarPeriode;
    private Date oppholdSammeVilkaarEffektuering;

    @AttributeOverride(name = "value", column = @Column(name = "oppholdstillatelse_value"))
    private OppholdstillatelseKategori oppholdstillatelseType;

    private Date oppholdstillatelseVedtaksDato;
}