package ru.itis.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;


/*
 * contains count of word on site
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Statistic {

    /*
     * id of object
     */
    private Long id;

    /*
     * url where words count
     */
    private String site;

    /*
     * which word is counting
     */
    private String word;

    /*
     * count of word
     */
    private Integer count;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Statistic)) return false;
        Statistic statistic = (Statistic) o;
        return Objects.equals(site, statistic.site) &&
                Objects.equals(word, statistic.word);
    }
}
