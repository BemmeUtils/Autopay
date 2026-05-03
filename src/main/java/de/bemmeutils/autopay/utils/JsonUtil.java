package de.bemmeutils.autopay.utils;

import com.google.gson.*;
import de.bemmeutils.autopay.Addon;
import lombok.Getter;
import org.apache.commons.lang3.NotImplementedException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class JsonUtil {
    @Getter
    private JsonObject jsonObject;

    public JsonUtil(JsonObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public static JsonObject readJsonFromFile(String filePath) throws FileNotFoundException, JsonParseException {
        Scanner scanner = new Scanner(new File(filePath));
        StringBuilder jsonContent = new StringBuilder();
        while (scanner.hasNextLine()) {
            jsonContent.append(scanner.nextLine());
        }
        scanner.close();
        return new JsonParser().parse(jsonContent.toString()).getAsJsonObject();
    }

    public static JsonObject parseJson(String jsonString) throws JsonParseException {
        return new JsonParser().parse(jsonString).getAsJsonObject();
    }

    private void saveJsonToFile() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(Addon.getADDON_FILE_PATH())) {
            gson.toJson(this.jsonObject, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Payment getPayment(String uuid) {
        if (!this.jsonObject.has("onetime")) {
            return null;
        }
        JsonArray jsonArray = this.jsonObject.getAsJsonArray("onetime");
        JsonElement jsonElement = null;
        for (JsonElement je : jsonArray) {
            JsonObject jsonContent = je.getAsJsonObject();
            if (!jsonContent.has("uuid") || !jsonContent.get("uuid").getAsString().equalsIgnoreCase(uuid)) continue;
            jsonElement = je;
            break;
        }
        if (jsonElement == null) return null;
        return new Payment(jsonElement.getAsJsonObject().get("uuid").getAsString(), jsonElement.getAsJsonObject().get("playername").getAsString(), jsonElement.getAsJsonObject().get("sum").getAsDouble());
    }

    public void addPayment(String uuid, String playerName, double amount) {
        if (!this.jsonObject.has("onetime")) {
            this.jsonObject.add("onetime", new JsonArray());
        }
        JsonArray jsonArray = this.jsonObject.getAsJsonArray("onetime");
        JsonObject jsonContent = new JsonObject();
        jsonContent.addProperty("uuid", uuid);
        jsonContent.addProperty("playername", playerName);
        jsonContent.addProperty("sum", amount);
        jsonArray.add(jsonContent);
        saveJsonToFile();
    }

    public void editPayment(String uuid, double sum) {
        if (!this.jsonObject.has("onetime")) {
            return;
        }
        JsonArray jsonArray = this.jsonObject.getAsJsonArray("onetime");
        JsonElement jsonElement = null;
        for (JsonElement je : jsonArray) {
            JsonObject jsonContent = je.getAsJsonObject();
            if (!jsonContent.has("uuid") || !jsonContent.get("uuid").getAsString().equalsIgnoreCase(uuid)) continue;
            jsonElement = je;
            break;
        }
        if (jsonElement == null) return;
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        jsonObject.addProperty("sum", sum);
        saveJsonToFile();
    }

    public void deletePayment(String uuid) {
        if (!this.jsonObject.has("onetime")) {
            return;
        }
        JsonArray jsonArray = this.jsonObject.getAsJsonArray("onetime");
        JsonArray updatedJsonArray = new JsonArray();
        for (JsonElement jsonElement : jsonArray) {
            JsonObject jsonContent = jsonElement.getAsJsonObject();
            if (jsonContent.has("uuid") && !jsonContent.get("uuid").getAsString().equalsIgnoreCase(uuid)) {
                updatedJsonArray.add(jsonElement);
            }
        }
        this.jsonObject.add("onetime", updatedJsonArray);
        saveJsonToFile();
    }

    public RecurringPayment getRecurringPayment(String uuid) {
        throw new NotImplementedException("");
    }

    public void addRecurringPayment(String uuid, double amount, int zeitraum, String zeiteinheit) {
        if (!this.jsonObject.has("recurring")) {
            this.jsonObject.add("recurring", new JsonArray());
        }
        saveJsonToFile();
        throw new NotImplementedException("");
    }

    public void removePayment(String uuid) {

    }

}
