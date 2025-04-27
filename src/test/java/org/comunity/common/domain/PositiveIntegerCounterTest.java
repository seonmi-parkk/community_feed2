package org.comunity.common.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

// 테스트 파일의 클래스는 public을 빼주는 것이 좋음.
class PositiveIntegerCounterTest {

    @Test
    void givenCreated_whenIncrease_thenCountIsOne() {
        // given
        PositiveIntegerCounter counter = new PositiveIntegerCounter();

        // when
        counter.increase();

        // then
        assertEquals(1, counter.getCount());
    }

    @Test
    void givenCreatedAndIncreased_whenDecrease_thenCountIsZero() {
        // given
        PositiveIntegerCounter counter = new PositiveIntegerCounter();
        counter.increase();

        // when
        counter.decrease();

        // then
        assertEquals(0, counter.getCount());
    }

    @Test
    void givenCreated_whenDecrease_thenCountIsZero() {
        // given
        PositiveIntegerCounter counter = new PositiveIntegerCounter();

        // when
        counter.decrease();

        // then
        assertEquals(0, counter.getCount());
    }
}
