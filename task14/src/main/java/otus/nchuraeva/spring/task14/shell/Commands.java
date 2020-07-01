package otus.nchuraeva.spring.task14.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import otus.nchuraeva.spring.task14.service.JobService;

@ShellComponent
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class Commands {

    private final JobService jobService;


    @ShellMethod(value = "Migrate from NoSQL to SQL", key = {"start"})
    public void restart(
            @ShellOption("-d") String objectName
    ) {
        switch (objectName) {
            case "job":
                jobService.start("noSqlToSqlMigration");
                break;
            default:
                System.out.println("Parameter is not supported");
        }
    }
}
