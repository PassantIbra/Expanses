package com.pectus.expanses.repository;

import com.pectus.expanses.model.Expanse;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpanseRepository extends PagingAndSortingRepository<Expanse, Long>, ExpanseCustomRepository,
        JpaSpecificationExecutor<Expanse> {

}

