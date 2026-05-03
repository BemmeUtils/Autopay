package de.bemmeutils.autopay.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import de.bemmeutils.autopay.Addon;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.FMLLog;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class FileUtil {
    public static void checkCreateFile(String path, List<String> jsonArrays) {
        File file = new File(path);
        if (file.exists()) {
            return;
        } else {
            try {
                if (file.getParentFile() != null && !file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                if (file.createNewFile()) {
                    JsonObject root = new JsonObject();
                    for (String jsonArray : jsonArrays) {
                        root.add(jsonArray, new JsonArray());
                    }
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
                        writer.write(gson.toJson(root));
                        return;
                    } catch (IOException exception) {
                        exception.printStackTrace();
                        return;
                    }
                }
            } catch (IOException exception) {
                exception.printStackTrace();
                return;
            }
        }
        return;
    }

    public static void setupAddonFiles(List<String> jsonArrays) {
        checkCreateFile(Addon.getADDON_FILE_PATH(), jsonArrays);
    }

    public static void migrate() {
        String mcDataDir = Minecraft.getMinecraft().mcDataDir.getAbsolutePath();

        File oldFile = new File(mcDataDir + File.separator + "config" + File.separator + "Autopay" + File.separator + "data.json");
        File newFile = new File(Addon.getADDON_FILE_PATH());

        if (!oldFile.exists() || newFile.exists()) return;

        try {
            // Neues Verzeichnis anlegen falls nötig
            if (!newFile.getParentFile().exists() && !newFile.getParentFile().mkdirs()) {
                FMLLog.bigWarning("[Autopay] Migration fehlgeschlagen: Neues Verzeichnis konnte nicht erstellt werden.");
                return;
            }

            Files.copy(oldFile.toPath(), newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            FMLLog.info("[Autopay] Datei erfolgreich nach %s migriert.", newFile.getPath());

            // Alte Datei + altes Verzeichnis aufräumen
            if (oldFile.delete()) {
                File oldDir = oldFile.getParentFile(); // .../config/Autopay
                String[] remaining = oldDir.list();
                if (remaining != null && remaining.length == 0) {
                    oldDir.delete();
                }
            } else {
                FMLLog.bigWarning("[Autopay] Alte Datei konnte nicht gelöscht werden: %s", oldFile.getPath());
            }

        } catch (IOException e) {
            FMLLog.bigWarning("[Autopay] Migration fehlgeschlagen: %s", e.getMessage());
            e.printStackTrace();
        }
    }
}
