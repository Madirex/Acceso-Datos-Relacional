package com.angcar.controller;

import com.angcar.dto.*;
import com.angcar.repository.DepartamentoRepository;
import com.angcar.service.DepartamentoService;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class DepartamentoController extends BaseController<DepartamentoDTO, String, DepartamentoService>{
    private static DepartamentoController controller = null;
    private final DepartamentoService service;

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

    private DepartamentoController(DepartamentoService departamentoService) {
        super(departamentoService);
        service = departamentoService;
    }

    public static DepartamentoController getInstance() {
        if (controller == null) {
            controller = new DepartamentoController(new DepartamentoService(new DepartamentoRepository()));
        }
        return controller;
    }

    public Optional<String> getAllProyectosAsociados(String id) {
        try {
            final Gson prettyGson = new GsonBuilder()
                    .setPrettyPrinting()
                    .addSerializationExclusionStrategy(strategy)
                    .create();

            Optional<List<ProyectoDTO>> opt = service.obtenerProyectosAsociados(id);

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

    public Optional<String> getAllTrabajadoresAsociados(String id) {
        try {
            final Gson prettyGson = new GsonBuilder()
                    .setPrettyPrinting()
                    .addSerializationExclusionStrategy(strategy)
                    .create();

            Optional<List<ProgramadorDTO>> opt = service.obtenerTrabajadoresAsociados(id);

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