package ru.itis.services;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class ParsingServiceImpl implements ParsingService{



    @Override
    public List<String> parse(Document document) {

        List<String> list = new ArrayList<>();

        for (Element el : document.select("body").select("*")) {

            for (TextNode textNode : el.textNodes()) {
                if (textNode.text().trim().length() > 0) {

                    String text = textNode.text();
                    List<String> words = split(text);
                    list.addAll(words);

                }
            }
        }
        return list;
    }

    private List<String> split(String text) {
        List<String> words = new ArrayList<>(Arrays.asList(text.split("[ '.,!?\";:()\\[«»{}\\]\n\r\t]")));
        words.removeIf(a -> a.matches("([0-9]?-?/?)+"));
        words.removeIf(String::isEmpty);
        words = words.stream().map(String::toUpperCase).collect(Collectors.toList());
        return words;
    }

}
