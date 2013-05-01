package ass.manotoma.webserver01.server.processor.provider;

import ass.manotoma.webserver01.http.HttpRequest;
import ass.manotoma.webserver01.http.HttpResponse;
import ass.manotoma.webserver01.server.processor.PostProcessor;
import java.util.HashSet;
import java.util.Set;


public class HttpPostProcessorsProvider implements PostProcessorsProvider<HttpRequest, HttpResponse> {
    
    private static Set<PostProcessor<HttpRequest, HttpResponse>> preProcessors = new HashSet<PostProcessor<HttpRequest, HttpResponse>>();
    
    private static PostProcessorsProvider<HttpRequest, HttpResponse> INSTANCE = new HttpPostProcessorsProvider();
    
    public static PostProcessorsProvider<HttpRequest, HttpResponse> getInstance(){
        return INSTANCE;
    }

    public HttpResponse postProcess(HttpRequest req, HttpResponse res) {
        for (PostProcessor<HttpRequest, HttpResponse> processor : preProcessors) {
            processor.postProcess(req, res);
        }
        return res;
    }

    public void add(PostProcessor<HttpRequest, HttpResponse> processor) {
        preProcessors.add(processor);
    }
    
}
