import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Tom on 25-10-2016.
 */
@SuppressWarnings("DefaultFileTemplate")
public class StatsDotA {
    public static void main(String[] args) throws IOException {

        int baseHealth = 200;
        double healthPerStr = 20;
        double agiPerArmor = 7;
        ArrayList<Hero> heroes = new ArrayList<>();

        // Abba stuff
        String name = "Abaddon";
        double strStart = 23;
        double strGain = 2.7;
        double agiStart = 17;
        double agiGain = 1.5;
        double intStart = 21;
        double intGain = 2;
        double baseArmor = -1;

        Hero abaddon = new Hero(name, strStart, strGain, agiStart, agiGain, intStart, intGain, baseArmor, baseHealth, agiPerArmor, healthPerStr);
        heroes.add(abaddon);
        Scraper test = new Scraper();

//        for (Hero h : heroes) {
//            System.out.println(h.getName()+".displayedHealth(1) = " + h.displayedHealth(1));
//            System.out.println(h.getName()+".effectiveHealth(1) = " + (int) h.effectiveHealth(1));
//            System.out.println(h.getName()+".displayedHealth(25) = " + h.displayedHealth(25));
//            System.out.println(h.getName()+".effectiveHealth(25) = " + (int) h.effectiveHealth(25));
//        }
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
