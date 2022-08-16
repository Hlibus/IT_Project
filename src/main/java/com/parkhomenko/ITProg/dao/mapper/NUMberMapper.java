package com.parkhomenko.ITProg.dao.mapper;

import com.parkhomenko.ITProg.dto.NUMberDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NUMberMapper implements RowMapper<NUMberDto> {

    @Override
    public NUMberDto mapRow(ResultSet rs, int rowNum) throws SQLException {

        NUMberDto nuMberDto = new NUMberDto();
        nuMberDto.setCount(rs.getInt("count"));
        return nuMberDto;

    }
}
