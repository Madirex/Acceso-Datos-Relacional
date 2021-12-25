package com.angcar.controller;

import com.angcar.dto.HistoricoJefesDTO;
import com.angcar.repository.HistoricoJefesRepository;
import com.angcar.service.HistoricoJefesService;

public class HistoricoJefesController extends BaseController<HistoricoJefesDTO, String, HistoricoJefesService>{
    private static HistoricoJefesController controller = null;

    private HistoricoJefesController(HistoricoJefesService service) {
        super(service);
    }

    public static HistoricoJefesController getInstance() {
        if (controller == null) {
            controller = new HistoricoJefesController(new HistoricoJefesService(new HistoricoJefesRepository()));
        }
        return controller;
    }
}