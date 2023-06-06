package ru.pasteshare.serviceapi.service.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.pasteshare.serviceapi.dto.request.PasteCreateDTO;
import ru.pasteshare.serviceapi.model.Paste;

@Component
public class PasteMapper {

    private final ModelMapper modelMapper;

    public PasteMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Paste toPaste(PasteCreateDTO pasteCreateDTO) {
        return modelMapper.map(pasteCreateDTO, Paste.class);
    }
}