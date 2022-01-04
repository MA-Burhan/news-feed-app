package maburhan.newsfeedapp.services;

import maburhan.newsfeedapp.dto.NewsDto;
import maburhan.newsfeedapp.model.NewsArticle;
import maburhan.newsfeedapp.repositories.NewsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsCrudService {

    private NewsRepository repository;

    public NewsCrudService(NewsRepository repository) {
        this.repository = repository;
    }

    public void save(NewsDto newsDto){

        List<NewsArticle> newsArticles = newsDto.getData();

        for(NewsArticle newsArticle : newsArticles){
            //create unique string composed of 50 first characters of String + timestamp
            // to be used as primary key when persisting the data
            String id = newsArticle.getTitle().substring(0, Math.min(50, newsArticle.getTitle().length())) + newsArticle.getPublishedAt();
            newsArticle.setId(id);
        }

        repository.saveAll(newsArticles);
    }
}
