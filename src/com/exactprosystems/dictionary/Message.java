package com.exactprosystems.dictionary;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Message", propOrder = {"field"})
public class Message
    extends Field
{

    protected List<Field> field;

    public List<Field> getField() {
        if (field == null) {
            field = new ArrayList<Field>();
        }
        return this.field;
    }

}
