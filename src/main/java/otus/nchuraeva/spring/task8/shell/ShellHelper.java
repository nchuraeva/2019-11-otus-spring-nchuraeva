package otus.nchuraeva.spring.task8.shell;

import org.jline.terminal.Terminal;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;


public class ShellHelper {

    private Terminal terminal;

    public ShellHelper(Terminal terminal) {
        this.terminal = terminal;
    }

    public String getColored(String message, PromptColor color) {
        return (new AttributedStringBuilder()).append(message, AttributedStyle.DEFAULT.foreground(color.toJlineAttributedStyle())).toAnsi();
    }

    public void printSuccess(String message) {
        print(message, PromptColor.valueOf("GREEN"));
    }

    public void printError(String message) {
        print(message, PromptColor.valueOf("RED"));
    }

    public void print(String message, PromptColor color) {
        String toPrint = message;
        if (color != null) {
            toPrint = getColored(message, color);
        }
        terminal.writer().println(toPrint);
        terminal.flush();
    }

}
