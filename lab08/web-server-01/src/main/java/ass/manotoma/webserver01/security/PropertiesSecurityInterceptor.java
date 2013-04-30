package ass.manotoma.webserver01.security;

import ass.manotoma.webserver01.Bootstrap;
import com.google.common.collect.Sets;
import java.util.Set;


public class PropertiesSecurityInterceptor implements SecurityInterceptor {
    
    private Set<String> urls;
    private static PropertiesSecurityInterceptor INSTANCE = new PropertiesSecurityInterceptor();

    private PropertiesSecurityInterceptor() {
        String property = Bootstrap.properties.getProperty("intercepted_urls");
        String[] urlsArr = property.split(" ");
        urls = Sets.newHashSet(urlsArr);
    }
    
    public static PropertiesSecurityInterceptor getInstance(){
        return INSTANCE;
    }

    public boolean isApplied(String url) {
        return urls.contains(url);
    }
    
}
