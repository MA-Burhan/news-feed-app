package maburhan.newsfeedapp.services;

import lombok.Getter;
import lombok.Setter;
import maburhan.newsfeedapp.dto.NewsDto;
import maburhan.newsfeedapp.model.NewsArticle;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Service
public class NewsFetchService {

    private final NewsCrudService newsCrudService;
    private final UrlBuilderService urlBuilderService;
    private final DownloadService downloadService;
    private final JsonMapperService jsonMapperService;

    @Value("${news.api.base.url}")
    private String baseUrl;

    @Value("${news.api.access.key}")
    private String accessKey;

    private String sort;
    private int limit;

    public NewsFetchService(NewsCrudService newsCrudService, UrlBuilderService urlBuilderService,
                            DownloadService downloadService, JsonMapperService jsonMapperService) {
        this.newsCrudService = newsCrudService;
        this.urlBuilderService = urlBuilderService;
        this.downloadService = downloadService;
        this.jsonMapperService = jsonMapperService;
        sort = "published_desc";
        limit = 100;
    }

    public List<NewsArticle> searchNews() {

        return searchNews(Collections.emptyMap());
    }

    public List<NewsArticle> searchNews(Map<String, String> searchParameters) {

        downloadService.setUrl(buildUrl(searchParameters));
        HttpResponse<String> response = downloadService.getData();

        jsonMapperService.setJson(response.body());
        NewsDto newsDto = jsonMapperService.mapToNewsDto();

        // TODO remove - this is side effect
        //Persist fetch news to database
        newsCrudService.save(newsDto);

        System.out.println(newsDto.getData().size());
        return newsDto.getData();
    }

    private String buildUrl(Map<String, String> searchParameters) {
        urlBuilderService.setBaseUrl(baseUrl);
        urlBuilderService.clearParameters();
        urlBuilderService.addParameter("access_key", accessKey);
        urlBuilderService.addParameter("sort", sort);
        urlBuilderService.addParameter("limit", Integer.toString(limit));
        urlBuilderService.addParameters(searchParameters);

        String fullUrl = urlBuilderService.generateUrl();
        System.out.println(fullUrl);
        return fullUrl;
    }
}
