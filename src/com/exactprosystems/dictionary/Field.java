package com.exactprosystems.dictionary;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Field", propOrder = {"description", "attribute", "value"})
@XmlSeeAlso({Message.class})
public class Field {

    protected String description;
    protected List<Attribute> attribute;
    protected List<Attribute> value;
    @XmlAttribute(name = "isServiceName")
    protected Boolean isServiceName;
    @XmlAttribute(name = "isCollection")
    protected Boolean isCollection;
    @XmlAttribute(name = "defaultvalue")
    protected String defaultvalue;
    @XmlAttribute(name = "type")
    protected JavaType type;
    @XmlAttribute(name = "id")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;
    @XmlAttribute(name = "reference")
    @XmlIDREF
    @XmlSchemaType(name = "IDREF")
    protected Object reference;
    @XmlAttribute(name = "name")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NMTOKEN")
    protected String name;
    @XmlAttribute(name = "required")
    protected Boolean required;

    public String getDescription() {
        return description;
    }

    public void setDescription(String value) {
        this.description = value;
    }

    public List<Attribute> getAttribute() {
        if (attribute == null) {
            attribute = new ArrayList<Attribute>();
        }
        return this.attribute;
    }

    public List<Attribute> getValue() {
        if (value == null) {
            value = new ArrayList<Attribute>();
        }
        return this.value;
    }

    public boolean isIsServiceName() {
        if (isServiceName == null) {
            return false;
        } else {
            return isServiceName;
        }
    }

    public void setIsServiceName(Boolean value) {
        this.isServiceName = value;
    }

    public boolean isIsCollection() {
        if (isCollection == null) {
            return false;
        } else {
            return isCollection;
        }
    }

    public void setIsCollection(Boolean value) {
        this.isCollection = value;
    }

    public String getDefaultvalue() {
        return defaultvalue;
    }

    public void setDefaultvalue(String value) {
        this.defaultvalue = value;
    }

    public JavaType getType() {
        return type;
    }

    public void setType(JavaType value) {
        this.type = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String value) {
        this.id = value;
    }

    public Object getReference() {
        return reference;
    }

    public void setReference(Object value) {
        this.reference = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public boolean isRequired() {
        if (required == null) {
            return false;
        } else {
            return required;
        }
    }

    public void setRequired(Boolean value) {
        this.required = value;
    }

}
