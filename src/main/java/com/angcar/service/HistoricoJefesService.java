package com.angcar.service;

import com.angcar.dto.HistoricoJefesDTO;
import com.angcar.mapper.HistoricoJefesMapper;
import com.angcar.model.*;
import com.angcar.repository.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HistoricoJefesService extends BaseService<HistoricoJefes, String, HistoricoJefesRepository,
        HistoricoJefesDTO>{

    HistoricoJefesMapper mapper = new HistoricoJefesMapper();

    public HistoricoJefesService(HistoricoJefesRepository repository) {
        super(repository);
    }

    private Optional<HistoricoJefesDTO> getHistoricoJefesDTO(Optional<HistoricoJefes> opt) throws SQLException {
        if (opt.isPresent()){
            HistoricoJefes historicoJefes = opt.get();
            HistoricoJefesDTO res = mapper.toDTO(historicoJefes);

            Optional<Programador> programador = this.getProgramadorById(historicoJefes.getUuid_programador());
            Optional<Departamento> departamento = this.getDepartamentoById(historicoJefes.getUuid_departamento());

            if (programador.isPresent() && departamento.isPresent()) {
                res.setProgramadorTo(programador.get());
                res.setDepartamentoTo(departamento.get());
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

    private Optional<Departamento> getDepartamentoById(String id) throws SQLException {
        DepartamentoService service = new DepartamentoService(new DepartamentoRepository());
        return service.getById(id);
    }

    @Override
    public Optional<List<HistoricoJefesDTO>> getAllDTO() throws SQLException {
        Optional<List<HistoricoJefes>> historicoJefess = this.findAll();

        if (historicoJefess.isPresent()){
            List<HistoricoJefesDTO> result = new ArrayList<>();
            for (HistoricoJefes historicoJefes : historicoJefess.get()) {
                Optional<HistoricoJefesDTO> historicoJefesDTO = getHistoricoJefesDTO(Optional.of(historicoJefes));
                historicoJefesDTO.ifPresent(result::add);
            }
            return Optional.of(result);
        }else{
            return Optional.empty();
        }
    }

    @Override
    public Optional<HistoricoJefesDTO> getByIdDTO(String s) throws SQLException {
        Optional<HistoricoJefes> opt = this.getById(s);
        return getHistoricoJefesDTO(opt);
    }

    @Override
    public Optional<HistoricoJefesDTO> insertDTO(HistoricoJefesDTO objDTO) throws SQLException {
        Optional<HistoricoJefes> opt = this.insert(mapper.fromDTO(objDTO));
        return getHistoricoJefesDTO(opt);
    }

    @Override
    public Optional<HistoricoJefesDTO> updateDTO(HistoricoJefesDTO objDTO) throws SQLException {
        Optional<HistoricoJefes> opt = this.update(mapper.fromDTO(objDTO));
        return getHistoricoJefesDTO(opt);
    }

    @Override
    public Optional<HistoricoJefesDTO> deleteDTO(String s) throws SQLException {
        Optional<HistoricoJefes> opt = this.delete(s);
        return getHistoricoJefesDTO(opt);
    }
}
