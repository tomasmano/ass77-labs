package ass.manotoma.webserver01.cache;

/**
 *
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public class ContentHolder {

    private byte[] data;

    public ContentHolder(byte[] data) {
        this.data = data;
    }

    public byte[] getBytes() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
