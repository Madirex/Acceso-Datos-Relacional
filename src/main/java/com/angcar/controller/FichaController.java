package com.angcar.controller;

import com.angcar.dto.FichaDTO;
import com.angcar.repository.FichaRepository;
import com.angcar.service.FichaService;

public class FichaController extends BaseController<FichaDTO, String, FichaService>{
    private static FichaController controller = null;

    private FichaController(FichaService fichaService) {
        super(fichaService);
    }

    public static FichaController getInstance() {
        if (controller == null) {
            controller = new FichaController(new FichaService(new FichaRepository()));
        }
        return controller;
    }
}