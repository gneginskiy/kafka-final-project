package com.techbank.account.query.repository.util.getall.criteria;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.Data;
import org.springdoc.core.annotations.ParameterObject;

@Data
@ParameterObject
public class SearchCriteria {
    @Parameter(required = false, example = "0")
    private Integer pageNum = 0;
    @Parameter(required = false, example = "1000")
    private Integer pageSize = 1000;
    @Parameter(required = false, example = "comment_blabla") //exact match: filterBy=paid_false,orderStatus_DELAYED
    private String filterBy = "";
    @Parameter(required = false, example = "id_ASC")
    private String sortBy = "";
    @Parameter(required = false, example = "t1")
    private String ft = "";
}
