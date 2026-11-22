package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Issue;
import models.common.ErrorResponseModel;
import models.persona.PersonaRequestModel;
import models.persona.PersonaResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import tests.testdata.TestDataPersona;

import java.util.List;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;

public class PersonaTests extends TestBase {
    TestDataPersona testData = new TestDataPersona();

    @Test
    @DisplayName("E2E CRUD: Создание, получение, обновление и удаление персоны")
    public void personaFullCrudLifecycleTest() {
        PersonaRequestModel createRequest = PersonaRequestModel.builder()
                .name(testData.personaName)
                .image(testData.personaImage)
                .disabled(false)
                .build();

        // 1. Create
        String createdId = api.persona.createPersona(createRequest);

        // 2. Read & Verify
        PersonaResponseModel createdPersona = api.persona.getPersonaById(createdId);
        step("Проверка данных созданной персоны", () -> {
            assertThat(createdPersona.getId()).isEqualTo(createdId);
            assertThat(createdPersona.getName()).isEqualTo(createRequest.getName());
            assertThat(createdPersona.getImage()).isEqualTo(createRequest.getImage());
            assertThat(createdPersona.getDisabled()).isFalse();
        });

        // 3. Update (PATCH)
        PersonaRequestModel patchRequest = PersonaRequestModel.builder()
                .name(testData.updatedPersonaName)
                .disabled(true)
                .build();
        api.persona.patchPersona(createdId, patchRequest);

        PersonaResponseModel updatedPersona = api.persona.getPersonaById(createdId);
        step("Проверка обновлённых полей персоны", () -> {
            assertThat(updatedPersona.getName()).isEqualTo(patchRequest.getName());
            assertThat(updatedPersona.getDisabled()).isTrue();
            assertThat(updatedPersona.getImage()).isEqualTo(createRequest.getImage());
        });

        // 4. Delete & Verify removal
        api.persona.deletePersona(createdId);
        api.persona.getNonExistentPersona(createdId);
    }

    @Test
    @DisplayName("Создание персоны с чужим/несуществующим UUID возвращает 409 Conflict")
    public void createPersonaWithConflictUuidTest() {
        PersonaRequestModel conflictRequest = PersonaRequestModel.builder()
                .id(testData.personaId)
                .name(testData.personaName)
                .image(testData.personaImage)
                .disabled(false)
                .build();

        ErrorResponseModel error = api.persona.createPersonaWithConflict(conflictRequest);

        step("Проверка ответа 409 Conflict", () -> {
            assertThat(error.getStatus()).isEqualTo(409);
            assertThat(error.getError()).isEqualTo("Conflict");
            assertThat(error.getMessage()).contains("Данные уже изменили в другом месте");
        });
    }

    @Test
    @DisplayName("Удаление несуществующей персоны возвращает 404 Not Found")
    public void deleteNonExistentPersonaTest() {
        api.persona.deleteNonExistentPersona(testData.personaId);
    }

    @Test
    @DisplayName("Получение списка всех персон содержит созданную персону")
    public void getAllPersonasListTest() {
        PersonaRequestModel request = PersonaRequestModel.builder()
                .name(testData.personaName)
                .image(testData.personaImage)
                .disabled(false)
                .build();

        String createdId = api.persona.createPersona(request);

        try {
            List<PersonaResponseModel> personas = api.persona.getAllPersonas();

            step("Проверка наличия созданной персоны в общем списке", () -> {
                assertThat(personas).isNotEmpty();
                assertThat(personas)
                        .anySatisfy(persona -> {
                            assertThat(persona.getId()).isEqualTo(createdId);
                            assertThat(persona.getName()).isEqualTo(request.getName());
                            assertThat(persona.getImage()).isEqualTo(request.getImage());
                            assertThat(persona.getDisabled()).isFalse();
                        });
            });
        } finally {
            api.persona.deletePersona(createdId);
        }
    }

    @ParameterizedTest(name = "GET /persona/{0} с невалидным UUID возвращает 400")
    @MethodSource("tests.testdata.TestDataPersona#invalidUuidList")
    @Issue("TTG-101")
    @Description("Ожидается 400 Bad Request, но сервер падает с 500 Internal Server Error при передаче невалидного UUID")
    @DisplayName("GET запрос с невалидным форматом UUID возвращает 400 Bad Request")
    public void getPersonaWithInvalidUuidTest(String invalidId) {
        ErrorResponseModel error = api.persona.getPersonaWithInvalidId(invalidId);

        step("Проверка ответа 400 Bad Request", () -> {
            assertThat(error.getStatus()).isEqualTo(400);
            assertThat(error.getMessage()).isNotBlank();
        });
    }

    @Test
    @Issue("TTG-101")
    @Description("Ожидается 400 Bad Request, но сервер падает с 500 Internal Server Error при передаче невалидного UUID")
    @DisplayName("PATCH запрос с невалидным форматом UUID возвращает 400 Bad Request")
    public void patchPersonaWithInvalidUuidTest() {
        PersonaRequestModel body = PersonaRequestModel.builder()
                .name(testData.personaName)
                .build();

        ErrorResponseModel error = api.persona.patchPersonaWithInvalidId(testData.invalidPersonaId, body);

        step("Проверка ответа 400 Bad Request", () -> {
            assertThat(error.getStatus()).isEqualTo(400);
            assertThat(error.getMessage()).isNotBlank();
        });
    }

    @Test
    @Issue("TTG-101")
    @Description("Ожидается 400 Bad Request, но сервер падает с 500 Internal Server Error при передаче невалидного UUID")
    @DisplayName("DELETE запрос с невалидным форматом UUID возвращает 400 Bad Request")
    public void deletePersonaWithInvalidUuidTest() {
        ErrorResponseModel error = api.persona.deletePersonaWithInvalidId(testData.invalidPersonaId);

        step("Проверка ответа 400 Bad Request", () -> {
            assertThat(error.getStatus()).isEqualTo(400);
            assertThat(error.getMessage()).isNotBlank();
        });
    }

}
