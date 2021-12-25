package com.angcar.service;

import com.angcar.dto.CommitDTO;
import com.angcar.dto.FichaDTO;
import com.angcar.dto.IssueDTO;
import com.angcar.dto.ProgramadorDTO;
import com.angcar.mapper.IssueMapper;
import com.angcar.model.*;
import com.angcar.repository.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class IssueService extends BaseService<Issue, String, IssueRepository, IssueDTO>{

    IssueMapper mapper = new IssueMapper();

    public IssueService(IssueRepository repository) {
        super(repository);
    }

    public Optional<List<IssueDTO>> getIssuesOpenAndNotInCommit() throws SQLException {

        Optional<List<IssueDTO>> opt = getAllDTO();
        List<IssueDTO> listIssues = new ArrayList<>();
        CommitService CommitServ = new CommitService(new CommitRepository());

        //Obtener Issues asociados
        opt.ifPresent(issueDTOS -> issueDTOS.forEach(pr -> {
            //Issues abiertas
            if (!pr.isEs_acabado()) {
                //Issues en commit
                try {
                    Optional<List<CommitDTO>> commits = CommitServ.getAllDTO();
                    commits.ifPresent(commitDTOS -> commitDTOS.forEach(c -> {
                        if (!c.getIssue_procedente().equals(pr.getUuid_issue())){
                            listIssues.add(pr);
                        }
                    }));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }));

        return Optional.of(listIssues);
    }

    private Optional<IssueDTO> getIssueDTO(Optional<Issue> opt) throws SQLException {
        if (opt.isPresent()){
            Issue issue = opt.get();
            IssueDTO res = mapper.toDTO(issue);

            Optional<Proyecto> proyecto = this.getProyectoById(issue.getProyecto());
            Optional<Repositorio> repositorio = this.getRepositorioById(issue.getProyecto());

            if (proyecto.isPresent() && repositorio.isPresent()) {
                res.setProyectoTo(proyecto.get());
                res.setRepositorioTo(repositorio.get());
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

    private Optional<Repositorio> getRepositorioById(String id) throws SQLException {
        RepositorioService service = new RepositorioService(new RepositorioRepository());
        return service.getById(id);
    }

    @Override
    public Optional<List<IssueDTO>> getAllDTO() throws SQLException {
        Optional<List<Issue>> issues = this.findAll();

        if (issues.isPresent()){
            List<IssueDTO> result = new ArrayList<>();
            for (Issue issue : issues.get()) {
                Optional<IssueDTO> issueDTO = getIssueDTO(Optional.of(issue));
                issueDTO.ifPresent(result::add);
            }
            return Optional.of(result);
        }else{
            return Optional.empty();
        }
    }

    @Override
    public Optional<IssueDTO> getByIdDTO(String s) throws SQLException {
        Optional<Issue> opt = this.getById(s);
        return getIssueDTO(opt);
    }

    @Override
    public Optional<IssueDTO> insertDTO(IssueDTO objDTO) throws SQLException {
        Optional<Issue> opt = this.insert(mapper.fromDTO(objDTO));
        return getIssueDTO(opt);
    }

    @Override
    public Optional<IssueDTO> updateDTO(IssueDTO objDTO) throws SQLException {
        Optional<Issue> opt = this.update(mapper.fromDTO(objDTO));
        return getIssueDTO(opt);
    }

    @Override
    public Optional<IssueDTO> deleteDTO(String s) throws SQLException {
        Optional<Issue> opt = this.delete(s);
        return getIssueDTO(opt);
    }
}
