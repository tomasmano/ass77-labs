package ass.manotoma.webserver01.server.processor.provider;

import ass.manotoma.webserver01.http.HttpRequest;
import ass.manotoma.webserver01.server.processor.PreProcessor;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public class HttpPreProcessorsProvider implements PreProcessorsProvider<HttpRequest> {

    private static Set<PreProcessor<HttpRequest>> preProcessors = new LinkedHashSet<PreProcessor<HttpRequest>>();

    private static PreProcessorsProvider<HttpRequest> INSTANCE = new HttpPreProcessorsProvider();
    
    private HttpPreProcessorsProvider() {
        
    }
    
    public static PreProcessorsProvider<HttpRequest> getInstance(){
        return INSTANCE;
    }
    

    public HttpRequest preProcess(HttpRequest req) {
        for (PreProcessor<HttpRequest> proc : preProcessors) {
            proc.preProcess(req);
        }
        return req;
    }
    
    public void add(PreProcessor<HttpRequest> processor){
        preProcessors.add(processor);
    }
}
