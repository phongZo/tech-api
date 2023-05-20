package com.tech.api.form.importForm;

import lombok.Data;

import javax.validation.Valid;
import java.util.List;

@Data
public class RequestImportForm {
    private List<@Valid RequestImportItem> importItems;
}
