package otus.nchuraeva.spring.task15.entity;

public class Caterpillar {

    private String name;
    private String body;
    private String paws;
    private String dayOfRebirth;

    public Caterpillar(String name, String body, String paws, String dayOfRebirth) {
        this.name = name;
        this.body = body;
        this.paws = paws;
        this.dayOfRebirth = dayOfRebirth;
    }

    public String getBody() {
        return body;
    }

    public Caterpillar setBody(String body) {
        this.body = body;
        return this;
    }

    public String getPaws() {
        return paws;
    }

    public Caterpillar setPaws(String paws) {
        this.paws = paws;
        return this;
    }

    public String getDayOfRebirth() {
        return dayOfRebirth;
    }

    public Caterpillar setDayOfRebirth(String dayOfRebirth) {
        this.dayOfRebirth = dayOfRebirth;
        return this;
    }

    public String getName() {
        return name;
    }

    public Caterpillar setName(String name) {
        this.name = name;
        return this;
    }
}
