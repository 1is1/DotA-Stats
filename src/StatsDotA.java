import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Tom on 25-10-2016.
 */
@SuppressWarnings("DefaultFileTemplate")
public class StatsDotA {
    public static void main(String[] args) throws IOException, NoSuchFieldException, IllegalAccessException {
        int baseHealth = 200;
        double healthPerStr = 20;
        double agiPerArmor = 7;
        ArrayList<Hero> heroes = new ArrayList<>();
        Scraper scrapy = new Scraper();
        List<Hero> allHeroes = makeAllHeroes();
        for (Hero hero : allHeroes) {
            System.out.println(hero);
            System.out.println(hero.getName() + " has effective HP of " + (int) hero.effectiveHealth() + " at level 1");
            System.out.println(hero.getName() + " has effective HP of " + (int) hero.effectiveHealth(25) + " at level 25");
            System.out.println(" - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - ");
        }

    }
    public static List<Hero> makeAllHeroes() throws IOException, NoSuchFieldException, IllegalAccessException {
        List<Hero> heroes = new ArrayList<>();
        Scraper scraper = new Scraper();
        List<String> heroNames = scraper.scrapeNames();
        for (String heroName : heroNames) {
            Map<String, String> mapOfAttributes = scraper.scrapeAllAttributes(heroName);
            heroes.add(new Hero(mapOfAttributes));
        }
        return heroes;
    }
}








//armor
// level 1
// simulates to 1.4
// was          1.4285
// level 2
// simulates to 1.6
// was          1.571
// level 3
// simulates to 1.857
// was          1.9
// level 4
// simulates to 2.0
// was          2.1
// level 5
// simulates to 2.2857
// was          2.3
// level 6
// simulates to 2.42857
// was          2.5
// level 7
// simulates to 2.714285
// was          2.7
// level 8
// simulates to 2.85714
// was          2.9
// level 9
// simulates to 3.14285
// was          3.1
// level 10
// simulates to 3.2857
// was          3.4
// level 11
// simulates to 3.571
// was          3.6
// level 12
// simulates to 3.714
// was          3.8
// level 13
// simulates to 4.0
// was          4.0
