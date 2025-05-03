package org.comunity.admin.ui.query;

import org.comunity.admin.ui.dto.GetTableListResponseDto;
import org.comunity.admin.ui.dto.users.GetUserTableRequestDto;
import org.comunity.admin.ui.dto.users.GetUserTableResponseDto;

public interface AdminTableQueryRepository {
    GetTableListResponseDto<GetUserTableResponseDto> getUserTableData(GetUserTableRequestDto dto);
}
