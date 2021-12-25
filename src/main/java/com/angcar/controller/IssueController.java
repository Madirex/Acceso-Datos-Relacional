package com.angcar.controller;

import com.angcar.dto.IssueDTO;
import com.angcar.repository.IssueRepository;
import com.angcar.service.IssueService;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class IssueController extends BaseController<IssueDTO, String, IssueService>{
    private static IssueController controller = null;

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

    private final IssueService service;

    private IssueController(IssueService issueService) {
        super(issueService);
        this.service = issueService;
    }

    public static IssueController getInstance() {
        if (controller == null) {
            controller = new IssueController(new IssueService(new IssueRepository()));
        }
        return controller;
    }

    public Optional<String> getIssuesOpenAndNotInCommit() {
        try {
            final Gson prettyGson = new GsonBuilder()
                    .setPrettyPrinting()
                    .addSerializationExclusionStrategy(strategy)
                    .create();

            Optional<List<IssueDTO>> opt = service.getIssuesOpenAndNotInCommit();

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