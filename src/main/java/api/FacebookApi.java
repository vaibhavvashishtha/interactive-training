package api;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import com.google.gson.Gson;
import send.Facebook;
import send.ImageMessage;
import send.TextMessage;


import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by vku131 on 2/10/17.
 */
@Api(name = "reveal",
        version = "v1",
        scopes = {Constants.EMAIL_SCOPE},
        clientIds = {Constants.WEB_CLIENT_ID, Constants.ANDROID_CLIENT_ID, Constants.IOS_CLIENT_ID},
        audiences = {Constants.ANDROID_AUDIENCE,},
        title = "Interactive Training",
        description = "Interactive Training Presentation api"

)
public class FacebookApi {
    private static Logger logger = Logger.getLogger(FacebookApi.class.getName());
    private Facebook facebook;
    private Gson gson = new Gson();
    private TextMessage textMessage;
    private ImageMessage imageMessage;
    private Map<String, String> msgMap;
    private Map<String, String> recipientMap;
    private String msgPayload;

    @ApiMethod(name = "facebook.sendtextmsg", path = "facebook_sendtextmsg", httpMethod = "POST")
    public Map<String, String> sendTextMsg(@Named("msg") String msg, @Named("recipient") String recipient) {
        this.facebook = new Facebook();
        this.textMessage = new TextMessage();
        msgMap = new HashMap<>();
        this.recipientMap = new HashMap<>();
        this.msgMap.put("text", msg);
        this.recipientMap.put("id", recipient);
        this.textMessage.setRecipient(recipientMap);
        this.textMessage.setMessage(msgMap);
        this.msgPayload = gson.toJson(textMessage);
        logger.warning("msgPayload = " + this.msgPayload);
        return facebook.sendMessage(this.msgPayload);
    }

    @ApiMethod(name = "facebook.sendimagemsg", path = "facebook_sendimagemsg", httpMethod = "POST")
    public Map<String, String> sendImageMsg(@Named("msg") String url, @Named("recipient") String recipient) {
//        TODO implement image size limit.
//        TODO need to handle request timeout.
        this.facebook = new Facebook();
        this.recipientMap = new HashMap<>();
        try {
            this.imageMessage = new ImageMessage(new URL(url));
            this.recipientMap.put("id", recipient);
            this.imageMessage.setRecipient(this.recipientMap);
        } catch (MalformedURLException mue) {
            logger.warning(mue.getMessage());
        }
        this.msgPayload = gson.toJson(imageMessage);
        logger.warning("msgPayload = " + msgPayload);
        return facebook.sendMessage(msgPayload);
    }
}
