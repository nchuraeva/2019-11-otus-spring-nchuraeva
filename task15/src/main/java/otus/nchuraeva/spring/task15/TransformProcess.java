package otus.nchuraeva.spring.task15;

import otus.nchuraeva.spring.task15.entity.Butterfly;

public class TransformProcess {

    private static final String DAY_OF_DEATH = "30";

    public Butterfly process(Butterfly butterfly) {
        checkPod(butterfly);
        return doProcess(butterfly, String.format("Processing transform caterpillar to butterfly %s", butterfly.getName()));
    }

    public Butterfly fastProcess(Butterfly butterfly) {
        return doProcess(butterfly, String.format("Fast processing transform caterpillar to butterfly %s", butterfly.getName()));
    }

    private Butterfly doProcess(Butterfly butterfly, String message) {
        System.out.println(message);
        return butterfly;
    }

    private void checkPod(Butterfly butterfly) {
        if(butterfly.getDayOfRebirth() == DAY_OF_DEATH) {
            throw new RuntimeException(String.format("The caterpillar was eaten %s", butterfly.getName()));
        }
    }

}
