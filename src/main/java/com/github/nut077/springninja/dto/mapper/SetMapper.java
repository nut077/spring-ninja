package com.github.nut077.springninja.dto.mapper;

import org.mapstruct.Mapper;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface SetMapper {

    default Set<String> map(String names) {
        if (StringUtils.isEmpty(names)) return null;
        return Arrays.asList(names.split(",")).parallelStream().map(String::trim).collect(Collectors.toSet());
    }

    default String map(Set<String> set) {
        if (set == null) return null;
        return String.join(",", set);
    }

}

