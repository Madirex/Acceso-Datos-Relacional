package com.angcar.dto;

import com.angcar.model.Issue;
import com.angcar.model.Programador;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TareaDTO extends BaseDTO{

    private String uuid_tarea;
    private String uuid_programador;
    private String uuid_issue;

    //ADDS
    private Programador programadorTo;
    private Issue issueTo;

}
