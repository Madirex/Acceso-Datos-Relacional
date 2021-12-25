package com.angcar.controller;

import com.angcar.dto.TareaDTO;
import com.angcar.repository.TareaRepository;
import com.angcar.service.TareaService;

public class TareaController extends BaseController<TareaDTO, String, TareaService>{
    private static TareaController controller = null;

    private TareaController(TareaService tareaService) {
        super(tareaService);
    }

    public static TareaController getInstance() {
        if (controller == null) {
            controller = new TareaController(new TareaService(new TareaRepository()));
        }
        return controller;
    }
}