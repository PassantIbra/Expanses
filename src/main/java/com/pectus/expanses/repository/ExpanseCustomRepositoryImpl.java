package com.pectus.expanses.repository;

import com.pectus.expanses.model.Expanse;
import com.pectus.expanses.model.dto.SumDto;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class ExpanseCustomRepositoryImpl implements ExpanseCustomRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public List<SumDto> getSum(String groupBy) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<SumDto> criteriaQuery = cb.createQuery(SumDto.class);
        Root<Expanse> root = criteriaQuery.from(Expanse.class);

        criteriaQuery.multiselect(root.get(groupBy).alias("groupBy"), cb.sum(root.get("amount")).alias("sum"))
                .groupBy(root.get(groupBy));
        return em.createQuery(criteriaQuery).getResultList();
    }

}
