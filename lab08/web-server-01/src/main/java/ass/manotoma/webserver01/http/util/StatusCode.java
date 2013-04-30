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

}