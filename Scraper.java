import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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


//        //      Location of stats:
////      Name:
////      <tbody>
////          <tr>
////              <td align="left">
////                  <span style="white-space:nowrap">
////                      Content of 2nd <a> element
////      StrStart:
////      <tbody>
////          <tr>
////              Content of 3rd <td> element
//
//        //        System.out.println("doc = " + doc);
//        Element tbody = doc.select("tbody").get(1);
////        System.out.println("tbody = " + tbody);
//        System.out.println(" ------------------------------------------------ ");
//        Element activeRow = tbody.select("tr").get(1);
//
////        Element title = activeRow.select("td span a:nth-child(2)").first();
////        String titleText = title.html();
////        System.out.println("title = " + title);
////        System.out.println("titleText = " + titleText);
//
//        Element primaryAttribute = activeRow.select("td:nth-child(2) a").first();
//        String primaryAttributeText = primaryAttribute.attr("title");
//        System.out.println("primAtt = " + primaryAttribute);
//        System.out.println("primAttText = " + primaryAttributeText);
//
////        Element baseArmor = activeRow.select("td:nth-child(13) span").first();
//        Element baseArmor = activeRow.select("td:nth-child(13) span").first();
//        String baseArmorText = baseArmor.attr("title");
//        String bText = baseArmorText.split(" ")[0];
//        System.out.println("baseArmor = " + baseArmor);
//        System.out.println("baseArmorText = " + baseArmorText);
//        System.out.println("bText = " + bText);
//        System.out.println("");
//
//        Element strStart = activeRow.select("td span a:nth-child(2)").first();
//        String strText = strStart.text();
//        System.out.println("strStart = " + strStart);
//        System.out.println("strText = " + strText);
//        System.out.println("");
        System.out.println("scrapeAttribute(\"Lich\",baseArmor) = " + scrapeAttribute("Lich","baseArmor"));
        System.out.println("scrapeAttribute(\"Lich\",strStart) = " + scrapeAttribute("Lich", "strStart"));
        System.out.println("scrapeAttribute(\"Axe\",baseArmor) = " + scrapeAttribute("Axe", "baseArmor"));
        System.out.println("scrapeAttribute(\"Kunkka\",\"agiGain\") = " + scrapeAttribute("Kunkka", "agiGain"));
        System.out.println("scrapeAttribute(\"mermaid\",\"agiGain\") = " + scrapeAttribute("mermaid", "agiGain"));
        System.out.println("scrapeAttribute(\"Axe\",\"level\") = " + scrapeAttribute("Axe", "level"));
        System.out.println("scrapeNames() = " + scrapeNames());
    }

    Scraper(int startingRowIndex, int startingTableBodyIndex, Document doc) throws IOException {
        this.startingRowIndex = startingRowIndex;
        this.startingTableBodyIndex = startingTableBodyIndex;
        this.doc = doc;
        buildSelectors();
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
            return;
        }
    }

    public Map<String, String> scrapeAttributes(String heroName, ArrayList<String> attributesToScrape) {
        Map<String, String> returnMap = new HashMap<>();
        for (String a : attributesToScrape) {
            returnMap.put(a, scrapeAttribute(heroName, a));
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
                if (attribute == "primaryAttribute") {
                    return attributeElement.attr("title");
                } else if (attribute == "baseArmor") {
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
