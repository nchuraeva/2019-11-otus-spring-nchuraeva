package otus.nchuraeva.spring.task8.shell;

public enum PromptColor {
    RED(1),
    GREEN(2);

    private final int value;

    PromptColor(int value) {
        this.value = value;
    }

    public int toJlineAttributedStyle() {
        return this.value;
    }

}
