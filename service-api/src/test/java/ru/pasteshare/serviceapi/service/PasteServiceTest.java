package ru.pasteshare.serviceapi.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.pasteshare.serviceapi.dto.request.PasteCreateDTO;
import ru.pasteshare.serviceapi.dto.response.UserPasteDTO;
import ru.pasteshare.serviceapi.exception.NoAccessException;
import ru.pasteshare.serviceapi.model.Paste;
import ru.pasteshare.serviceapi.model.User;
import ru.pasteshare.serviceapi.repository.PasteRepository;
import ru.pasteshare.serviceapi.security.UserInfo;
import ru.pasteshare.serviceapi.service.impl.PasteServiceImpl;
import ru.pasteshare.serviceapi.service.mapper.PasteMapper;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PasteServiceTest {
    @Mock
    private AccessControlService accessControlService;
    @Mock
    private PasteRepository pasteRepository;
    @Mock
    private PasteMapper pasteMapper;
    @InjectMocks
    private PasteServiceImpl pasteService;

    @Test
    public void testCreatePaste() throws NoAccessException {
        // Arrange
        UUID userId = UUID.randomUUID();
        PasteCreateDTO pasteCreateDTO = new PasteCreateDTO();
//        pasteCreateDTO.setUserId(userId);
        // Остальные необходимые поля для pasteCreateDTO

        Paste paste = new Paste();
        // Установите ожидаемые значения для paste

        User user = new User();
        // Установите ожидаемые значения для user

        Paste savedPaste = new Paste();
        // Установите ожидаемые значения для savedPaste

        UserPasteDTO expectedUserPasteDTO = new UserPasteDTO();
        // Установите ожидаемые значения для expectedUserPasteDTO

        when(accessControlService.getUserInfo()).thenReturn(new UserInfo(user));
        when(pasteMapper.toPaste(pasteCreateDTO)).thenReturn(paste);
        when(pasteRepository.save(paste)).thenReturn(savedPaste);
        when(pasteMapper.toUserPasteDTO(savedPaste)).thenReturn(expectedUserPasteDTO);

        // Act
        UserPasteDTO result = pasteService.createPaste(pasteCreateDTO);

        // Assert
        assertNotNull(result);
        assertEquals(expectedUserPasteDTO, result);
        // Добавьте другие проверки на ожидаемые вызовы методов и значения полей
    }
}
