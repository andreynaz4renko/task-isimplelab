package ru.andreynaz4renko.data.xml;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType
@XmlEnum
public enum XmlTaskStatus {
    @XmlEnumValue("new") NEW,
    @XmlEnumValue("in_progress") IN_PROGRESS,
    @XmlEnumValue("done") DONE
}
