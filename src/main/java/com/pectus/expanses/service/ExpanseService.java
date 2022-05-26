package com.pectus.expanses.service;

import com.pectus.expanses.exceptionhandling.ExpanseNotFoundException;
import com.pectus.expanses.model.Expanse;
import com.pectus.expanses.model.dto.SumDto;

import java.util.List;

public interface ExpanseService {
    List<Expanse> findAllByFilter(String filter, String sort);

    Expanse findOneById(Long id, String fields) throws ExpanseNotFoundException;

    List<SumDto> getExpanseSum(String groupBy);
}
