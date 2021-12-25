package com.angcar.utils;

import com.angcar.dto.CommitDTO;
import com.angcar.dto.ProgramadorDTO;
import com.angcar.model.Issue;
import com.angcar.model.Proyecto;
import lombok.Data;

import java.util.List;

@Data
public class ProductividadProgramador {
    private ProgramadorDTO programador;
    private List<Proyecto> proyectos;
    private List<CommitDTO> commits;
    private List<Issue> issues;
}
