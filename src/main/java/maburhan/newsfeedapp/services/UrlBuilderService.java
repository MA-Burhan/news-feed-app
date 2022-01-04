package maburhan.newsfeedapp.services;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Service
public class UrlBuilderService {

    private String baseUrl;
    private final Map<String, String> parameters = new HashMap<>();

    public UrlBuilderService() {
    }

    public UrlBuilderService(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public void addParameter(String key, String value){
        parameters.put(key,value);
    }
    public void addParameters(Map<String, String> params){
        for (Map.Entry<String, String> entry : params.entrySet()) {
            parameters.put(entry.getKey(), entry.getValue());
        }
    }

    public void clearParameters(){
        parameters.clear();
    }

    public String generateUrl(){
        StringBuilder builder = new StringBuilder(baseUrl);

        boolean first = true;
        for (Map.Entry<String, String> parameter : parameters.entrySet()) {
            if(first){
                builder.append("?");
                first = false;
            } else {
                builder.append("&");
            }
            builder.append(parameter.getKey());
            builder.append("=");
            builder.append(parameter.getValue().replace(" ", "%20"));
        }

        String url = builder.toString();
        return url;
    }

}
