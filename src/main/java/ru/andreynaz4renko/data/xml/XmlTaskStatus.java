package ru.andreynaz4renko.data.xml;


import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;

@XmlType
@XmlEnum
public enum XmlTaskStatus {
    @XmlEnumValue("new") NEW,
    @XmlEnumValue("in_progress") IN_PROGRESS,
    @XmlEnumValue("done") DONE
}
