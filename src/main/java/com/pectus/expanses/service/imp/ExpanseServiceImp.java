package com.pectus.expanses.service.imp;

import com.google.common.base.Joiner;
import com.pectus.expanses.exceptionhandling.ExpanseNotFoundException;
import com.pectus.expanses.exceptionhandling.InvalidFilterException;
import com.pectus.expanses.exceptionhandling.InvalidGroupByFieldException;
import com.pectus.expanses.model.Expanse;
import com.pectus.expanses.model.dto.SumDto;
import com.pectus.expanses.repository.ExpanseRepository;
import com.pectus.expanses.repository.Specification.ExpanseSpecificationsBuilder;
import com.pectus.expanses.repository.Specification.SearchOperation;
import com.pectus.expanses.service.ExpanseService;
import lombok.AllArgsConstructor;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class ExpanseServiceImp implements ExpanseService {

    private final ExpanseRepository expanseRepository;

    public List<Expanse> findAllByFilter(String filter, String sort) {
        Specification<Expanse> searchSpec = getSearchSpecs(filter);
        Sort sortParams = getSort(sort);
        try {
            return expanseRepository.findAll(searchSpec, sortParams);
        } catch (InvalidDataAccessApiUsageException e) {
            throw new InvalidFilterException("filer: " + filter + "is invalid");
        }

    }


    public Expanse findOneById(Long id, String fields) throws ExpanseNotFoundException {

        Expanse expanse = expanseRepository.findById(id)
                .orElseThrow(() -> new ExpanseNotFoundException("No expanse found with id: " + id));

        if (fields == null || fields.length() == 0)
            return expanse;

        return getRequiredFields(expanse, fields);

    }

    public List<SumDto> getExpanseSum(String groupBy) {
        try {
            return expanseRepository.getSum(groupBy);
        } catch (InvalidDataAccessApiUsageException e) {
            throw new InvalidGroupByFieldException("grouping by field: " + groupBy + "is invalid");
        }
    }

    private Specification<Expanse> getSearchSpecs(String filter) {
        if (filter == null || filter.isEmpty()) {
            return null;
        }
        ExpanseSpecificationsBuilder builder = new ExpanseSpecificationsBuilder();
        String operationSetExpr = Joiner.on("|")
                .join(SearchOperation.SIMPLE_OPERATION_SET);
        Pattern pattern = Pattern.compile("(\\w+?)(" + operationSetExpr + ")(\\p{Punct}?)((\\w+?)|(\\w+-\\w+-\\w+))(\\p{Punct}?),");
        Matcher matcher = pattern.matcher(filter + ",");
        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(2), matcher.group(4), matcher.group(3), matcher.group(5));
        }

        return builder.build();
    }

    private Sort getSort(String sort) {

        Sort sortParams = Sort.unsorted();
        if (sort != null && !sort.isEmpty()) {
            return Sort.by(Arrays.stream(sort.split(",")).map(s -> {
                String[] split = s.split(":");
                return split.length == 2 ? new Sort.Order(Sort.Direction.fromString(split[1]), split[0]) : new Sort.Order(Sort.Direction.fromString("Asc"), split[0]);
            }).collect(Collectors.toList()));
        }
        return sortParams;
    }

    private Expanse getRequiredFields(Expanse expanse, String fields) {
        return Expanse.builder().id(fields.contains("id") ? expanse.getId() : null)
                .date(fields.contains("date") ? expanse.getDate() : null)
                .amount(fields.contains("amount") ? expanse.getAmount() : null)
                .memberName(fields.contains("memberName") ? expanse.getMemberName() : null)
                .department(fields.contains("department") ? expanse.getDepartment() : null)
                .projectName(fields.contains("projectName") ? expanse.getProjectName() : null)
                .build();
    }
}
