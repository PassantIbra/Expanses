package com.pectus.expanses.repository;

import com.pectus.expanses.model.dto.SumDto;

import java.util.List;

public interface ExpanseCustomRepository {

    List<SumDto> getSum(String groupBy);

}
