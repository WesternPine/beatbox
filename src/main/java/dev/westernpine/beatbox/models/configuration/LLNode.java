package dev.westernpine.beatbox.models.configuration;

public class LLNode {
    String name;
    String url;
    String password;

    public LLNode(String name, String url, String password) {
        this.name = name;
        this.url = url;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getPassword() {
        return password;
    }
}
