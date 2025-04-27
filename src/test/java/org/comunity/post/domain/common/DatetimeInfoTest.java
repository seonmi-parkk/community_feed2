package org.comunity.post.domain.common;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class DatetimeInfoTest {

    @Test
    void givenCreated_whenUpdated_thenTimeAndStateArsUpdated() {
        // given
        DatetimeInfo datetimeInfo = new DatetimeInfo();
        LocalDateTime localDateTime = datetimeInfo.getDateTime();

        // when
        datetimeInfo.updateEditDatetime();

        // then
        assertTrue(datetimeInfo.isEdited());
        assertNotEquals(localDateTime, datetimeInfo.getDateTime());
    }
}
