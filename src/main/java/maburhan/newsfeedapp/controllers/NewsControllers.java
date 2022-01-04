package maburhan.newsfeedapp.controllers;

import maburhan.newsfeedapp.model.NewsArticle;
import maburhan.newsfeedapp.services.NewsFetchService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class NewsControllers {

    NewsFetchService newsFetchService;

    public NewsControllers(NewsFetchService newsFetchService) {
        this.newsFetchService = newsFetchService;
    }

    @GetMapping("/")
    public String fetchNews(Model model){
        List<NewsArticle> newsArticles = newsFetchService.searchNews();
        model.addAttribute("news", newsArticles);
        return "news";
    }
    @GetMapping("/search")
    public String searchNews(Model model,
                            @RequestParam(required = false) String country,
                            @RequestParam(required = false) String language,
                            @RequestParam(required = false) String source,
                            @RequestParam(required = false) String category,
                            @RequestParam(required = false) String keywords,
                            @RequestParam(required = false) String date){

        Map<String, String> parameters = createParameterMap(country, language, source, category, keywords, date);

        List<NewsArticle> newsArticles = newsFetchService.searchNews(parameters);

        model.addAttribute("news", newsArticles);
        for (Map.Entry<String, String> entry : parameters.entrySet()){
            model.addAttribute(entry.getKey(), entry.getValue());
        }

      return "news";
    }

    private Map<String, String> createParameterMap(String country, String language, String source, String category, String keywords, String date) {

        Map<String, String> parameters = new HashMap<>();

        if(country != null && !country.equalsIgnoreCase("Country")){
            parameters.put("countries", country);
        }
        if(language != null && !language.equalsIgnoreCase("Language")){
            parameters.put("languages", language);
        }
        if(source != null && !source.isEmpty()){
            parameters.put("sources", source);
        }
        if(category != null && !category.equalsIgnoreCase("Category")){
            parameters.put("categories", category);
        }
        if(keywords != null && !keywords.isEmpty()){
            parameters.put("keywords", keywords);
        }
        if(date != null && !date.isEmpty()){
            parameters.put("date", date);
        }

        return parameters;
    }
}
