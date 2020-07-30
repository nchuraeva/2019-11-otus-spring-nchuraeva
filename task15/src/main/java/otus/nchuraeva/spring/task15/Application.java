package otus.nchuraeva.spring.task15;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import otus.nchuraeva.spring.task15.entity.Butterfly;
import otus.nchuraeva.spring.task15.entity.Caterpillar;
import otus.nchuraeva.spring.task15.repository.ButterflyRepository;

import java.util.List;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {

		ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);

		ButterflyRepository butterflyRepository = context.getBean(ButterflyRepository.class);

		resetDatabase(butterflyRepository);

		Caterpillar caterpillar1 = new Caterpillar("butterfly1", "body", "paws", "25");
		Caterpillar caterpillar2 = new Caterpillar("butterfly2","body", "paws", "30");
		Caterpillar caterpillar3 = new Caterpillar("butterfly3","body", "paws", "6");
		Caterpillar caterpillar4 = new Caterpillar("butterfly4","body", "paws", "23");

		Config.CaterpillarService caterpillarService = context.getBean(Config.CaterpillarService.class);

		caterpillarService.caterpillar(caterpillar1);
		caterpillarService.caterpillar(caterpillar2);
		caterpillarService.caterpillar(caterpillar3);
		caterpillarService.caterpillar(caterpillar4);

		getAll(butterflyRepository);
	}

	private static void resetDatabase(ButterflyRepository butterflyRepository) {
		butterflyRepository.deleteAll();
	}

	private static void getAll(ButterflyRepository butterflyRepository) {
		List<Butterfly> butterflyList = butterflyRepository.findAll();
		butterflyList.stream().forEach(System.out::println);
	}

}
