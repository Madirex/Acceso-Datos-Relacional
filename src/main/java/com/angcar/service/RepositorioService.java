package com.angcar.service;

import com.angcar.dto.CommitDTO;
import com.angcar.dto.IssueDTO;
import com.angcar.dto.RepositorioDTO;
import com.angcar.mapper.RepositorioMapper;
import com.angcar.model.*;
import com.angcar.repository.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RepositorioService extends BaseService<Repositorio, String, RepositorioRepository, RepositorioDTO>{
    RepositorioMapper mapper = new RepositorioMapper();

    public RepositorioService(RepositorioRepository repository) {
        super(repository);
    }

    private Optional<RepositorioDTO> getRepositorioDTO(Optional<Repositorio> opt) throws SQLException {
        if (opt.isPresent()){
            Repositorio repositorio = opt.get();
            RepositorioDTO res = mapper.toDTO(repositorio);

            Optional<Proyecto> proyecto = this.getProyectoById(repositorio.getProyecto());
            Optional<ArrayList<CommitDTO>> commits = this.getCommitsById(repositorio.getProyecto());
            Optional<ArrayList<IssueDTO>> issues = this.getIssuesById(repositorio.getProyecto());

            if (proyecto.isPresent() && commits.isPresent() && issues.isPresent()) {
                res.setProyectoTo(proyecto.get());
            }

            return Optional.of(res);
        }else{
            return Optional.empty();
        }
    }

    private Optional<Proyecto> getProyectoById(String id) throws SQLException {
        ProyectoService service = new ProyectoService(new ProyectoRepository());
        return service.getById(id);
    }

    private Optional<ArrayList<CommitDTO>> getCommitsById(String id) throws SQLException {
        CommitService service = new CommitService(new CommitRepository());

        ArrayList<CommitDTO> commits = new ArrayList<>();
        Optional<List<CommitDTO>> commitsOpt = service.getAllDTO();

        commitsOpt.ifPresent(p -> p.forEach(pr -> {
            if (pr.getProyecto().equals(id)) {
                System.out.println(pr);
                commits.add(pr);
            }
        }));

            return Optional.of(commits);
    }

    private Optional<ArrayList<IssueDTO>> getIssuesById(String id) throws SQLException {
        IssueService service = new IssueService(new IssueRepository());

        ArrayList<IssueDTO> issues = new ArrayList<>();

        Optional<List<IssueDTO>> proyecto = service.getAllDTO();

        proyecto.ifPresent(p -> p.forEach(pr -> {
            if (pr.getProyecto().equals(id)) {
                System.out.println(pr);
                issues.add(pr);
            }
        }));

        return Optional.of(issues);
    }

    @Override
    public Optional<List<RepositorioDTO>> getAllDTO() throws SQLException {
        Optional<List<Repositorio>> repositorios = this.findAll();

        if (repositorios.isPresent()){
            List<RepositorioDTO> result = new ArrayList<>();
            for (Repositorio repositorio : repositorios.get()) {
                Optional<RepositorioDTO> repositorioDTO = getRepositorioDTO(Optional.of(repositorio));
                repositorioDTO.ifPresent(result::add);
            }
            return Optional.of(result);
        }else{
            return Optional.empty();
        }
    }

    @Override
    public Optional<RepositorioDTO> getByIdDTO(String s) throws SQLException {
        Optional<Repositorio> opt = this.getById(s);
        return getRepositorioDTO(opt);
    }

    @Override
    public Optional<RepositorioDTO> insertDTO(RepositorioDTO objDTO) throws SQLException {
        Optional<Repositorio> opt = this.insert(mapper.fromDTO(objDTO));
        return getRepositorioDTO(opt);
    }

    @Override
    public Optional<RepositorioDTO> updateDTO(RepositorioDTO objDTO) throws SQLException {
        Optional<Repositorio> opt = this.update(mapper.fromDTO(objDTO));
        return getRepositorioDTO(opt);
    }

    @Override
    public Optional<RepositorioDTO> deleteDTO(String s) throws SQLException {
        Optional<Repositorio> opt = this.delete(s);
        return getRepositorioDTO(opt);
    }
}
