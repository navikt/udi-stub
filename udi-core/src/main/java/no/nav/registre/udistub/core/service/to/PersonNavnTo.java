package no.nav.registre.udistub.core.service.to;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class PersonNavnTo {
    private String fornavn;
    private String mellomnavn;
    private String etternavn;
}