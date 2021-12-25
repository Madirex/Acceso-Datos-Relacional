package com.angcar.controller;

import com.angcar.dto.RepositorioDTO;
import com.angcar.repository.RepositorioRepository;
import com.angcar.service.RepositorioService;

public class RepositorioController extends BaseController<RepositorioDTO, String, RepositorioService>{
    private static RepositorioController controller = null;

    private RepositorioController(RepositorioService repositorioService) {
        super(repositorioService);
    }

    public static RepositorioController getInstance() {
        if (controller == null) {
            controller = new RepositorioController(new RepositorioService(new RepositorioRepository()));
        }
        return controller;
    }
}