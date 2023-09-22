package ru.andreynaz4renko.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.andreynaz4renko.converters.LocalDateAdapter;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LocalDateAdapterTest {

    LocalDateAdapter adapter;

    @BeforeEach
    public void setUp() {
        adapter = new LocalDateAdapter();
    }

    @Test
    public void testUnmarshal() {
        String date = "2023-09-21";
        LocalDate deserializedDate = adapter.unmarshal(date);

        assertNotNull(date);
        assertEquals(2023, deserializedDate.getYear());
        assertEquals(9, deserializedDate.getMonthValue());
        assertEquals(21, deserializedDate.getDayOfMonth());
    }

    @Test
    public void testMarshal() {
        LocalDate date = LocalDate.of(2023, 9, 21);
        String serializedDate = adapter.marshal(date);

        assertNotNull(serializedDate);
        assertEquals("2023-09-21", serializedDate);
    }
}

