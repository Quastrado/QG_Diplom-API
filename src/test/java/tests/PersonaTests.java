package tests;

import models.common.ErrorResponseModel;
import models.persona.PersonaRequestModel;
import models.persona.PersonaResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tests.testdata.TestDataPersona;

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
        ErrorResponseModel error = api.persona.deleteNonExistentPersona(testData.personaId);

        step("Проверка ответа 404 Not Found", () -> {
            assertThat(error.getStatus()).isEqualTo(404);
            assertThat(error.getError()).isEqualTo(testData.notFoundErrorText);
            assertThat(error.getMessage()).isNotBlank();
        });
    }
}
