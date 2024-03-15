package com.excite.taskmanager;

import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.excite.taskmanager.application.resource.gen.org.openapitools.model.TaskResponseBody.StatusEnum;

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

        // integer→EnumStatus
        AbstractConverter<Integer, StatusEnum> intToEnumStatus = new AbstractConverter<Integer, StatusEnum>() {
            protected StatusEnum convert(Integer context) {
                return StatusEnum.fromValue(context);
            }
        };

        modelMapper.addConverter(intToEnumStatus);

        return modelMapper;
    }
}
