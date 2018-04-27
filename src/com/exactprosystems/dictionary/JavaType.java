package com.exactprosystems.dictionary;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;
import java.sql.Date;
import java.sql.Time;

@XmlType(name = "JavaType")
@XmlEnum
public enum JavaType {

    @XmlEnumValue("java.lang.Boolean")
    JAVA_LANG_BOOLEAN("java.lang.Boolean") {
        @Override
        public Object convert(String value) {
            return null;
        }
    },
    @XmlEnumValue("java.lang.Short")
    JAVA_LANG_SHORT("java.lang.Short"){
        @Override
        public Object convert(String value) {
            return Short.valueOf(value);
        }
    },
    @XmlEnumValue("java.lang.Integer")
    JAVA_LANG_INTEGER("java.lang.Integer"){
        @Override
        public Object convert(String value) {
            return Integer.valueOf(value);
        }
    },
    @XmlEnumValue("java.lang.Long")
    JAVA_LANG_LONG("java.lang.Long") {
        @Override
        public Object convert(String value) {
            return Long.valueOf(value);
        }
    },
    @XmlEnumValue("java.lang.Byte")
    JAVA_LANG_BYTE("java.lang.Byte"){
        @Override
        public Object convert(String value) {
            return Byte.valueOf(value);
        }
    },
    @XmlEnumValue("java.lang.Float")
    JAVA_LANG_FLOAT("java.lang.Float"),
    @XmlEnumValue("java.lang.Double")
    JAVA_LANG_DOUBLE("java.lang.Double"),
    @XmlEnumValue("java.lang.String")
    JAVA_LANG_STRING("java.lang.String"){
        @Override
        public Object convert(String value) {
            return null;
        }
    },
    @XmlEnumValue("org.threeten.bp.LocalDateTime")
    ORG_THREETEN_BP_LOCAL_DATE_TIME("org.threeten.bp.LocalDateTime"),
    @XmlEnumValue("org.threeten.bp.LocalDate")
    ORG_THREETEN_BP_LOCAL_DATE("org.threeten.bp.LocalDate"){
        @Override
        public Object convert(String value) {
            return Date.valueOf(value);
        }
    },
    @XmlEnumValue("org.threeten.bp.LocalTime")
    ORG_THREETEN_BP_LOCAL_TIME("org.threeten.bp.LocalTime"){
        @Override
        public Object convert(String value) {
            return Time.valueOf(value);
        }
    },
    @XmlEnumValue("java.lang.Character")
    JAVA_LANG_CHARACTER("java.lang.Character"),
    @XmlEnumValue("java.math.BigDecimal")
    JAVA_MATH_BIG_DECIMAL("java.math.BigDecimal"){
        @Override
        public Object convert(String value) {
            return Double.valueOf(value);
        }
    };

    private final String value;

    JavaType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public Object convert(String value) {
        throw new UnsupportedOperationException("Unsupported for java type '" + this + "'");
    }

    public static JavaType fromValue(String v) {
        for (JavaType c: JavaType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
