package dev.westernpine.beatbox.models.configuration;

import java.util.Collection;
import java.util.List;

/**
 * Schema
 * required... = No checks are performed to validate data, and/or these fields are required for the application to run.
 * ... = Not required for app, but should be configured if possible.
 */
public class Configuration {

    public transient boolean configGenerated = false;

    /**
     * Required discord token for the app to run.
     */
    public String requiredDiscordToken = "SomeRequiredTokenString";

    public String activity = "Coming Soon? o.0";

    public Collection<LLNode> llNodes = List.of(new LLNode("HostPC", "http://10.0.0.6:2333", "youshallnotpass"));

}


