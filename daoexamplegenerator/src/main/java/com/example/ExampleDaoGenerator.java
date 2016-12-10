package com.example;


import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class ExampleDaoGenerator {
    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(2, "com.boxfish.lishaowei");
        addNote(schema);
        //添加实体类
        new DaoGenerator().generateAll(schema, "/Users/lishaowei/StudioProjects/CalendarView/app/src/main/java-gen");
    }
    private static void addNote(Schema schema) {
        Entity note = schema.addEntity("Note");

        note.addIdProperty();
        note.addStringProperty("text").notNull();
        note.addStringProperty("comment");
        note.addDateProperty("date");
        note.addStringProperty("title");

    }
}
