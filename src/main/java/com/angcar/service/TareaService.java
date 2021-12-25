package com.angcar.service;

import com.angcar.dto.TareaDTO;
import com.angcar.mapper.TareaMapper;
import com.angcar.model.*;
import com.angcar.repository.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TareaService extends BaseService<Tarea, String, TareaRepository, TareaDTO>{

    TareaMapper mapper = new TareaMapper();

    public TareaService(TareaRepository repository) {
        super(repository);
    }

    private Optional<TareaDTO> getTareaDTO(Optional<Tarea> opt) throws SQLException {
        if (opt.isPresent()){
            Tarea tarea = opt.get();
            TareaDTO res = mapper.toDTO(tarea);

            Optional<Programador> programador = this.getProgramadorById(tarea.getUuid_programador());
            Optional<Issue> issue = this.getIssueById(tarea.getUuid_issue());

            if (programador.isPresent() && issue.isPresent()) {
                res.setProgramadorTo(programador.get());
                res.setIssueTo(issue.get());
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

    private Optional<Issue> getIssueById(String id) throws SQLException {
        IssueService service = new IssueService(new IssueRepository());
        return service.getById(id);
    }

    @Override
    public Optional<List<TareaDTO>> getAllDTO() throws SQLException {
        Optional<List<Tarea>> tareas = this.findAll();

        if (tareas.isPresent()){
            List<TareaDTO> result = new ArrayList<>();
            for (Tarea tarea : tareas.get()) {
                Optional<TareaDTO> tareaDTO = getTareaDTO(Optional.of(tarea));
                tareaDTO.ifPresent(result::add);
            }
            return Optional.of(result);
        }else{
            return Optional.empty();
        }
    }

    @Override
    public Optional<TareaDTO> getByIdDTO(String s) throws SQLException {
        Optional<Tarea> opt = this.getById(s);
        return getTareaDTO(opt);
    }

    @Override
    public Optional<TareaDTO> insertDTO(TareaDTO objDTO) throws SQLException {
        Optional<Tarea> opt = this.insert(mapper.fromDTO(objDTO));
        return getTareaDTO(opt);
    }

    @Override
    public Optional<TareaDTO> updateDTO(TareaDTO objDTO) throws SQLException {
        Optional<Tarea> opt = this.update(mapper.fromDTO(objDTO));
        return getTareaDTO(opt);
    }

    @Override
    public Optional<TareaDTO> deleteDTO(String s) throws SQLException {
        Optional<Tarea> opt = this.delete(s);
        return getTareaDTO(opt);
    }
}
