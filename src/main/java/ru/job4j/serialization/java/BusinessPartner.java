package ru.job4j.serialization.java;

import org.json.JSONObject;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Arrays;

@XmlRootElement(name = "businessPartner")
@XmlAccessorType(XmlAccessType.FIELD)
public class BusinessPartner {
    @XmlAttribute
    private String name;
    @XmlAttribute
    private int id;
    @XmlAttribute
    private boolean isActive;
    private Contact bpContact;
    private String[] oldNames;

    public BusinessPartner() {

    }

    public BusinessPartner(String name, int id, boolean isActive, Contact bpContact, String[] oldNames) {
        this.name = name;
        this.id = id;
        this.isActive = isActive;
        this.bpContact = bpContact;
        this.oldNames = oldNames;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public boolean isActive() {
        return isActive;
    }

    public Contact getBpContact() {
        return bpContact;
    }

    public String[] getOldNames() {
        return oldNames;
    }

    @Override
    public String toString() {
        return "BusinessPartner{"
                + "name='"
                + name
                + '\''
                + ", id="
                + id
                + ", isActive="
                + isActive
                + ", bpContact="
                + bpContact
                + ", oldNames="
                + Arrays.toString(oldNames)
                + '}';
    }

    public static void main(String[] args) throws Exception {
        BusinessPartner partner = new BusinessPartner("ООО Ромашка", 1, true,
                new Contact(123, "9999999999"), new String[]{"ООО Ромашка old"});

        JSONObject jsonObject = new JSONObject(partner);
        System.out.println(jsonObject.toString());
    }
}
