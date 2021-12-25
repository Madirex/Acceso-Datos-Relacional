package com.angcar.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface IService<DTO, ID> {
    Optional<List<DTO>> getAllDTO() throws SQLException;
    Optional<DTO> getByIdDTO(ID id) throws SQLException;
    Optional<DTO> insertDTO(DTO objDTO) throws SQLException;
    Optional<DTO> updateDTO(DTO objDTO) throws SQLException;
    Optional<DTO> deleteDTO(ID objDTO) throws SQLException;
}
