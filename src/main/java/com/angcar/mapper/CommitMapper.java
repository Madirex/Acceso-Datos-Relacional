package com.angcar.mapper;

import com.angcar.dto.CommitDTO;
import com.angcar.model.Commit;

public class CommitMapper extends BaseMapper<Commit, CommitDTO>{
    @Override
    public Commit fromDTO(CommitDTO item) {
        return Commit.builder()
                .uuid_commit(item.getUuid_commit())
                .titulo(item.getTitulo())
                .texto(item.getTexto())
                .fecha(item.getFecha())
                .repositorio(item.getRepositorioTo().getUuid_repositorio())
                .proyecto(item.getProyectoTo().getUuid_proyecto())
                .autor_commit(item.getAutor_commitTo().getUuid_programador())
                .issue_procedente(item.getIssue_procedenteTo().getUuid_issue())
                .build();
    }

    @Override
    public CommitDTO toDTO(Commit item) {
        return CommitDTO.builder()
                .uuid_commit(item.getUuid_commit())
                .titulo(item.getTitulo())
                .texto(item.getTexto())
                .fecha(item.getFecha())
                .repositorio(item.getRepositorio())
                .proyecto(item.getProyecto())
                .autor_commit(item.getAutor_commit())
                .issue_procedente(item.getIssue_procedente())
                .build();
    }
}
