package delphinadrealms;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by henry27 on 8/17/2017.
 */
public class utilities {

    public static String urlEncode(String url) throws UnsupportedEncodingException {

        return URLEncoder.encode(url,"UTF-8");

    }
}
