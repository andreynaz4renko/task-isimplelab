package ru.andreynaz4renko.data;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Перечисление {@link TaskStatus} представляет статусы задачи, такие как
 * {@link TaskStatus#NEW}, {@link TaskStatus#IN_PROGRESS} и {@link TaskStatus#DONE}.
 */
@XmlType
@XmlEnum
public enum TaskStatus {
    @XmlEnumValue("new")
    @JsonProperty("new")
    @JsonEnumDefaultValue
    NEW,
    @JsonProperty("in_progress")
    @XmlEnumValue("in_progress")
    IN_PROGRESS,
    @JsonProperty("done")
    @XmlEnumValue("done")
    DONE
}

