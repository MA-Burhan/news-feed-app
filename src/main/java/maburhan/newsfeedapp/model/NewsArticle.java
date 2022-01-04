package maburhan.newsfeedapp.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.neovisionaries.i18n.CountryCode;
import com.neovisionaries.i18n.LanguageCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Getter
@Setter
@Entity
@Table(name="news")
public class NewsArticle {

    @JsonIgnore
    @Id
    private String id;

    @Lob
    private String title;

    @Lob
    private String author;

    private String source;
    private String category;

    @Enumerated(EnumType.STRING)
    private LanguageCode language;

    @Enumerated(EnumType.STRING)
    private CountryCode country;

    @Lob
    private String url;

    @Lob
    private String image;

    @Lob
    private String description;

    @JsonAlias("published_at")
    private ZonedDateTime publishedAt;

    public LocalDateTime getPublishedAtInLocalTime(){
        return publishedAt.toLocalDateTime();
    }

}
