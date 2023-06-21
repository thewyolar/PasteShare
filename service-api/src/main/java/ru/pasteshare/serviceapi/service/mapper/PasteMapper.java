package ru.pasteshare.serviceapi.service.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.pasteshare.serviceapi.dto.request.PasteCreateDTO;
import ru.pasteshare.serviceapi.dto.response.UserPasteDTO;
import ru.pasteshare.serviceapi.model.Paste;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PasteMapper {

    private final ModelMapper modelMapper;

    public PasteMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Paste toPaste(PasteCreateDTO pasteCreateDTO) {
        return modelMapper.map(pasteCreateDTO, Paste.class);
    }

    public UserPasteDTO toUserPasteDTO(Paste paste) {
        return modelMapper.map(paste, UserPasteDTO.class);
    }

    public List<UserPasteDTO> toUserPasteDTOList(List<Paste> pastes) {
        return pastes.stream()
                .map(paste -> modelMapper.map(paste, UserPasteDTO.class))
                .collect(Collectors.toList());
    }
}