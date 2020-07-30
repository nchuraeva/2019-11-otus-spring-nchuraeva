package otus.nchuraeva.spring.task15;

import org.springframework.core.convert.converter.Converter;
import otus.nchuraeva.spring.task15.entity.Butterfly;
import otus.nchuraeva.spring.task15.entity.Caterpillar;

public class CaterpillarToButterflyConverter implements Converter<Caterpillar, Butterfly> {

        @Override
        public Butterfly convert(Caterpillar caterpillar) {
                return new Butterfly(caterpillar.getName(), caterpillar.getBody(), caterpillar.getPaws(), caterpillar.getDayOfRebirth());
        }
}
