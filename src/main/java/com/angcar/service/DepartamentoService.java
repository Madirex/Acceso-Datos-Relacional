package com.angcar.service;

import com.angcar.dto.*;
import com.angcar.mapper.DepartamentoMapper;
import com.angcar.model.*;
import com.angcar.repository.*;
import com.angcar.utils.UUID5;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DepartamentoService extends BaseService<Departamento, String, DepartamentoRepository, DepartamentoDTO>{
    DepartamentoMapper mapper = new DepartamentoMapper();

    public DepartamentoService(DepartamentoRepository repository) {
        super(repository);
    }

    public Optional<List<ProyectoDTO>> obtenerProyectosAsociados(String id) throws SQLException {
        List<ProyectoDTO> proyectosAsociados = new ArrayList<>();

        ProyectoService proyectoServ = new ProyectoService(new ProyectoRepository());
        Optional<List<ProyectoDTO>> proyecto = proyectoServ.getAllDTO();

        proyecto.ifPresent(proyectoDTOS -> proyectoDTOS.forEach(pr -> {
            if (pr.getUuid_departamento().equals(id)) {
                proyectosAsociados.add(pr);
            }
        }));

        return Optional.of(proyectosAsociados);
    }

    public Optional<List<ProgramadorDTO>> obtenerTrabajadoresAsociados(String id) throws SQLException {
        List<ProgramadorDTO> programadoresAsociados = new ArrayList<>();

        ProgramadorService programadorServ = new ProgramadorService(new ProgramadorRepository());
        Optional<List<ProgramadorDTO>> programador = programadorServ.getAllDTO();

        //Obtener trabajadores asociados
        programador.ifPresent(proyectoDTOS -> proyectoDTOS.forEach(pr -> {
            if (pr.getDepartamento().equals(id)) {
                programadoresAsociados.add(pr);
            }
        }));

        return Optional.of(programadoresAsociados);
    }

    private Optional<DepartamentoDTO> getDepartamentoDTO(Optional<Departamento> opt) throws SQLException {
        if (opt.isPresent()){
            Departamento departamento = opt.get();
            DepartamentoDTO res = mapper.toDTO(departamento);

            Optional<Programador> programador = this.getJefeById(departamento.getJefe_departamento());
            programador.ifPresent(res::setJefe_departamentoTo);

            return Optional.of(res);
        }else{
            return Optional.empty();
        }
    }

    private Optional<Programador> getJefeById(String id) throws SQLException {
        ProgramadorService service = new ProgramadorService(new ProgramadorRepository());
        return service.getById(id);
    }

    @Override
    public Optional<List<DepartamentoDTO>> getAllDTO() throws SQLException {
        Optional<List<Departamento>> departamentos = this.findAll();

        if (departamentos.isPresent()){
            List<DepartamentoDTO> result = new ArrayList<>();
            for (Departamento departamento : departamentos.get()) {
                Optional<DepartamentoDTO> departamentoDTO = getDepartamentoDTO(Optional.of(departamento));
                departamentoDTO.ifPresent(result::add);
            }
            return Optional.of(result);
        }else{
            return Optional.empty();
        }
    }

    @Override
    public Optional<DepartamentoDTO> getByIdDTO(String s) throws SQLException {
        Optional<Departamento> opt = this.getById(s);
        return getDepartamentoDTO(opt);
    }

    @Override
    public Optional<DepartamentoDTO> insertDTO(DepartamentoDTO objDTO) throws SQLException {
        //Las issues las crea solamente el jefe del proyecto.
        Optional<Departamento> opt = Optional.empty();

        if(objDTO.getJefe_departamentoTo() != null){
            if (objDTO.getJefe_departamentoTo().isEs_jefe_departamento()){
                opt = this.insert(mapper.fromDTO(objDTO));

                addNewJefe(objDTO); //Agregar a histórico de jefes

            }else{
                System.err.println("No se ha insertado el departamento porque el programador " +
                        "asignado no es un jefe de departamento");
            }
        }else{
            System.err.println("No se ha insertado el departamento porque no hay ningún jefe de departamento" +
                    " que haya sido asignado.");
        }

        return getDepartamentoDTO(opt);
    }

    @Override
    public Optional<DepartamentoDTO> updateDTO(DepartamentoDTO objDTO) throws SQLException {
        Optional<Departamento> opt = this.update(mapper.fromDTO(objDTO));

        //HISTÓRICO DE JEFES
        removeOldJefe(objDTO.getJefe_departamento()); //Quitar al antiguo jefe
        addNewJefe(objDTO); //Agregar a histórico de jefes

        return getDepartamentoDTO(opt);
    }

    @Override
    public Optional<DepartamentoDTO> deleteDTO(String s) throws SQLException {
        removeOldJefe(s); //Quitar antiguo jefe
        Optional<Departamento> opt = this.delete(s);
        return getDepartamentoDTO(opt);
    }

    private void removeOldJefe(String s){
        java.util.Date utilDate = new java.util.Date();
        Date date = new Date(utilDate.getTime());

        //Quitar al antiguo jefe
        try {
            this.getByIdDTO(s).ifPresent(d -> {
                HistoricoJefesService historicoService = new HistoricoJefesService(new HistoricoJefesRepository());
                try {
                    historicoService.getByIdDTO(d.getJefe_departamento()).ifPresent(dd -> {
                        try {
                            historicoService.update(new HistoricoJefes(dd.getUuid_historico(), dd.getUuid_programador(),
                                    dd.getUuid_departamento(), dd.getFecha_alta(), date));
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    });
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void addNewJefe(DepartamentoDTO objDTO){
        java.util.Date utilDate = new java.util.Date();
        Date date = new Date(utilDate.getTime());
        HistoricoJefesService historicoService = new HistoricoJefesService(new HistoricoJefesRepository());
        try {
            historicoService.insert(new HistoricoJefes(UUID5.create(), objDTO.getJefe_departamento(),
                    objDTO.getUuid_departamento(), date, null));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
