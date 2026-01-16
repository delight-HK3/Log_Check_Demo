package com.spring.jpatest.dto.Like;

import lombok.Getter;

@Getter
public class likeRequestDTO {
    
    private final Long useruuId;
    private final int boardCd;

    public likeRequestDTO(Long useruuId, int boardCd) {
        this.useruuId = useruuId;
        this.boardCd = boardCd;
    }

}
