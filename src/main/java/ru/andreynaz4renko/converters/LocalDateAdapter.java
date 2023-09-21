package ru.andreynaz4renko.converters;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import java.time.LocalDate;

public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {
    public LocalDate unmarshal(String date) {
        return LocalDate.parse(date);
    }

    public String marshal(LocalDate date) {
        return date.toString();
    }
}
