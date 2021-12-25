package com.angcar.controller;

import com.angcar.dto.ProgramadorDTO;
import com.angcar.dto.ProyectoDTO;
import com.angcar.repository.ProyectoRepository;
import com.angcar.service.ProyectoService;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ProyectoController extends BaseController<ProyectoDTO, String, ProyectoService>{
    private static ProyectoController controller = null;
    private final ProyectoService service;

    ExclusionStrategy strategy = new ExclusionStrategy() {
        @Override
        public boolean shouldSkipClass(Class<?> clazz) {
            return false;
        }

        @Override
        public boolean shouldSkipField(FieldAttributes field) {
            return field.getName().startsWith("password"); //Excluir contraseña
        }
    };

    private ProyectoController(ProyectoService proyectoService) {
        super(proyectoService);
        this.service = proyectoService;
    }

    public static ProyectoController getInstance() {
        if (controller == null) {
            controller = new ProyectoController(new ProyectoService(new ProyectoRepository()));
        }
        return controller;
    }

    public Optional<String> getAllProgramadorCommitOrder(String id) {
        try {
            final Gson prettyGson = new GsonBuilder()
                    .setPrettyPrinting()
                    .addSerializationExclusionStrategy(strategy)
                    .create();

            Optional<List<ProgramadorDTO>> opt = service.getAllProgramadorCommitOrder(id);

            if (opt.isPresent()){
                return Optional.of(prettyGson.toJson(opt.get()));
            }else{
                return Optional.empty();
            }

        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
            return Optional.empty();
        }
    }

    public Optional<String> getThreeExpensiveProjects() {
        try {
            final Gson prettyGson = new GsonBuilder()
                    .setPrettyPrinting()
                    .addSerializationExclusionStrategy(strategy)
                    .create();

            Optional<List<ProyectoDTO>> opt = service.getThreeExpensiveProjects();

            if (opt.isPresent()){
                return Optional.of(prettyGson.toJson(opt.get()));
            }else{
                return Optional.empty();
            }

        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
            return Optional.empty();
        }
    }
}