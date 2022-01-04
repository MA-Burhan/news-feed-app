package maburhan.newsfeedapp.services;

import maburhan.newsfeedapp.dto.NewsDto;
import maburhan.newsfeedapp.model.NewsArticle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NewsFetchServiceTest {

    NewsFetchService newsFetchService;

    @Mock
    DownloadService downloadService;

    @Mock
    UrlBuilderService urlBuilderService;

    @Mock
    NewsCrudService newsCrudService;

    @Mock
    JsonMapperService jsonMapperService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        newsFetchService = new NewsFetchService(newsCrudService, urlBuilderService, downloadService, jsonMapperService);
    }

    @Test
    void searchNews() {
        NewsDto.PageInfo pageInfo = new NewsDto.PageInfo("100", "0", "100", "100");
        List<NewsArticle> news = new ArrayList<>();
        news.add(new NewsArticle());
        news.add(new NewsArticle());
        NewsDto newsDto = new NewsDto(pageInfo, news);

        HttpResponse<String> response = mock(HttpResponse.class);
        when(downloadService.getData()).thenReturn(response);
        when(jsonMapperService.mapToNewsDto()).thenReturn(newsDto);

        List<NewsArticle> result = newsFetchService.searchNews();

        assertEquals(2, result.size());
        assertEquals(news, result);

        verify(urlBuilderService, times(1)).generateUrl();
        verify(downloadService, times(1)).getData();
        verify(jsonMapperService, times(1)).mapToNewsDto();

    }

}