package ass.manotoma.webserver01.http.util;

/**
 *
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public enum StatusCode {

    _200(200, "OK"),
    _400(400, "Bad Request"),
    _401(401, "Authorization Required"),
    _404(404, "Not Found");
    private final int code;
    private final String description;

    StatusCode(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public String description() {
        return this.description;
    }

    public int code() {
        return this.code;
    }

    //////////  Inner Enums  //////////
    
    public enum Title {

        _400("<html><head><title>Bad Request</title></head><body><h1>"), _401("<html><head><title>Authorization Required</title></head><body><h1>"), _404("<html><head><title>Not Found</title></head><body><h1>");
        private final String text;

        private Title(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }
    }

    public enum Page {

        _400("</h1><p>Web server was unable to understand the request and process it.</p></body></html>"), _401("</h1><p>Authentication required.</p></body></html>"), _404("<html><head><title>Not Found</title></head><body><h1>");
        private final String text;

        private Page(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }
    }
    
}