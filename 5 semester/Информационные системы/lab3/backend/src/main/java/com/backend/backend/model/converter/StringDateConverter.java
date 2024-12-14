package com.backend.backend.model.converter;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringDateConverter extends AbstractBeanField<Date, String> {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");

    @Override
    protected Date convert(String value) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
        if (value == null || value.isEmpty()) {
            return null; // Обработка пустого значения
        }
        try {
            return dateFormat.parse(value);
        } catch (ParseException e) {
            throw new CsvDataTypeMismatchException(value, Date.class, e.getMessage());
        }
    }
}
