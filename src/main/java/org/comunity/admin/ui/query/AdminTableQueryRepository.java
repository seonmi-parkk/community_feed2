package org.comunity.admin.ui.query;

import org.comunity.admin.ui.dto.GetTableListResponseDto;
import org.comunity.admin.ui.dto.posts.GetPostTableRequestDto;
import org.comunity.admin.ui.dto.posts.GetPostTableResponseDto;
import org.comunity.admin.ui.dto.users.GetUserTableRequestDto;
import org.comunity.admin.ui.dto.users.GetUserTableResponseDto;

public interface AdminTableQueryRepository {
    GetTableListResponseDto<GetUserTableResponseDto> getUserTableData(GetUserTableRequestDto dto);
    GetTableListResponseDto<GetPostTableResponseDto> getPostTableData(GetPostTableRequestDto dto);
}
