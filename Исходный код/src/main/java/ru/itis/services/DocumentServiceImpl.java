package ru.itis.services;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import ru.itis.Main;
import ru.itis.models.Statistic;
import ru.itis.repositories.StatisticRepository;
import ru.itis.repositories.StatisticRepositoryImpl;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;


public class DocumentServiceImpl implements DocumentService {

    private final ParsingService parsingService;

    public DocumentServiceImpl() {
        this.parsingService = new ParsingServiceImpl();
    }

    @Override
    public void downloadPage(String site, String folder, String mode) {
        URL url;
        File file = new File(folder + File.separator + "temp.html");
        try (PrintWriter printWriter = new PrintWriter(file)) {
            BufferedReader bufferedReader;
            if (mode.equals("file")) {
                FileInputStream fileInputStream = new FileInputStream(site);
                bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            } else if (mode.equals("url")) {
                url = new URL(site);
                bufferedReader = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream(), StandardCharsets.UTF_8));
            } else {
                throw new IllegalArgumentException("mode '" + mode + "' doesn't support");
            }

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                printWriter.println(line);
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }


    @Override
    public Map<String, Integer> statistic(String site, String type) {
        Map<String, Integer> map = new HashMap<>();
        Document document;

        File file = new File(site);
        try {
            if (type.equals("file")) {
                document = Jsoup.parse(file, "UTF-8");
            } else if (type.equals("url")) {
                document = Jsoup.connect(site).get();
            } else {
                throw new IllegalArgumentException("type '" + type + "' doesn't support");
            }

            List<String> wordList = parsingService.parse(document);
            for (String word : wordList) {
                if (map.containsKey(word)) {
                    int count = map.get(word) + 1;
                    map.put(word, count);
                } else {
                    map.put(word, 1);
                }
            }


        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        return map;
    }

    @Override
    public void save(Map<String, Integer> map, String site) {


        StatisticRepository statisticRepository
                = new StatisticRepositoryImpl(Main.connection);

        for (String word : map.keySet()) {
            Statistic statistic = Statistic.builder()
                    .site(site)
                    .word(word)
                    .count(map.get(word))
                    .build();
            statisticRepository.save(statistic);
        }

        System.out.println("Saved to data base!");
    }
}
