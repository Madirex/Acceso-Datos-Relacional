package com.angcar.controller;

import com.angcar.dto.CommitDTO;
import com.angcar.repository.CommitRepository;
import com.angcar.service.CommitService;

public class CommitController extends BaseController<CommitDTO, String, CommitService> {
    private static CommitController controller = null;

    private CommitController(CommitService commitService) {
        super(commitService);
    }

    public static CommitController getInstance() {
        if (controller == null) {
            controller = new CommitController(new CommitService(new CommitRepository()));
        }
        return controller;
    }

}