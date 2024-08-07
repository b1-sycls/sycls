package com.b1.ticket.dto;

import com.b1.cast.entity.dto.CastGetUserResponseDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class TicketGetDetailCastResponseDto {
    private Long castId;
    private String name;
    private String imagePath;

    public static List<TicketGetDetailCastResponseDto> from(List<CastGetUserResponseDto> castDto) {
        return castDto.stream()
                .map(TicketGetDetailCastResponseDto::fromDto).toList();
    }

    private static TicketGetDetailCastResponseDto fromDto(CastGetUserResponseDto dto) {
        return TicketGetDetailCastResponseDto.builder()
                .castId(dto.getCastId())
                .name(dto.getName())
                .imagePath(dto.getImagePath())
                .build();
    }
}
