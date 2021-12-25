package com.angcar.controller;

import com.angcar.service.BaseService;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public abstract class BaseController<DTO, ID, SERVICE extends BaseService> {
    private final SERVICE service;

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

    protected BaseController(SERVICE service) {
        this.service = service;
    }

    public Optional<String> getAll(){
        try {
            final Gson prettyGson = new GsonBuilder()
                    .setPrettyPrinting()
                    .addSerializationExclusionStrategy(strategy)
                    .create();

            Optional<List<DTO>> opt = service.getAllDTO();

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
    public Optional<String> getByID(ID id){
        try {
            final Gson prettyGson = new GsonBuilder()
                    .setPrettyPrinting()
                    .addSerializationExclusionStrategy(strategy)
                    .create();

            Optional<DTO> opt = service.getByIdDTO(id);

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
    public Optional<String> insert(DTO objDTO){
        try {
            final Gson prettyGson = new GsonBuilder()
                    .setPrettyPrinting()
                    .addSerializationExclusionStrategy(strategy)
                    .create();

            Optional<DTO> opt = service.insertDTO(objDTO);

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
    public Optional<String> update(DTO objDTO){
        try {
            final Gson prettyGson = new GsonBuilder()
                    .setPrettyPrinting()
                    .addSerializationExclusionStrategy(strategy)
                    .create();

            Optional<DTO> opt = service.updateDTO(objDTO);

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
    public Optional<String> delete(ID id){
        try {
            final Gson prettyGson = new GsonBuilder()
                    .setPrettyPrinting()
                    .addSerializationExclusionStrategy(strategy)
                    .create();

            Optional<DTO> opt = service.deleteDTO(id);

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