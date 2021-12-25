package com.angcar.mapper;

import com.angcar.dto.IssueDTO;
import com.angcar.model.Issue;

public class IssueMapper extends BaseMapper<Issue, IssueDTO>{
    @Override
    public Issue fromDTO(IssueDTO item) {
        return Issue.builder()
                .uuid_issue(item.getUuid_issue())
                .titulo(item.getTitulo())
                .texto(item.getTexto())
                .fecha(item.getFecha())
                .proyecto(item.getProyecto())
                .repositorio_asignado(item.getRepositorio_asignado())
                .es_acabado(item.isEs_acabado())
                .build();
    }

    @Override
    public IssueDTO toDTO(Issue item) {
        return IssueDTO.builder()
                .uuid_issue(item.getUuid_issue())
                .titulo(item.getTitulo())
                .texto(item.getTexto())
                .fecha(item.getFecha())
                .proyecto(item.getProyecto())
                .repositorio_asignado(item.getRepositorio_asignado())
                .es_acabado(item.isEsAcabado())
                .build();
    }
}
