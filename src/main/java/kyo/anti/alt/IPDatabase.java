package kyo.anti.alt;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class IPDatabase {
    // Lưu cấu trúc thân thiện cho Admin dễ đọc file JSON
    public static class Record {
        public String uuid;
        public String name;

        public Record(String uuid, String name) {
            this.uuid = uuid;
            this.name = name;
        }
    }

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File FILE = new File(FabricLoader.getInstance().getConfigDir().toFile(), "kyo_anti.alt.json");

    // Bản đồ Map<IP, Record>
    public static Map<String, Record> db = new ConcurrentHashMap<>();

    public static void load() {
        if (FILE.exists()) {
            try (Reader reader = new FileReader(FILE)) {
                Type type = new TypeToken<Map<String, Record>>(){}.getType();
                Map<String, Record> data = GSON.fromJson(reader, type);
                if (data != null) db.putAll(data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void save() {
        try (Writer writer = new FileWriter(FILE)) {
            GSON.toJson(db, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Trả về TRUE nếu hợp lệ (Chưa ai dùng IP này, hoặc đúng là ông chủ của IP này)
    public static boolean checkAndBind(String ip, String uuid, String name) {
        if (!db.containsKey(ip)) {
            // IP mới toanh -> Khóa chết vào Nick này
            db.put(ip, new Record(uuid, name));
            save();
            return true;
        }
        // IP đã tồn tại -> Chỉ cho vào nếu UUID khớp
        return db.get(ip).uuid.equals(uuid);
    }

    public static String getOwner(String ip) {
        Record rec = db.get(ip);
        return rec != null ? rec.name : "Unknown";
    }
}