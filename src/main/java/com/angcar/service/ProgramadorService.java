package com.angcar.service;

import com.angcar.dto.CommitDTO;
import com.angcar.dto.FichaDTO;
import com.angcar.dto.ProgramadorDTO;
import com.angcar.mapper.ProgramadorMapper;
import com.angcar.model.*;
import com.angcar.repository.*;
import com.angcar.utils.Cifrator;
import com.angcar.utils.ProductividadProgramador;

import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class ProgramadorService extends BaseService<Programador, String, ProgramadorRepository, ProgramadorDTO>{

    ProgramadorMapper mapper = new ProgramadorMapper();

    public ProgramadorService(ProgramadorRepository repository) {
        super(repository);
    }

    public Optional<List<ProductividadProgramador>> getAllProgramadorProductividad() throws SQLException {

        //Agregar programadores
        Optional<List<ProgramadorDTO>> programadores = this.getAllDTO();
        List<ProductividadProgramador> productividadProgramador = new ArrayList<>();

        programadores.ifPresent(ps -> ps.forEach(p -> {
            ProductividadProgramador productividad = new ProductividadProgramador();
            productividad.setProgramador(p);
            productividadProgramador.add(productividad);
        }));

        //Agregar proyectos relacionados
        FichaService fichaService = new FichaService(new FichaRepository());
        Optional<List<FichaDTO>> fichas = fichaService.getAllDTO();

        fichas.ifPresent(fi -> fi.forEach(f -> productividadProgramador.forEach(p -> {
            if (p.getProgramador().getUuid_programador().equals(f.getUuid_programador())){
                List<Proyecto> proyectoList = p.getProyectos();
                if (proyectoList == null) proyectoList = new ArrayList<>();
                if (f.getProyectoTo() != null){
                    proyectoList.add(f.getProyectoTo());
                }
                p.setProyectos(proyectoList);
            }
        })));

        //Agregar issues
        TareaService tareaService = new TareaService(new TareaRepository());
        tareaService.getAllDTO().ifPresent(ta -> ta.forEach(t -> {
            productividadProgramador.forEach(p -> {
                if (p.getProgramador().getUuid_programador().equals(t.getUuid_programador())) {
                    List<Issue> issueList = p.getIssues();
                    if (issueList == null) issueList = new ArrayList<>();
                    if (t.getIssueTo() != null){
                        issueList.add(t.getIssueTo());
                    }
                    p.setIssues(issueList);
                }
            });
        }));

        //Agregar commits
        CommitService commitService = new CommitService(new CommitRepository());

        commitService.getAllDTO().ifPresent(co -> co.forEach(c -> {
            productividadProgramador.forEach(p -> {
                if (p.getProgramador().getUuid_programador().equals(c.getAutor_commit())) {
                    List<CommitDTO> commitList = p.getCommits();
                    if (commitList == null) commitList = new ArrayList<>();
                    commitList.add(c);
                    p.setCommits(commitList);
                }
            });
        }));

        return Optional.of(productividadProgramador);
    }


    private Optional<ProgramadorDTO> getProgramadorDTO(Optional<Programador> programadorOpt) throws SQLException {
        if (programadorOpt.isPresent()){
            Programador programador = programadorOpt.get();
            ProgramadorDTO res = mapper.toDTO(programador);

            Optional<Departamento> departamento = this.getDepartamentoById(programador.getDepartamento());
            departamento.ifPresent(res::setDepartamentoTo);

            return Optional.of(res);
        }else{
            return Optional.empty();
        }
    }

    private Optional<Departamento> getDepartamentoById(String id) throws SQLException {
        DepartamentoService service = new DepartamentoService(new DepartamentoRepository());
        return service.getById(id);
    }

    @Override
    public Optional<List<ProgramadorDTO>> getAllDTO() throws SQLException {
        Optional<List<Programador>> programadores = this.findAll();

        if (programadores.isPresent()){
            List<ProgramadorDTO> result = new ArrayList<>();
            for (Programador programador : programadores.get()) {
                Optional<ProgramadorDTO> programadorDTO = getProgramadorDTO(Optional.of(programador));
                programadorDTO.ifPresent(result::add);
            }
            return Optional.of(result);
        }else{
            return Optional.empty();
        }
    }

    @Override
    public Optional<ProgramadorDTO> getByIdDTO(String s) throws SQLException {
        Optional<Programador> proOpt = this.getById(s);
        return getProgramadorDTO(proOpt);
    }

    @Override
    public Optional<ProgramadorDTO> insertDTO(ProgramadorDTO objDTO) throws SQLException {
        Optional<String> shaOpt = Cifrator.getInstance().sha256(objDTO.getPassword());
        if (shaOpt.isPresent()){
            objDTO.setPassword(shaOpt.get());

            //No se podrá asignar ningún programador que no esté en proyecto.
            ProyectoService service = new ProyectoService(new ProyectoRepository());
            AtomicBoolean assign = new AtomicBoolean(false);
            service.getAllDTO().ifPresent(s ->
                    s.forEach(d -> {
                                if (d.getUuid_departamento().equals(objDTO.getDepartamento())) {
                                    if (d.getUuid_proyecto() != null){
                                        assign.set(true);
                                    }
                                }
                            }
                    ));

            if (assign.get()){
                Optional<Programador> res = this.insert(mapper.fromDTO(objDTO));
                return res.map(programador -> mapper.toDTO(programador));
            }else{
                System.err.println("No se puede asignar ningún programador que no esté en un proyecto.");
                return Optional.empty();
            }

        }else{
            return Optional.empty();
        }
    }

    @Override
    public Optional<ProgramadorDTO> updateDTO(ProgramadorDTO objDTO) throws SQLException {
        Optional<Programador> proOpt = this.update(mapper.fromDTO(objDTO));
        return getProgramadorDTO(proOpt);
    }

    @Override
    public Optional<ProgramadorDTO> deleteDTO(String s) throws SQLException {
        Optional<Programador> proOpt = this.delete(s);
        return getProgramadorDTO(proOpt);
    }
}
