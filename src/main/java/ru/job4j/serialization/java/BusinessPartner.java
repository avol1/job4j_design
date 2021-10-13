package ru.job4j.serialization.java;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Arrays;

public class BusinessPartner {
    private String name;
    private int id;
    private boolean isActive;
    private Contact bpContact;
    private String[] oldNames;

    public BusinessPartner(String name, int id, boolean isActive, Contact bpContact, String[] oldNames) {
        this.name = name;
        this.id = id;
        this.isActive = isActive;
        this.bpContact = bpContact;
        this.oldNames = oldNames;
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

    public static void main(String[] args) {
        BusinessPartner partner = new BusinessPartner("ООО Ромашка", 1, true,
                new Contact(123, "9999999999"), new String[]{"ООО Ромашка old"});
        System.out.println(partner.toString());

        final Gson gson = new GsonBuilder().create();
        String jsonObject = gson.toJson(partner);
        System.out.println(jsonObject);

        final BusinessPartner partnerFromJson = gson.fromJson(jsonObject, BusinessPartner.class);
        System.out.println(partnerFromJson.toString());
    }
}
