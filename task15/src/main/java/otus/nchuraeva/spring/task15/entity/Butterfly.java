package otus.nchuraeva.spring.task15.entity;

public class Butterfly {
    private static final int DAYS_STANDARD = 14;

    private String id;
    private String name;
    private String body;
    private String paws;
    private String dayOfRebirth;
    private boolean isFastTransforming;

    public Butterfly(String name, String body, String paws, String dayOfRebirth) {
        this.name = name;
        this.body = body;
        this.paws = paws;
        this.dayOfRebirth = dayOfRebirth;
        this.isFastTransforming = Integer.valueOf(dayOfRebirth) > DAYS_STANDARD ? false : true;
    }

    public String getId() {
        return id;
    }

    public Butterfly setId(String id) {
        this.id = id;
        return this;
    }

    public String getBody() {
        return body;
    }

    public Butterfly setBody(String body) {
        this.body = body;
        return this;
    }

    public String getPaws() {
        return paws;
    }

    public Butterfly setPaws(String paws) {
        this.paws = paws;
        return this;
    }

    public String getDayOfRebirth() {
        return dayOfRebirth;
    }

    public Butterfly setDayOfRebirth(String dayOfRebirth) {
        this.dayOfRebirth = dayOfRebirth;
        return this;
    }

    public String getName() {
        return name;
    }

    public Butterfly setName(String name) {
        this.name = name;
        return this;
    }

    public boolean isFastTransforming() {
        return isFastTransforming;
    }

    public Butterfly setFastTransforming(boolean fastTransforming) {
        isFastTransforming = fastTransforming;
        return this;
    }

    @Override
    public String toString() {
        return "Butterfly{" +
                "id='" + id + '\'' +
                ", body='" + body + '\'' +
                ", paws='" + paws + '\'' +
                ", dayOfRebirth='" + dayOfRebirth + '\'' +
                ", isFastTransforming=" + isFastTransforming +
                '}';
    }
}
