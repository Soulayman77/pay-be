package com.example.school.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaginatedResponse<T> {
    private List<T> results;
    private int page;
    private int count;
    private int totalPages;
    private long totalItems;
    private boolean last;
}
