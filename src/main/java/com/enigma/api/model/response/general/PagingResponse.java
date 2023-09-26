package com.enigma.api.model.response.general;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class PagingResponse {
    private Integer count;
    private Integer totalPage;
    private Integer page;
    private Integer size;
}
