package org.palette.easelsearchservice.dto.request;

public record SearchRequest (
    Integer page,
    Integer size,
    String keyword
){
}
