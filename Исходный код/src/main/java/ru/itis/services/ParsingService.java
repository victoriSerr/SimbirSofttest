package ru.itis.services;

import org.jsoup.nodes.Document;

import java.util.List;

public interface ParsingService {

    /*
        метод parse принимает на вход объект класса org.jsoup.nodes.Document,
        в котором идет перебор всех TextNode, из которых извлекается текст
        и далее отправляется в метод split
        для получения отдельных слов
     */
    /*
     * html-document parsing
     * @param document Document object of html web page
     * @return List<String> list of words on web page
     */
    public List<String> parse(Document document);
}
