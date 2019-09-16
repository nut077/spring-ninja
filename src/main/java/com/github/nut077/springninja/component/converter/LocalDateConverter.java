package com.github.nut077.springninja.component.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class LocalDateConverter implements Converter<String, LocalDate> { // ขาเข้า ขาออก แปลงจาก String ไปเป็น LocalDate ตาม pattern ที่เราต้องการ

    @Override
    public LocalDate convert(String source) {
        return LocalDate.parse(source, DateTimeFormatter.ofPattern("d-M-yyyy"));
    }
}
