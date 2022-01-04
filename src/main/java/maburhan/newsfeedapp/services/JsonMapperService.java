package maburhan.newsfeedapp.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.Getter;
import lombok.Setter;
import maburhan.newsfeedapp.dto.NewsDto;
import org.springframework.stereotype.Service;

@Getter
@Setter
@Service
public class JsonMapperService {

    private String json;
    private ObjectMapper mapper;

    public JsonMapperService() {
        mapper = JsonMapper.builder()
                .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS)
                .enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL)
                .findAndAddModules()
                .build();
    }

    public NewsDto mapToNewsDto() {

        NewsDto newsDto = null;
        try {
            newsDto = mapper.readValue(json, NewsDto.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return newsDto;
    }
}
