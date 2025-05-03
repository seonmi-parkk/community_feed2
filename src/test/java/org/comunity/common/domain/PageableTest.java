package org.comunity.common.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PageableTest {

    @Test
    void givenPagableIndexIsNull_whenGetOffset_thenShouldBeReturn0(){
        // given
        Pageable pageable = new Pageable();

        // when
        int offset = pageable.getOffset();
        int limit = pageable.getLimit();

        // then
        assertEquals(0, offset);
        assertEquals(10, limit);
    }

    @Test
    void givenPageableIndexIs10_whenGetOffset_thenShouldBeReturn10(){
        // given
        Pageable pageable = new Pageable(2, 10);

        // when
        int offset = pageable.getOffset();
        int limit = pageable.getLimit();

        // then
        assertEquals(10, offset);
        assertEquals(10, limit);
    }
}
