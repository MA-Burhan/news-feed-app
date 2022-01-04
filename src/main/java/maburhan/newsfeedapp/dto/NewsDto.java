package maburhan.newsfeedapp.dto;

import lombok.*;
import maburhan.newsfeedapp.model.NewsArticle;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewsDto {
    private PageInfo pagination;
    private List<NewsArticle> data;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PageInfo {
        private String limit;
        private String offset;
        private String count;
        private String total;
    }
}
