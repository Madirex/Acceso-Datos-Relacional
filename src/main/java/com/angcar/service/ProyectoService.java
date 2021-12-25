package com.angcar.service;

import com.angcar.dto.ProgramadorDTO;
import com.angcar.dto.ProyectoDTO;
import com.angcar.mapper.ProyectoMapper;
import com.angcar.model.*;
import com.angcar.repository.*;

import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class ProyectoService extends BaseService<Proyecto, String, ProyectoRepository, ProyectoDTO>{

    ProyectoMapper mapper = new ProyectoMapper();

    public ProyectoService(ProyectoRepository repository) {
        super(repository);
    }

    public Optional<List<ProgramadorDTO>> getAllProgramadorCommitOrder(String id) throws SQLException {

        ProgramadorService programServ = new ProgramadorService(new ProgramadorRepository());
        Optional<ProyectoDTO> proyecto = getByIdDTO(id); //Proyecto
        Optional<List<ProgramadorDTO>> programList = programServ.getAllDTO();
        List<ProgramadorDTO> programadoresAsociados = new ArrayList<>();

        programList.ifPresent(programadorDTOS -> programadorDTOS.forEach(p -> {
                if (proyecto.isPresent()){
                    if (p.getDepartamentoTo() != null){
                        if (proyecto.get().getUuid_departamento().equals(p.getDepartamentoTo().getUuid_departamento())){
                            programadoresAsociados.add(p);
                        }
                    }
                }else{
                    System.err.println("Error en el método getAllProgramadorCommitOrder() de la clase ProyectoService.");
                    System.exit(1);
                }
        }));

        return Optional.of(programadoresAsociados.stream()
                .sorted(Comparator.comparing(t -> Collections.frequency(programadoresAsociados, t)))
                .collect(Collectors.toList()));
    }

    public Optional<List<ProyectoDTO>> getThreeExpensiveProjects() throws SQLException {

        ProgramadorService programServ = new ProgramadorService(new ProgramadorRepository());

        //Todos los proyectos
        Optional<List<ProyectoDTO>> proyectos = this.getAllDTO();
        Map<ProyectoDTO, Double> proyectosPresupuesto = new LinkedHashMap<>();
        proyectos.ifPresent(p -> {
            p.forEach(pr -> {

                //Recoger el salario de los programadores asociados al proyecto
                AtomicReference<Double> salarioProgramador = new AtomicReference<>(0.0);
                try {
                    programServ.getAllProgramadorProductividad().ifPresent(
                            s -> s.forEach(ss -> {
                                if (ss.getProyectos() != null){ //TEST: null
                                    ss.getProyectos().forEach(pr2 -> {
                                        if (pr.getUuid_proyecto().equals(pr2.getUuid_proyecto())){
                                            salarioProgramador.updateAndGet(v ->
                                                    (double) (v + ss.getProgramador().getSalario()));
                                        }
                                    });
                                }
                            })
                    );
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                //Agregar presupuesto asignado del proyecto + salario de cada trabajador
                proyectosPresupuesto.put(pr,pr.getPresupuesto() + salarioProgramador.get());
            });
        });

        //Ahora ordenarlos
        Map<ProyectoDTO, Double> sorted = proyectosPresupuesto.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey,
                        Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        //Ahora buscar los 3 proyectos más caros y devolverlos
        List<ProyectoDTO> threeExpensiveProjects = new ArrayList<>();
        for (int n = 0; n < 3; n++){
            threeExpensiveProjects.add((ProyectoDTO) sorted.keySet().toArray()[n]);
        }

        return Optional.of(threeExpensiveProjects);
    }

    private Optional<ProyectoDTO> getProyectoDTO(Optional<Proyecto> opt) throws SQLException {
        if (opt.isPresent()){
            Proyecto proyecto = opt.get();
            ProyectoDTO res = mapper.toDTO(proyecto);

            Optional<Programador> programador = this.getJefeById(proyecto.getJefe_proyecto());
            Optional<Departamento> departamento = this.getDepartamentoById(proyecto.getUuid_departamento());
            Optional<Repositorio> repositorio = this.getRepositorioById(proyecto.getRepositorio());

            if (programador.isPresent() && departamento.isPresent() && repositorio.isPresent()) {
                res.setJefe_proyectoTo(programador.get());
                res.setDepartamentoTo(departamento.get());
                res.setRepositorioTo(repositorio.get());
            }

            return Optional.of(res);
        }else{
            return Optional.empty();
        }
    }

    private Optional<Programador> getJefeById(String id) throws SQLException {
        ProgramadorService service = new ProgramadorService(new ProgramadorRepository());
        return service.getById(id);
    }

    private Optional<Departamento> getDepartamentoById(String id) throws SQLException {
        DepartamentoService service = new DepartamentoService(new DepartamentoRepository());
        return service.getById(id);
    }

    private Optional<Repositorio> getRepositorioById(String id) throws SQLException {
        RepositorioService service = new RepositorioService(new RepositorioRepository());
        return service.getById(id);
    }

    @Override
    public Optional<List<ProyectoDTO>> getAllDTO() throws SQLException {
        Optional<List<Proyecto>> proyectos = this.findAll();

        if (proyectos.isPresent()){
            List<ProyectoDTO> result = new ArrayList<>();
            for (Proyecto proyecto : proyectos.get()) {
                Optional<ProyectoDTO> proyectoDTO = getProyectoDTO(Optional.of(proyecto));
                proyectoDTO.ifPresent(result::add);
            }
            return Optional.of(result);
        }else{
            return Optional.empty();
        }
    }

    @Override
    public Optional<ProyectoDTO> getByIdDTO(String s) throws SQLException {
        Optional<Proyecto> opt = this.getById(s);
        return getProyectoDTO(opt);
    }

    @Override
    public Optional<ProyectoDTO> insertDTO(ProyectoDTO objDTO) throws SQLException {
        Optional<Proyecto> opt = this.insert(mapper.fromDTO(objDTO));
        return getProyectoDTO(opt);
    }

    @Override
    public Optional<ProyectoDTO> updateDTO(ProyectoDTO objDTO) throws SQLException {
        Optional<Proyecto> opt = this.update(mapper.fromDTO(objDTO));
        return getProyectoDTO(opt);
    }

    @Override
    public Optional<ProyectoDTO> deleteDTO(String s) throws SQLException {
        Optional<Proyecto> opt = this.delete(s);
        return getProyectoDTO(opt);
    }
}
