package maburhan.newsfeedapp.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class UrlBuilderServiceTest {

    UrlBuilderService urlBuilderService;
    String baseUrl = "http://www.test.com";

    @BeforeEach
    void setUp() {
        urlBuilderService = new UrlBuilderService(baseUrl);

    }

    @Test
    void addParameter() {
        urlBuilderService.addParameter("param_key1", "param_value1");

        String url = urlBuilderService.generateUrl();
        assertEquals("http://www.test.com?param_key1=param_value1", url);
    }

    @Test
    void addParameters() {
        Map<String, String> params = new HashMap<>();
        params.put("param_key1", "param_value1");
        params.put("param_key2", "param_value2");

        urlBuilderService.addParameters(params);

        String url = urlBuilderService.generateUrl();
        String expectedUrl1 = "http://www.test.com?param_key1=param_value1&param_key2=param_value2";
        String expectedUrl2 = "http://www.test.com?param_key2=param_value2&param_key1=param_value1";
        assertTrue(url.equals(expectedUrl1) || url.equals(expectedUrl2));
    }

    @Test
    void whiteSpacesInUrlAreReplaced(){

        urlBuilderService.addParameter("param_key1", "param value1");

        String url = urlBuilderService.generateUrl();
        String expectedUrl = "http://www.test.com?param_key1=param%20value1";
        assertEquals(expectedUrl, url);
    }

    @Test
    void generateUrlWithoutParameters() {
        assertEquals(urlBuilderService.generateUrl(), baseUrl);
    }
}