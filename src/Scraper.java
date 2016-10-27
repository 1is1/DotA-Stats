import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Tom on 25-10-2016.
 */
class Scraper {
    private Document doc;
    private Map<String, String> attributeSelectors;
    private int startingRowIndex;
    private int startingTableBodyIndex;
    private Element tbody;
    private Element startRow;
    private int amountOfRows;
    private int amountOfUsableRows;

    Scraper() throws IOException {
        startingRowIndex = 1;
        startingTableBodyIndex = 1;
        buildSelectors();
        buildDocument();
    }

    private void buildSelectors() {
        this.attributeSelectors = new HashMap<>();
        attributeSelectors.put("heroName", "td span a:nth-child(2)");
        attributeSelectors.put("primaryAttribute", "td:nth-child(2)");
        attributeSelectors.put("strStart", "td:nth-child(3)");
        attributeSelectors.put("strGain", "td:nth-child(4)");
        attributeSelectors.put("agiStart", "td:nth-child(5)");
        attributeSelectors.put("agiGain", "td:nth-child(6)");
        attributeSelectors.put("intStart", "td:nth-child(7)");
        attributeSelectors.put("intGain", "td:nth-child(8)");
        attributeSelectors.put("baseArmor", "td:nth-child(13) span");
    }

    private void buildDocument() throws IOException {
        doc = Jsoup.connect("http://dota2.gamepedia.com/Table_of_hero_attributes").userAgent("Mozilla").get();
        if (doc.select("tbody").size() < startingTableBodyIndex) {
            System.err.println("The starting table index is larger than the amount of tables in the document");
            return;
        }
        tbody = doc.select("tbody").get(startingTableBodyIndex);

        amountOfRows = tbody.select("tr").size();
        amountOfUsableRows = amountOfRows - startingRowIndex;
        int rowIndex = startingRowIndex;

        if (rowIndex > amountOfUsableRows) {
            System.err.println("The starting row index is larger than the amount of rows in tbody");
            return;
        }
        startRow = tbody.select("tr").get(rowIndex);
        if (startRow == null) {
            System.err.println("The starting row of this index is empty");
        }
    }

    public Map<String, String> scrapeAttributes(String heroName, ArrayList<String> attributesToScrape) {
        Map<String, String> returnMap = new HashMap<>();
        for (String a : attributesToScrape) {
            returnMap.put(a, scrapeAttribute(heroName, a));
        }
        return returnMap;
    }

    public Map<String, String> scrapeAllAttributes(String heroName) {
        Map<String, String> returnMap = new HashMap<>();
        Set<String> attributeNames = attributeSelectors.keySet();
        for (String attribute : attributeNames) {
            returnMap.put(attribute, scrapeAttribute(heroName, attribute));
        }
        return returnMap;
    }

    public String scrapeAttribute(String heroName, String attribute) {
        // Checks if attribute exists
        if (!attributeExists(attribute)) {
            System.err.println("Attribute of name \"" + attribute + "\" doesn't exist");
            return "ERROR";
        }
        boolean isMoreRows = true;
        int rowIndex = startingRowIndex;
        // Iterates through rows until last name is found or last row is treated - upon which it returns
        while (isMoreRows) {
            Element row = tbody.select("tr").get(rowIndex);
            Element attributeElement = row.select(attributeSelectors.get("heroName")).first();
            String currentHeroName = attributeElement.text();
            if (currentHeroName.equals(heroName)) {
                attributeElement = row.select(attributeSelectors.get(attribute)).first();
                if (attribute.equals("primaryAttribute")) {
                    return attributeElement.attr("title");
                } else if (attribute.equals("baseArmor")) {
                    String baseArmorText = attributeElement.attr("title");
                    String baseArmorValue = baseArmorText.split(" ")[0];
                    return baseArmorValue;
                } else {
                    return attributeElement.text();
                }
            }
            rowIndex++;
            if (rowIndex > amountOfUsableRows) {
                isMoreRows = false;
            }


        }
        System.err.println("Hero of name \"" + heroName + "\" is not found!");
        return "ERROR";
    }

    private boolean attributeExists(String attributeName) {
        return attributeSelectors.keySet().contains(attributeName);
    }

    public ArrayList<String> scrapeNames() {
        int rowIndex = startingRowIndex;
        ArrayList<String> heroNames = new ArrayList<>();
        boolean isMoreRows = true;
        Element row;
        while (isMoreRows) {
            row = tbody.select("tr").get(rowIndex);
            Element attributeElement = row.select(attributeSelectors.get("heroName")).first();
            heroNames.add(attributeElement.text());
            rowIndex++;
            if (rowIndex > amountOfUsableRows) {
                isMoreRows = false;
            }
        }
        return heroNames;
    }
}
