package rwilk.englishWordsGUI.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class TatoebaScrapper {

  private List<String> exampleSentenceList = new ArrayList<>();

  public TatoebaScrapper() {
  }

  public void webScrap(String englishWord) {
    exampleSentenceList = new ArrayList<>();

    try {
      // TODO add paging
      Document document = Jsoup.connect("https://tatoeba.org/pol/sentences/search?query=" + englishWord +"&from=eng&to=pol").timeout(20000).get();

      Elements mainElement = document.select("div#main_content"); //get main content
      Elements pagesElement = mainElement.select("ul.paging"); // get pages navigation content

      Integer pagesNumber = 1;
      if (!pagesElement.isEmpty()) {
        pagesNumber = pagesElement.get(0).children().stream()
            .map(child -> child.children().text())
            .collect(Collectors.toList())
            .stream()
            .filter(StringUtils::isNumeric)
            .map(Integer::valueOf)
            .max(Integer::compareTo).orElse(1);
      }
      Elements translations = mainElement.select("div.sentence-and-translations"); //get 10 translations

      if (pagesNumber > 1) {
        if (pagesNumber > 2) {
          pagesNumber = 2;
        }
        for (int i = 2; i <= pagesNumber; i++) {
          document = Jsoup.connect("https://tatoeba.org/pol/sentences/search?query=" + englishWord +"&from=eng&to=pol&page=" + i).timeout(6000).get();
          mainElement = document.select("div#main_content"); //get main content
          translations.addAll(mainElement.select("div.sentence-and-translations"));
        }
      }
      /*translations.get(0).select("div").select("div.text").text(); //get english and polish translations
      translations.get(0).select("div").select("div.sentence").select("div.text").text(); //get english translations
      translations.get(0).select("div").select("div.translation").select("div.text").text(); //get polish translations*/

      exampleSentenceList = translations.stream()
          .map(translation -> translation.select("div").select("div.sentence").select("div.text").text()
              + "("
              + translation.select("div").select("div.translation").select("div.text").text()
              + ")"
          ).collect(Collectors.toList());

    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  public List<String> getExampleSentenceList() {
    return exampleSentenceList;
  }


  public static void main(String[] args) {
    TatoebaScrapper tatoebaScrapper = new TatoebaScrapper();
    tatoebaScrapper.webScrap("Brazil");
  }

}

