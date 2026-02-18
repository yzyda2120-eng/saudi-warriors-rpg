package com.saudi.warriors.rpg;

import android.speech.tts.TextToSpeech;
import java.util.HashMap;
import java.util.Map;

/**
 * نظام التصنيع والموارد (Crafting & Resources System)
 * يسمح للاعب بصناعة وتطوير الدبابات والطائرات والمدرعات.
 */
public class CraftingSystem {
    private TextToSpeech tts;
    private Map<String, Integer> resources;

    public CraftingSystem(TextToSpeech tts) {
        this.tts = tts;
        this.resources = new HashMap<>();
        resources.put("حديد", 0);
        resources.put("وقود", 0);
        resources.put("ذخيرة", 0);
    }

    /**
     * جمع الموارد من ساحة المعركة.
     */
    public void collectResource(String resourceName, int amount) {
        int currentAmount = resources.getOrDefault(resourceName, 0);
        resources.put(resourceName, currentAmount + amount);
        tts.speak("تم جمع " + amount + " من " + resourceName + ". المجموع الآن " + (currentAmount + amount), TextToSpeech.QUEUE_ADD, null, null);
    }

    /**
     * صناعة مركبة جديدة.
     */
    public void craftVehicle(String vehicleType) {
        if (resources.getOrDefault("حديد", 0) >= 100 && resources.getOrDefault("وقود", 0) >= 50) {
            resources.put("حديد", resources.get("حديد") - 100);
            resources.put("وقود", resources.get("وقود") - 50);
            tts.speak("تمت صناعة " + vehicleType + " بنجاح. استعد للقيادة.", TextToSpeech.QUEUE_FLUSH, null, null);
        } else {
            tts.speak("لا توجد موارد كافية لصناعة " + vehicleType + ". تحتاج إلى المزيد من الحديد والوقود.", TextToSpeech.QUEUE_FLUSH, null, null);
        }
    }

    /**
     * التحقق من الموارد المتاحة.
     */
    public void checkResources() {
        StringBuilder status = new StringBuilder("الموارد المتاحة: ");
        for (Map.Entry<String, Integer> entry : resources.entrySet()) {
            status.append(entry.getKey()).append(" ").append(entry.getValue()).append("، ");
        }
        tts.speak(status.toString(), TextToSpeech.QUEUE_FLUSH, null, null);
    }
}
