package org.comunity.post.domain.common;

import java.time.LocalDateTime;

public class DatetimeInfo {

    private boolean isEdited;
    private LocalDateTime dateTime;

    public DatetimeInfo() {
        this.isEdited = false;
        this.dateTime = LocalDateTime.now();
    }

    // 수정된 여부 / dateTime update
    public void updateEditDatetime(){
        this.isEdited = true;
        this.dateTime = LocalDateTime.now();
    }

    public boolean isEdited() {
        return isEdited;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

}
