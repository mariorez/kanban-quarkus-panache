package org.seariver.kanbanboard.write.adapter.in;

import com.github.jsontemplate.JsonTemplate;
import helper.TestHelper;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static javax.ws.rs.core.Response.Status.CREATED;

@QuarkusTest
public class BucketCreationIT extends TestHelper {

    public static final String RESOURCE_PATH = "/v1/buckets";

    @Test
    void GIVEN_ValidPayload_MUST_ReturnCreated() {

        // setup
        var externalId = UUID.randomUUID().toString();
        var position = faker.number().randomDouble(3, 1, 10);
        var name = faker.pokemon().name();

        var template = String.format("{" +
                "  bucketId : $bucketId," +
                "  position : %s," +
                "  name : $name" +
                "}", position);

        var payload = new JsonTemplate(template)
                .withVar("bucketId", externalId)
                .withVar("name", name)
                .prettyString();

        // verify
        given()
                .contentType(JSON)
                .body(payload).log().body()
                .when()
                .post(RESOURCE_PATH)
                .then()
                .statusCode(CREATED.getStatusCode());
    }
}
