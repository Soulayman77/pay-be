package com.example.school.Dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponse {
    @NonNull
    private String message;
}
