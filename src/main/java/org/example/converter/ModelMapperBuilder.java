package org.example.converter;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ModelMapperBuilder {

    public static ModelMapper getModelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE) // Acessa campos privados
                .setMatchingStrategy(MatchingStrategies.STRICT) // Exige correspondência exata dos nomes
                .setSkipNullEnabled(true); // Ignora propriedades null no source

        modelMapper.addConverter(ctx -> {
            String dateStr = ctx.getSource();
            if (dateStr == null || dateStr.isBlank()) return null;
            return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        }, String.class, LocalDate.class);

        modelMapper.addConverter(ctx -> {
            LocalDate date = ctx.getSource();
            return date != null ? date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) : null;
        }, LocalDate.class, String.class);

        return modelMapper;
    }

}
