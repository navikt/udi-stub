package no.nav.registre.udistub.core.itest;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.matching;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import static no.nav.registre.udistub.core.DefaultTestData.createPersonTo;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import no.nav.registre.udistub.core.database.model.Person;
import no.nav.registre.udistub.core.service.to.PersonTo;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ActiveProfiles;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@ActiveProfiles("itest")
@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
public class ITestBase {

    protected static final PersonTo testpersonTo = createPersonTo();

    @BeforeEach
    public void setUp() throws IOException {
        String tpsfResponseBody = getJsonContentsAsString("tpsfResponse-happy.json");
        stubFor(get(urlMatching("/tpsfstuburl/.*"))
                .withHeader(AUTHORIZATION, matching("test"))
                .willReturn(okJson(tpsfResponseBody)));
    }

    protected String getJsonContentsAsString(String filename) throws IOException {
        File requestBodyFile = new ClassPathResource(filename).getFile();
        return new String(Files.readAllBytes(requestBodyFile.toPath()));
    }
}