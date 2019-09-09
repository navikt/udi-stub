package no.nav.registre.udistub.core.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.String.format;
import no.nav.registre.udistub.core.database.model.Alias;
import no.nav.registre.udistub.core.database.model.Arbeidsadgang;
import no.nav.registre.udistub.core.database.model.Avgjorelse;
import no.nav.registre.udistub.core.database.model.Person;
import no.nav.registre.udistub.core.database.model.PersonNavn;
import no.nav.registre.udistub.core.database.model.opphold.OppholdStatus;
import no.nav.registre.udistub.core.database.repository.AliasRepository;
import no.nav.registre.udistub.core.database.repository.ArbeidsAdgangRepository;
import no.nav.registre.udistub.core.database.repository.AvgjorelseRepository;
import no.nav.registre.udistub.core.database.repository.OppholdStatusRepository;
import no.nav.registre.udistub.core.database.repository.PersonRepository;
import no.nav.registre.udistub.core.exception.NotFoundException;
import no.nav.registre.udistub.core.service.to.UdiAlias;
import no.nav.registre.udistub.core.service.to.UdiAvgjorelse;
import no.nav.registre.udistub.core.service.to.UdiPerson;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonService {

    private final MapperFacade mapperFacade;

    private final PersonRepository personRepository;
    private final AliasRepository aliasRepository;
    private final AvgjorelseRepository avgjorelseRepository;
    private final OppholdStatusRepository oppholdStatusRepository;
    private final ArbeidsAdgangRepository arbeidsAdgangRepository;

    public Optional<UdiPerson> opprettPerson(UdiPerson udiPerson) {

        Person nyPerson = Person.builder()
                .flyktning(udiPerson.getFlyktning())
                .avgjoerelseUavklart(udiPerson.getAvgjoerelseUavklart())
                .harOppholdsTillatelse(udiPerson.getHarOppholdsTillatelse())
                .ident(udiPerson.getIdent())
                .foedselsDato(udiPerson.getFoedselsDato())
                .navn(mapperFacade.map(udiPerson.getNavn(), PersonNavn.class))
                .soeknadOmBeskyttelseUnderBehandling(udiPerson.getSoeknadOmBeskyttelseUnderBehandling())
                .soknadDato(udiPerson.getSoknadDato())
                .build();
        Person person = personRepository.save(nyPerson);
        List<UdiAlias> aliaser = udiPerson.getAliaser();
        if (aliaser != null) {
            aliasRepository.saveAll(aliaser.stream()
                    .map(alias -> mapperFacade.map(alias, Alias.class))
                    .peek(alias -> alias.setPerson(person))
                    .collect(Collectors.toList())
            );
        }
        List<UdiAvgjorelse> avgjoerelser = udiPerson.getAvgjoerelser();
        if (avgjoerelser != null) {
            avgjorelseRepository.saveAll(avgjoerelser.stream()
                    .map(avgjorelse -> mapperFacade.map(avgjorelse, Avgjorelse.class))
                    .peek(avgjorelse -> avgjorelse.setPerson(person))
                    .collect(Collectors.toList())
            );
        }
        OppholdStatus oppholdStatus = mapperFacade.map(udiPerson.getOppholdStatus(), OppholdStatus.class);
        oppholdStatus.setPerson(person);
        oppholdStatusRepository.save(oppholdStatus);

        Arbeidsadgang arbeidsadgang = mapperFacade.map(udiPerson.getArbeidsadgang(), Arbeidsadgang.class);
        arbeidsadgang.setPerson(person);
        arbeidsAdgangRepository.save(arbeidsadgang);

        return personRepository.findById(person.getId()).map(found -> mapperFacade.map(found, UdiPerson.class));
    }

    public Optional<UdiPerson> finnPerson(String ident) {
        return personRepository.findByIdent(ident).map(person -> mapperFacade.map(person, UdiPerson.class));
    }

    public void deletePerson(String ident) {
        Optional<Person> optionalPerson = personRepository.findByIdent(ident);
        if (optionalPerson.isPresent()) {
            personRepository.deleteById(optionalPerson.get().getId());
        } else {
            throw new NotFoundException(format("Kunne ikke slette person med ident:%s, da personen ikke ble funnet", ident));
        }
    }
}
