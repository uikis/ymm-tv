package com.ymm.ymmtvcommon.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AjaxResult {

    private boolean success;
    private Object object;
}
