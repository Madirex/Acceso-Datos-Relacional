package com.angcar.service;

import com.angcar.dto.FichaDTO;
import com.angcar.mapper.FichaMapper;
import com.angcar.model.*;
import com.angcar.repository.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FichaService extends BaseService<Ficha, String, FichaRepository, FichaDTO>{

    FichaMapper mapper = new FichaMapper();

    public FichaService(FichaRepository repository) {
        super(repository);
    }

    private Optional<FichaDTO> getFichaDTO(Optional<Ficha> fichaOpt) throws SQLException {
        if (fichaOpt.isPresent()){
            Ficha ficha = fichaOpt.get();
            FichaDTO res = mapper.toDTO(ficha);

            Optional<Programador> programador = this.getProgramadorById(ficha.getUuid_programador());
            Optional<Proyecto> proyecto = this.getProyectoById(ficha.getUuid_proyecto());

            if (programador.isPresent() && proyecto.isPresent()) {
                res.setProgramadorTo(programador.get());
                res.setProyectoTo(proyecto.get());
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

    private Optional<Proyecto> getProyectoById(String id) throws SQLException {
        ProyectoService service = new ProyectoService(new ProyectoRepository());
        return service.getById(id);
    }

    @Override
    public Optional<List<FichaDTO>> getAllDTO() throws SQLException {
        Optional<List<Ficha>> fichas = this.findAll();

        if (fichas.isPresent()){
            List<FichaDTO> result = new ArrayList<>();
            for (Ficha ficha : fichas.get()) {
                Optional<FichaDTO> fichaDTO = getFichaDTO(Optional.of(ficha));
                fichaDTO.ifPresent(result::add);
            }
            return Optional.of(result);
        }else{
            return Optional.empty();
        }
    }

    @Override
    public Optional<FichaDTO> getByIdDTO(String s) throws SQLException {
        Optional<Ficha> opt = this.getById(s);
        return getFichaDTO(opt);
    }

    @Override
    public Optional<FichaDTO> insertDTO(FichaDTO objDTO) throws SQLException {
        Optional<Ficha> opt = this.insert(mapper.fromDTO(objDTO));
        return getFichaDTO(opt);
    }

    @Override
    public Optional<FichaDTO> updateDTO(FichaDTO objDTO) throws SQLException {
        Optional<Ficha> opt = this.update(mapper.fromDTO(objDTO));
        return getFichaDTO(opt);
    }

    @Override
    public Optional<FichaDTO> deleteDTO(String s) throws SQLException {
        Optional<Ficha> opt = this.delete(s);
        return getFichaDTO(opt);
    }
}
