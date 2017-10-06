package com.grayzlp.ggithub.data.model.event.inheritance;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.grayzlp.ggithub.data.model.event.BaseEvent;
import com.grayzlp.ggithub.util.LogUtils;

import java.lang.reflect.Type;

/**
 * A json deserializer for event model which payload type is not fixed.
 */

public class BaseEventDeserializer implements JsonDeserializer<BaseEvent> {

    private static final String TAG = LogUtils.makeLogTag("BaseEventDeserializer");
    private static final Class<BaseEventDeserializer> CURRENT_CLASS = BaseEventDeserializer.class;

    @Override
    public BaseEvent deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String type = jsonObject.get("type").getAsString();

        // Reflection require full-qualified class name
        String fullQualified = CURRENT_CLASS.getName().replace(CURRENT_CLASS.getSimpleName(), type);

        Class<? extends BaseEvent> concreteEvent = null;
        try {
            concreteEvent = Class.forName(fullQualified).asSubclass(BaseEvent.class);
        } catch (ClassNotFoundException e) {
            LogUtils.LOGW(TAG, "Unknown event type : " + type);
        }
        if (concreteEvent != null) {
            return new Gson().fromJson(json, concreteEvent);
        } else {
            // no-op
            return new Gson().fromJson(json, BaseEvent.class);
        }
    }
}
