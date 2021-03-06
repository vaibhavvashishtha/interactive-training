package entity.config;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.util.Date;
import java.util.Map;

/**
 * Created by vku131 on 2/10/17.
 */
@Entity(name = "config")
public class Config {
    @Id
    private Long id;
    @Index
    private String groupName;
    @Index
    private Map<String, Object> configMap;

    public Config() {

    }

    public Config(String groupName, Map<String, Object> configMap) {
        this.groupName = groupName;
        this.configMap = configMap;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Map<String, Object> getConfigMap() {
        return configMap;
    }

    public void setConfigMap(Map<String, Object> configMap) {
        this.configMap = configMap;
    }
}
