package com.excite.taskmanager;

import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Configuration
public class ApplicationConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // LocalDate→Date
        AbstractConverter<LocalDate, Date> localDateToDate = new AbstractConverter<LocalDate, Date>() {
            protected Date convert(LocalDate source) {
                return Date.from(source.atStartOfDay(ZoneId.systemDefault()).toInstant());
            }
        };

        // Date→LocalDate
        AbstractConverter<Date, LocalDate> dateToLocalDate = new AbstractConverter<Date, LocalDate>() {
            protected LocalDate convert(Date source) {
                return source.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            }
        };

        modelMapper.addConverter(localDateToDate);
        modelMapper.addConverter(dateToLocalDate);

        return modelMapper;
    }
}
