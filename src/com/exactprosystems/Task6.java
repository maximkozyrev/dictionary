package com.exactprosystems;

import com.exactprosystems.dictionary.*;
import com.exactprosystems.dictionary.Dictionary;
import org.supercsv.cellprocessor.*;
import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.constraint.UniqueHashCode;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.*;

import com.exactprosystems.dictionary.Dictionary.Fields;
import com.exactprosystems.dictionary.Dictionary.Messages;

public class Task6 {

    public static final String NEW_LINE_CHARACTER = "\\u000a";

    public static void main(String[] args) throws IOException{

        Dictionary targetDictionary = new Dictionary();
        targetDictionary.setName("It's mine");
        targetDictionary.setFields(new Fields());
        targetDictionary.setMessages(new Messages());
        targetDictionary.setDescription("Словарь");

        Map<String, Message> nameToMessage = new HashMap<>();
        Map<String, Field> nameToField = new HashMap<>();
        Map<String, JavaType> nameToType = new HashMap<String, JavaType>(){{
            put("Boolean", JavaType.JAVA_LANG_BOOLEAN);
            put("Short", JavaType.JAVA_LANG_SHORT);
            put("UInt8", JavaType.JAVA_LANG_SHORT);
            put("UInt32", JavaType.JAVA_LANG_LONG);
            put("UInt64", JavaType.JAVA_MATH_BIG_DECIMAL);
            put("Byte", JavaType.JAVA_LANG_BYTE);
            put("Bit Field", JavaType.JAVA_LANG_BYTE);
            put("Alpha", JavaType.JAVA_LANG_STRING);
            put("Date", JavaType.ORG_THREETEN_BP_LOCAL_DATE);
            put("Time", JavaType.ORG_THREETEN_BP_LOCAL_TIME);
            put("UInt16", JavaType.JAVA_LANG_INTEGER);
            put("Price", JavaType.JAVA_MATH_BIG_DECIMAL);
        }};
        Set<String> requiredAttributes = new HashSet<String>(){{
            add("IsAdmin");
            add("MessageType");
        }};
        Set<String> requiredFields = new HashSet<String>(){{
            add("UHSequenceNumber");
            add("UHMarketDataGroup");
            add("MessageSequenceNumber");
            add("MessageTime");
        }};

        String[] mapping = new String[]{"id", "message", "field", "lengthLSE", "lengthBIT", "lengthTQ", "lengthOB", "type", "Valuename",
                "valueNameLSE", "valueNameBIT", "valueNameTQ", "valueNameOLS", "defaultValue", "description", "specialRemarksLSE",
                "specialRemarksBIT", "specialRemarksTQ", "specialRemarksOB", "Exactprocomments"};
        try (ICsvBeanReader csvBeanReader = new CsvBeanReader(new FileReader(args[0]), CsvPreference.STANDARD_PREFERENCE)) {
            CellProcessor[] procs = getProcessors();
            CsvRow row;
            int length = 0;
            while ((row = csvBeanReader.read(CsvRow.class, mapping, procs)) != null) {
                String messagename = row.getMessage().replaceAll(" ", "");
                Message message = nameToMessage.get(messagename);
                switch (args[2]){
                    case "lse": length = row.getLengthlse(); break;
                    case "bit": length = row.getLengthbit(); break;
                    case "tq": length = row.getLengthtq(); break;
                    case "ob": length = row.getLengthob(); break;
                    default:
                        System.out.println("Incorrect arguments");
                        System.exit(1);
                }
                if (message == null) {
                    message = new Message();
                    message.setName(messagename);
                    if (!messagename.equals("UnitHeader")) {
                        Iterator<String> iterator = requiredFields.iterator();
                        while (iterator.hasNext()) {
                            Field fieldforfields = new Field();
                            Field fieldformessage = new Field();
                            String fieldname = iterator.next().replaceAll(" ", "");
                            Attribute attribute1 = new Attribute();
                            Attribute attribute2 = new Attribute();
                            attribute1.setName("Length");
                            attribute1.setValue(String.valueOf(length));
                            attribute2.setName("Type");
                            attribute2.setValue("Unknown");
                            fieldforfields.getAttribute().add(attribute1);
                            fieldforfields.getAttribute().add(attribute2);
                            fieldforfields.setName(fieldname);
                            fieldformessage.setName(fieldname);
                            fieldforfields.setDefaultvalue(row.getDefaultvalue());
                            fieldforfields.setId("F_" + fieldname);
                            fieldformessage.setReference(fieldforfields);
                            message.getField().add(fieldformessage);
                            if (!nameToField.containsKey(fieldname)){
                                nameToField.put(fieldname, fieldforfields);
                                targetDictionary.getFields().getField().add(fieldforfields);
                            }
                        }
                    }
                    nameToMessage.put(messagename, message);
                    targetDictionary.getMessages().getMessage().add(message);
                }
                JavaType javaType = nameToType.get(row.getType());
                if (requiredAttributes.contains(row.getField()) && (length == 0)) {
                    Attribute attribute = new Attribute();
                    attribute.setName(row.getField());
                    attribute.setType(javaType);
                    attribute.setValue(row.getValuename());
                    //convertJavaType(javaType, row.getValuename()); // Switch example
                    //javaType.convert(row.getValuename()); // Embedded method example
                    message.getAttribute().add(attribute);
                } else if (length != 0) {
                    Field fieldforfields = nameToField.get(row.getField());
                    Field fieldformessage = new Field();
                    String fieldname = row.getField().replaceAll(" ", "");
                    String value = row.getValuename();
                    if (nameToField.containsKey(row.getField())) {
                        if (value != null) {
                            String[] lines = value.split(NEW_LINE_CHARACTER);
                            for (String line : lines) {
                                String[] valueRecord = line.split("\\|");
                                switch (valueRecord.length) {
                                      case 1:
                                          Attribute attribute = new Attribute();
                                          attribute.setValue(valueRecord[0].trim());
                                          attribute.setType(javaType);
                                          fieldformessage.getAttribute().add(attribute);
                                          break;
                                      case 2:
                                          attribute = new Attribute();
                                          String attributename = valueRecord[1].replaceAll(" ", "");
                                          attribute.setValue(valueRecord[0].trim());
                                          attribute.setName(attributename);
                                          attribute.setType(javaType);
                                          //convertJavaType(javaType, valueRecord[0]);
                                          if (!contains(fieldforfields.getValue(), attributename)) {
                                             fieldforfields.getValue().add(attribute);
                                          }
                                }
                            }
                            fieldformessage.setDefaultvalue(row.getDefaultvalue());
                            fieldformessage.setType(javaType);
                            fieldformessage.setName(fieldname);
                            fieldformessage.setReference(fieldforfields);
                            message.getField().add(fieldformessage);
                        }
                    } else {
                        fieldforfields = new Field();
                        if (value != null) {
                            String[] lines = value.split(NEW_LINE_CHARACTER);
                            for (String line : lines) {
                                String[] valueRecord = line.split("\\|");
                                switch (valueRecord.length) {
                                    case 1:
                                        Attribute attribute = new Attribute();
                                        attribute.setValue(valueRecord[0].trim());
                                        attribute.setType(javaType);
                                        fieldformessage.getAttribute().add(attribute);
                                        break;
                                    case 2:
                                        attribute = new Attribute();
                                        String attributename = valueRecord[1].replaceAll(" ", "");
                                        attribute.setValue(valueRecord[0].trim());
                                        attribute.setName(attributename);
                                        attribute.setType(javaType);
                                        //convertJavaType(javaType, valueRecord[0]);
                                        fieldforfields.getValue().add(attribute);
                                }
                            }
                            fieldforfields.setDefaultvalue(row.getDefaultvalue());
                            fieldforfields.setType(javaType);
                            fieldforfields.setName(fieldname);
                            fieldforfields.setId("F_" + fieldname);
                            fieldformessage.setDefaultvalue(row.getDefaultvalue());
                            fieldformessage.setType(javaType);
                            fieldformessage.setName(fieldname);
                            fieldformessage.setReference(fieldforfields);
                            message.getField().add(fieldformessage);
                            nameToField.put(row.getField(), fieldforfields);
                            if (value.split("\\|").length > 1) targetDictionary.getFields().getField().add(fieldforfields);
                        }
                    }
                }
            }
        }

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Dictionary.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(targetDictionary, new File(args[1]));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    private static Object convertJavaType(JavaType javaType, String value) {
        try {
            switch (javaType) {
                case JAVA_LANG_BOOLEAN:
                    return Boolean.valueOf(value);
                case JAVA_LANG_SHORT:
                    return Short.valueOf(value);
                case JAVA_LANG_INTEGER:
                    return Integer.valueOf(value);
                case JAVA_LANG_LONG:
                    return Long.valueOf(value);
                case JAVA_LANG_BYTE:
                    return Byte.valueOf(value);
                case JAVA_LANG_STRING:
                    return String.valueOf(value);
                case ORG_THREETEN_BP_LOCAL_DATE:
                    return Date.valueOf(value);
                case ORG_THREETEN_BP_LOCAL_TIME:
                    return Time.valueOf(value);
                case JAVA_MATH_BIG_DECIMAL:
                    return Double.valueOf(value);
                default:
                    throw new RuntimeException("Unknown type: '" + javaType + "'");
            }
        } catch (RuntimeException e) {
            throw new RuntimeException("Check java type '" + javaType + "' failed for value '" + value + "'", e);
        }
    }

    private static boolean contains(List<Attribute> list, String name) {
        for (Attribute attribute : list) {
            if (Objects.equals(attribute.getName(), name)) {
                return true;
            }
        }
        return false;
    }

    private static CellProcessor[] getProcessors(){
        CellProcessor[] readprocs = new CellProcessor[]{
                new UniqueHashCode(),
                new NotNull(),
                new NotNull(),
                new Optional(new ParseInt()),
                new Optional(new ParseInt()),
                new Optional(new ParseInt()),
                new Optional(new ParseInt()),
                new Optional(),
                new Optional(),
                new Optional(),
                new Optional(),
                new Optional(),
                new Optional(),
                new Optional(),
                new Optional(),
                new Optional(),
                new Optional(),
                new Optional(),
                new Optional(),
                new Optional()
        };
        return readprocs;
    }

}
