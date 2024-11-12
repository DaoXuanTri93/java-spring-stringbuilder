package com.tmn.master_service.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Data
@Setter
@AllArgsConstructor
public class Objectpage<T> {
    private long totalLength;
    private int totalSize;
    private List<T> data;
}
