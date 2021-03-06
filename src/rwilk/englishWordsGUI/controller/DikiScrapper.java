package rwilk.englishWordsGUI.controller;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class DikiScrapper {

    private ArrayList<String> translationsList = new ArrayList<>();
    private ArrayList<String> exampleSentenceList = new ArrayList<>();

    public DikiScrapper() {
    }

    public void webScraps(String englishWord) throws Exception {
        ArrayList<String> partOfSpeechList = new ArrayList<>();
        translationsList = new ArrayList<>();
        exampleSentenceList = new ArrayList<>();

        try {
            Document document = Jsoup.connect("http://www.diki.pl/slownik-angielskiego?q=" + englishWord).timeout(6000).get();
            Elements elements = document.select("div.diki-results-left-column"); //select("span.partOfSpeech");
            for (Element element : elements.select("span.partOfSpeech")) {
                partOfSpeechList.add(element.text());
            }
            elements = document.select("ol.foreignToNativeMeanings");
            int iCountPartOfSpeech = 0;

            // Elements represented all translations for all part of speech
            // Element represented all translations for the single part of speech
            for (Element element : elements) {
                String lastPartOfSpeech = "";
                String tempTranslation = "";

                //Children is single translation.
                for (Element children : element.children()) {

                    if (lastPartOfSpeech.isEmpty()) {
                        if (partOfSpeechList.isEmpty() || iCountPartOfSpeech >= partOfSpeechList.size()) {
                            lastPartOfSpeech = "inne";
                        } else {
                            lastPartOfSpeech = partOfSpeechList.get(iCountPartOfSpeech);
                        }
                        translationsList.add("[" + lastPartOfSpeech + "]");
                    }

                    List<String> tempList = children.select("span.hw").eachText();
                    for (String translation : tempList) {
                        if (tempTranslation.isEmpty()) {
                            tempTranslation = translation;
                        } else {
                            tempTranslation += ", " + translation;
                        }
                    }

                    translationsList.add(tempTranslation);
                    Elements exampleSentence = children.select("div.exampleSentence");
                    for (Element elementExampleSentence : exampleSentence) {
                        exampleSentenceList.add(elementExampleSentence.text());
                    }
                }
                iCountPartOfSpeech++;
            }
        } catch (Exception e) {
            System.err.println(e.toString());
            throw new Exception(e);
        }
    }

    public ArrayList<String> getTranslationsList() {
        return translationsList;
    }

    public ArrayList<String> getExampleSentenceList() {
        return exampleSentenceList;
    }
}
