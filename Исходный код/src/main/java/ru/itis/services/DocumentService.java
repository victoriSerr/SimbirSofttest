package ru.itis.services;

import ru.itis.models.Statistic;

import java.util.Map;

public interface DocumentService {

    /*
     * download html page
     * @param site url string of site or path to html-document
     * @param folder string with folder where html-document downloading
     * @param mode mode of current url or path to html-document(for testing)
     */
    void downloadPage(String site, String folder, String type);

    /*
     * counting of unics words on page
     * @param site url string of site or path to html-document
     * @param mode mode of current url or path to html-document(for testing)
     */
    Map<String, Integer> statistic(String site, String mode);

    /*
     * save result to data base
     * @param statistic Statistic object with statistic info
     */
    void save(Map<String, Integer> map, String site);
}
