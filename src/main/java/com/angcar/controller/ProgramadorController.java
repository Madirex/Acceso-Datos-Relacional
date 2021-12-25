package com.angcar.controller;

import com.angcar.dto.ProgramadorDTO;
import com.angcar.repository.ProgramadorRepository;
import com.angcar.service.ProgramadorService;
import com.angcar.utils.ProductividadProgramador;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ProgramadorController extends BaseController<ProgramadorDTO, String, ProgramadorService>{

    ExclusionStrategy strategy = new ExclusionStrategy() {
        @Override
        public boolean shouldSkipClass(Class<?> clazz) {
            return false;
        }

        @Override
        public boolean shouldSkipField(FieldAttributes field) {
            return field.getName().startsWith("password");
        }
    };

    private static ProgramadorController controller = null;

    private final ProgramadorService service;

    private ProgramadorController(ProgramadorService programadorService) {
        super(programadorService);
        this.service = programadorService;
    }

    public static ProgramadorController getInstance() {
        if (controller == null) {
            controller = new ProgramadorController(new ProgramadorService(new ProgramadorRepository()));
        }
        return controller;
    }

    public Optional<String> getAllProgramadorProductividad(){
        try {
            final Gson prettyGson = new GsonBuilder()
                    .addSerializationExclusionStrategy(strategy)
                    .setPrettyPrinting()
                    .create();

            Optional<List<ProductividadProgramador>> opt = service.getAllProgramadorProductividad();

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

    @Override
    public Optional<String> getAll(){
        try {
            final Gson prettyGson = new GsonBuilder()
                    .addSerializationExclusionStrategy(strategy)
                    .setPrettyPrinting()
                    .create();

            Optional<List<ProgramadorDTO>> opt = service.getAllDTO();

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
    public Optional<String> getByID(String id){
        try {
            final Gson prettyGson = new GsonBuilder()
                    .addSerializationExclusionStrategy(strategy)
                    .setPrettyPrinting()
                    .create();

            Optional<ProgramadorDTO> opt = service.getByIdDTO(id);

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
    public Optional<String> insert(ProgramadorDTO objDTO){
        try {
            final Gson prettyGson = new GsonBuilder()
                    .addSerializationExclusionStrategy(strategy)
                    .setPrettyPrinting()
                    .create();

            Optional<ProgramadorDTO> opt = service.insertDTO(objDTO);

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
    public Optional<String> update(ProgramadorDTO objDTO){
        try {
            final Gson prettyGson = new GsonBuilder()
                    .addSerializationExclusionStrategy(strategy)
                    .setPrettyPrinting()
                    .create();

            Optional<ProgramadorDTO> opt = service.updateDTO(objDTO);

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
    public Optional<String> delete(String id){
        try {
            final Gson prettyGson = new GsonBuilder()
                    .addSerializationExclusionStrategy(strategy)
                    .setPrettyPrinting()
                    .create();

            Optional<ProgramadorDTO> opt = service.deleteDTO(id);

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