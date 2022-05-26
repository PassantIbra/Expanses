package com.pectus.expanses.controller;

import com.pectus.expanses.exceptionhandling.ExpanseNotFoundException;
import com.pectus.expanses.model.Expanse;
import com.pectus.expanses.model.dto.SumDto;
import com.pectus.expanses.service.ExpanseService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/expanses")
public class ExpanseController {

    private final ExpanseService expanseService;

    /**
     * @param filter : the condition to filter on Ex: filter=memberName=Matt,amount>=1400
     * @param sort   : holds the fields to sort on and the sorting order Ex: sort=id:Asc
     * @return list of sorted expanses that match the filter
     */
    // @ApiOperation(value = "get a list of filtered expanses ")
    @GetMapping("")
    public ResponseEntity<List<Expanse>> findAllByFilter(@RequestParam(value = "filter", required = false) String filter,
                                                         @RequestParam(value = "sort", required = false) String sort) {
        return ResponseEntity.ok(expanseService.findAllByFilter(filter, sort));
    }

    /**
     * @param fields : fields that need to be retrieved for the requested expanse,
     *               if Empty all fields will be retrieved Ex: fields=memberName
     * @param id     : id of the requested expanse
     * @return Expanse field with the required fields only
     * @throws Exception: ExpanseNotFoundException
     */
    @GetMapping("/{id}")
    public ResponseEntity<Expanse> getOneExpanse(
            @RequestParam(value = "fields", required = false) String fields,
            @PathVariable("id") Long id) throws ExpanseNotFoundException {
        return ResponseEntity.ok(expanseService.findOneById(id, fields));
    }

    /**
     * @param groupBy column to group by it
     * @return sum of amount for each unique value of the group by column
     */
    @GetMapping("/amount-sum")
    public ResponseEntity<List<SumDto>> getExpanseSum(@RequestParam String groupBy) {
        return ResponseEntity.ok(expanseService.getExpanseSum(groupBy));
    }

}
