package com.exactprosystems.dictionary;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Dictionary", propOrder = {"description", "fields", "messages"})
public class Dictionary {

    protected String description;
    protected Fields fields;
    protected Messages messages;
    @XmlAttribute(name = "name", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NMTOKEN")
    protected String name;

    public String getDescription() {
        return description;
    }

    public void setDescription(String value) {
        this.description = value;
    }

    public Fields getFields() {
        return fields;
    }

    public void setFields(Fields value) {
        this.fields = value;
    }

    public Messages getMessages() {
        return messages;
    }

    public void setMessages(Messages value) {
        this.messages = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"field"})
    public static class Fields {

        protected List<Field> field;

        public List<Field> getField() {
            if (field == null) {
                field = new ArrayList<Field>();
            }
            return this.field;
        }

    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"message"})
    public static class Messages {

        protected List<Message> message;

        public List<Message> getMessage() {
            if (message == null) {
                message = new ArrayList<Message>();
            }
            return this.message;
        }

    }

}
