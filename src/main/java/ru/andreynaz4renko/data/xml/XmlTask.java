package ru.andreynaz4renko.data.xml;

import ru.andreynaz4renko.domain.TaskStatus;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.time.LocalDate;

@XmlAccessorType(XmlAccessType.FIELD)
public class XmlTask {
    @XmlAttribute
    private int id;
    @XmlAttribute
    private String title;
    @XmlElement(name = "Description")
    private String description;
    @XmlElement(name = "Priority")
    private int priority;
    @XmlElement(name = "Deadline")
    private LocalDate deadline;
    @XmlElement(name = "Complete")
    private LocalDate completion;
    @XmlElement(name = "Status")
    private TaskStatus status;
}
