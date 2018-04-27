package com.exactprosystems.dictionary;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

@XmlRegistry
public class ObjectFactory {

    private final static QName _Dictionary_QNAME = new QName("http://exactprosystems.com/dictionary", "dictionary");

    public ObjectFactory() {
    }

    public Dictionary createDictionary() {
        return new Dictionary();
    }

    public Field createField() {
        return new Field();
    }

    public Message createMessage() {
        return new Message();
    }

    public Attribute createAttribute() {
        return new Attribute();
    }

    public Dictionary.Fields createDictionaryFields() {
        return new Dictionary.Fields();
    }

    public Dictionary.Messages createDictionaryMessages() {
        return new Dictionary.Messages();
    }

    @XmlElementDecl(namespace = "http://exactprosystems.com/dictionary", name = "dictionary")
    public JAXBElement<Dictionary> createDictionary(Dictionary value) {
        return new JAXBElement<Dictionary>(_Dictionary_QNAME, Dictionary.class, null, value);
    }

}
