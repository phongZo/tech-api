package com.tech.api.dto.importDto;

import com.tech.api.dto.ABasicAdminDto;
import com.tech.api.dto.store.StoreDto;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ImportDto extends ABasicAdminDto {
    private Long id;
    private LocalDate date;
    private StoreDto storeDto;
    private Integer total;
    private Integer state;

    private List<ImportLineItemDto> items;
}
