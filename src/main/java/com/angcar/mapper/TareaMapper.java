package com.angcar.mapper;

import com.angcar.dto.TareaDTO;
import com.angcar.model.Tarea;

public class TareaMapper extends BaseMapper<Tarea, TareaDTO> {
    @Override
    public Tarea fromDTO(TareaDTO item) {
        return Tarea.builder()
                .uuid_tarea(item.getUuid_tarea())
                .uuid_programador(item.getUuid_programador())
                .uuid_issue(item.getUuid_issue())
                .build();
    }

    @Override
    public TareaDTO toDTO(Tarea item) {
        return TareaDTO.builder()
                .uuid_tarea(item.getUuid_tarea())
                .uuid_programador(item.getUuid_programador())
                .uuid_issue(item.getUuid_issue())
                .build();
    }
}

