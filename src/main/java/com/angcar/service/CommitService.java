package com.angcar.service;

import com.angcar.dto.CommitDTO;
import com.angcar.mapper.CommitMapper;
import com.angcar.model.*;
import com.angcar.repository.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

public class CommitService extends BaseService<Commit, String, CommitRepository, CommitDTO> {
    CommitMapper mapper = new CommitMapper();

    public CommitService(CommitRepository repository) {
        super(repository);
    }

    private Optional<CommitDTO> getCommitDTO(Optional<Commit> commitOpt) throws SQLException {
        if (commitOpt.isPresent()){
            Commit commit = commitOpt.get();
            CommitDTO res = mapper.toDTO(commit);

            Optional<Programador> programador = this.getProgramadorById(commit.getAutor_commit());
            Optional<Proyecto> proyecto = this.getProjectById(commit.getProyecto());
            Optional<Repositorio> repositorio = this.getRepositorioById(commit.getRepositorio());
            Optional<Issue> issue = this.getIssueById(commit.getIssue_procedente());

            if (programador.isPresent() && proyecto.isPresent() && repositorio.isPresent() && issue.isPresent()) {
                res.setAutor_commitTo(programador.get());
                res.setProyectoTo(proyecto.get());
                res.setRepositorioTo(repositorio.get());
                res.setIssue_procedenteTo(issue.get());
            }
            return Optional.of(res);

        }else{
            return Optional.empty();
        }
    }

    private Optional<Programador> getProgramadorById(String id) throws SQLException {
        ProgramadorService service = new ProgramadorService(new ProgramadorRepository());
        return service.getById(id);
    }

    private Optional<Proyecto> getProjectById(String id) throws SQLException {
        ProyectoService service = new ProyectoService(new ProyectoRepository());
        return service.getById(id);
    }

    private Optional<Repositorio> getRepositorioById(String id) throws SQLException {
        RepositorioService service = new RepositorioService(new RepositorioRepository());
        return service.getById(id);
    }

    private Optional<Issue> getIssueById(String id) throws SQLException {
        IssueService service = new IssueService(new IssueRepository());
        return service.getById(id);
    }

    @Override
    public Optional<List<CommitDTO>> getAllDTO() throws SQLException {
        Optional<List<Commit>> commits = this.findAll();

        if (commits.isPresent()){
            List<CommitDTO> result = new ArrayList<>();
            for (Commit commit : commits.get()) {
                Optional<CommitDTO> commitDTO = getCommitDTO(Optional.of(commit));
                commitDTO.ifPresent(result::add);
            }
            return Optional.of(result);
        }else{
            return Optional.empty();
        }
    }

    @Override
    public Optional<CommitDTO> getByIdDTO(String s) throws SQLException {
        Optional<Commit> commitOpt = this.getById(s);
        return getCommitDTO(commitOpt);
    }

    @Override
    public Optional<CommitDTO> insertDTO(CommitDTO objDTO) throws SQLException {

        Optional<Commit> commitOpt = this.insert(mapper.fromDTO(objDTO));
        Optional<CommitDTO> opt = getCommitDTO(commitOpt);
        AtomicBoolean continuar = new AtomicBoolean(false);

        if (opt.isPresent()) {
            if (opt.get().getIssue_procedenteTo() != null) {

                if (objDTO.getIssue_procedenteTo() != null) {
                    objDTO.getIssue_procedenteTo().setEsAcabado(true);

                    ProyectoService proyectoService = new ProyectoService(new ProyectoRepository());

                    //No podrá realizar ningún commit ningún programador que no esté en este proyecto
                    // ni que no nazca de un issue.
                    if (objDTO.getIssue_procedenteTo() != null && objDTO.getAutor_commitTo() != null
                            && objDTO.getProyectoTo() != null) {
                        proyectoService.getById(objDTO.getProyectoTo().getUuid_proyecto()).ifPresent(p -> {
                            if (p.getUuid_departamento().equals(objDTO.getAutor_commitTo().getDepartamento())) {
                                continuar.set(true);
                            }
                        });
                    }
                } else {
                    System.err.println("Error: no se ha encontrado el issue procedente");
                }
            }
        }

        if (continuar.get()) {
            return getCommitDTO(commitOpt);
        } else {
            System.err.println("No podrá realizar ningún commit ningún programador que no esté en este proyecto " +
                    "ni que no nazca de un issue.");
            return Optional.empty();
        }
    }

    @Override
    public Optional<CommitDTO> updateDTO(CommitDTO objDTO) throws SQLException {
        Optional<Commit> commitOpt = this.update(mapper.fromDTO(objDTO));
        return getCommitDTO(commitOpt);
    }

    @Override
    public Optional<CommitDTO> deleteDTO(String s) throws SQLException {
        Optional<Commit> commitOpt = this.delete(s);
        return getCommitDTO(commitOpt);
    }
}