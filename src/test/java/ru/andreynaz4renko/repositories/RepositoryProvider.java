package ru.andreynaz4renko.repositories;

import jakarta.xml.bind.JAXBException;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import ru.andreynaz4renko.data.repositories.JsonTaskRepository;
import ru.andreynaz4renko.data.repositories.RestTaskRepository;
import ru.andreynaz4renko.data.repositories.XmlTaskRepository;

import java.util.stream.Stream;

public class RepositoryProvider implements ArgumentsProvider {
    public static final String XML_FILEPATH = "tasks.xml";
    public static final String JSON_FILEPATH = "tasks.json";
    public static final String REST_URL = "https://mocki.io/v1/6aed3f0b-9334-44b9-87ed-489f5559b0b5";

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws JAXBException {
        return Stream.of(
                Arguments.of(new XmlTaskRepository(XML_FILEPATH)),
                Arguments.of(new JsonTaskRepository(JSON_FILEPATH)),
                Arguments.of(new RestTaskRepository(REST_URL))
        );
    }
}
