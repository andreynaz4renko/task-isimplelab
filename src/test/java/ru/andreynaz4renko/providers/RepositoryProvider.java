package ru.andreynaz4renko.providers;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import ru.andreynaz4renko.data.repositories.XmlTaskRepository;

import javax.xml.parsers.ParserConfigurationException;
import java.util.stream.Stream;

public class RepositoryProvider implements ArgumentsProvider {
    public static final String XML_FILEPATH = "tasks.xml";

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws ParserConfigurationException {
        return Stream.of(
                Arguments.of(new XmlTaskRepository(XML_FILEPATH))
        );
    }
}
