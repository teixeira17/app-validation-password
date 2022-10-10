package com.validation.appvalidation.services;

import com.validation.appvalidation.domain.entities.Validation;
import com.validation.appvalidation.domain.repositories.ValidationRepository;
import com.validation.appvalidation.domain.services.ValidationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class ValidationServicesTest {

    private static final String VALIDLOWERCASE = "Senha inválida. Informe pelo menos uma letra minúscula.";
    private static final String VALIDUPPERCASE = "Senha inválida. Informe pelo menos uma letra maiúscula.";
    private static final String VALIDSPECIALCHARACTER = "Senha inválida. Informe pelo menos um caractere especial.";
    private static final String VALIDLESSTHANNINECHARACTERS = "Senha inválida. Informe pelo menos noves caracteres.";
    private static final String VALIDPASSWORDHASREPEATEDCHARACTER = "Senha inválida. Não informe caracteres repetidos.";

    @InjectMocks
    private ValidationService service;

    @Mock
    private ValidationRepository repository;

    private String password;

    @Test
    public void shouldReturnIsValidTrueWhenHasCorrectPassword() {
        Validation validation = new Validation();
        validation.setValid(true);

        Mockito.when(repository.save(any())).thenReturn(validation);

        validation = service.enterPassword(new Validation("AbTp9!fok"));
        Assertions.assertEquals(true, validation.getValid());
    }

    @Test
    public void shouldReturnIncorrectRequestWhenPasswordHasNoLowerCaseCharacters() {

        Exception exception = Assertions.assertThrows(ResponseStatusException.class, () -> service.enterPassword(new Validation("ABTP9!FOK")));

        String message = ((ResponseStatusException) exception).getReason();

        Assertions.assertTrue(VALIDLOWERCASE.contains(message));
    }

    @Test
    public void shouldReturnIncorrectRequestWhenPasswordHasNoUpperCaseCharacters() {

        Exception exception = Assertions.assertThrows(ResponseStatusException.class, () -> service.enterPassword(new Validation("abtp9!fok")));

        String message = ((ResponseStatusException) exception).getReason();

        Assertions.assertTrue(VALIDUPPERCASE.contains(message));
    }

    @Test
    public void shouldReturnIncorrectPromptWhenPasswordHasNoSpecialCharacters() {

        Exception exception = Assertions.assertThrows(ResponseStatusException.class, () -> service.enterPassword(new Validation("aBtp9RfoK")));

        String message = ((ResponseStatusException) exception).getReason();

        Assertions.assertTrue(VALIDSPECIALCHARACTER.contains(message));
    }

    @Test
    public void shouldReturnIncorrectPromptWhenPasswordIsLessThanNineCharacters() {

        Exception exception = Assertions.assertThrows(ResponseStatusException.class, () -> service.enterPassword(new Validation("ab")));

        String message = ((ResponseStatusException) exception).getReason();

        Assertions.assertTrue(VALIDLESSTHANNINECHARACTERS.contains(message));
    }

    @Test
    public void shouldReturnIncorrectRequestWhenPasswordHasRepeatedCharacter() {

        Exception exception = Assertions.assertThrows(ResponseStatusException.class, () -> service.enterPassword(new Validation("AAAbbbCc")));

        String message = ((ResponseStatusException) exception).getReason();

        Assertions.assertTrue(VALIDPASSWORDHASREPEATEDCHARACTER.contains(message));
    }
}