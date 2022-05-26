package com.pectus.expanses.service.imp;

import com.pectus.expanses.exceptionhandling.ExpanseNotFoundException;
import com.pectus.expanses.exceptionhandling.InvalidFilterException;
import com.pectus.expanses.exceptionhandling.InvalidGroupByFieldException;
import com.pectus.expanses.model.Expanse;
import com.pectus.expanses.model.dto.SumDto;
import com.pectus.expanses.repository.ExpanseRepository;
import com.pectus.expanses.service.ExpanseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExpanseServiceImpTest {

    private static final Expanse expanseOne = new Expanse(1L, "IT", "Gaama", new BigDecimal(1200.00), LocalDate.of(2021, 10, 2), "Sam");
    private static final Expanse expanseTwo = new Expanse(2L, "IT", "Mars-NS1", new BigDecimal(1400.00), LocalDate.of(2021, 2, 4), "Mat");
    private static final Expanse expanseThree = new Expanse(3L, "IT", null, null, null, "Mat");

    private static final String validFilter = "memberName!=Matt,amount>=1400";
    private static final String invalidFilter = "membe!=Matt,amount>=1400";

    private static final String sortOne = "id:Desc";

    private static final SumDto sumDto = new SumDto("Gaama", new BigDecimal(1200));
    @Mock
    ExpanseRepository expanseRepository;

    private ExpanseService expanseService;


    @BeforeEach
    void init() {
        expanseService = new ExpanseServiceImp(expanseRepository);
    }


    @Test
    void findAllByFilter_validFilterAndSort_CorrectOutput() {

        when(expanseRepository.findAll(any(Specification.class), any(Sort.class))).thenReturn(List.of(expanseTwo));

        List<Expanse> actual = expanseService.findAllByFilter(validFilter, sortOne);
        List<Expanse> expected = List.of(expanseTwo);
        assertEquals(expected, actual);

    }

    @Test
    void findAllByFilter_invalidFilterAndSort_InvalidFilterExceptionThrown() {

        when(expanseRepository.findAll(any(Specification.class), any(Sort.class))).thenThrow(InvalidFilterException.class);
        assertThrows(InvalidFilterException.class, () -> expanseService.findAllByFilter(invalidFilter, sortOne));

    }

    @Test
    void findOneById_validIdEmptyFilter_wholeExpanseObject() {

        when(expanseRepository.findById(expanseOne.getId())).thenReturn(Optional.of(expanseOne));
        Expanse actual = expanseService.findOneById(expanseOne.getId(), "");
        assertEquals(expanseOne, actual);

    }

    @Test
    void findOneById_invalidId_wholeExpanseObject() {

        when(expanseRepository.findById(3L)).thenThrow(ExpanseNotFoundException.class);
        assertThrows(ExpanseNotFoundException.class, () -> expanseService.findOneById(3L, ""));

    }

    @Test
    void findOneById_validIdValidFilter_ExpanseWithRequiredFieldsOnly() {

        when(expanseRepository.findById(expanseThree.getId())).thenReturn(Optional.of(expanseThree));
        Expanse actual = expanseService.findOneById(expanseThree.getId(), "id,memberName,department");
        assertEquals(expanseThree, actual);

    }

    @Test
    void findOneById_validIdInValidFilter_ExpanseWithAllPropertiesNull() {

        when(expanseRepository.findById(expanseThree.getId())).thenReturn(Optional.of(expanseThree));
        Expanse actual = expanseService.findOneById(expanseThree.getId(), "iB,member,depart");
        assertEquals(new Expanse(), actual);

    }

    @Test
    void getExpanseSum_validGroupByField_CorrectOutput() {

        when(expanseRepository.getSum("memberName")).thenReturn(List.of(sumDto));

        List<SumDto> actual = expanseService.getExpanseSum("memberName");
        List<SumDto> expected = List.of(sumDto);
        assertEquals(expected, actual);

    }

    @Test
    void getExpanseSum_invalidGroupByField_InvalidGroupByFieldExceptionThrown() {

        when(expanseRepository.getSum("member")).thenThrow(InvalidGroupByFieldException.class);
        assertThrows(InvalidGroupByFieldException.class, () -> expanseService.getExpanseSum("member"));

    }

}
