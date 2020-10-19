package co.uk.kenyoncomputing.mixfix.models;

import java.io.Serializable;

public class TestPOJO implements Serializable {
    private int id;
    private String name;
    private String someData;

    public TestPOJO(int id, String name, String someData) {
        this.id = id;
        this.name = name;
        this.someData = someData;
    }

    public TestPOJO() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSomeData() {
        return someData;
    }

    public void setSomeData(String someData) {
        this.someData = someData;
    }
}
