package pl.mareczek100.api.dto;

import lombok.Builder;

@Builder
public record ExceptionMessage (String errorId){
}
