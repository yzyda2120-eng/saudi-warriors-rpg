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
    private Map<String, Vehicle> vehicles;

    public CraftingSystem(TextToSpeech tts) {
        this.tts = tts;
        this.resources = new HashMap<>();
        this.vehicles = new HashMap<>();
        
        resources.put("حديد", 0);
        resources.put("وقود", 0);
        resources.put("ذخيرة", 0);
        
        // إضافة مركبات حقيقية (دبابة الفهد، مدرعة النمر)
        vehicles.put("دبابة الفهد", new Vehicle("دبابة الفهد", 100, 50, 200));
        vehicles.put("مدرعة النمر", new Vehicle("مدرعة النمر", 50, 30, 100));
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
        Vehicle vehicle = vehicles.get(vehicleType);
        if (vehicle != null) {
            if (resources.getOrDefault("حديد", 0) >= vehicle.ironCost && resources.getOrDefault("وقود", 0) >= vehicle.fuelCost) {
                resources.put("حديد", resources.get("حديد") - vehicle.ironCost);
                resources.put("وقود", resources.get("وقود") - vehicle.fuelCost);
                tts.speak("تمت صناعة " + vehicleType + " بنجاح. استعد للقيادة.", TextToSpeech.QUEUE_FLUSH, null, null);
            } else {
                tts.speak("لا توجد موارد كافية لصناعة " + vehicleType + ". تحتاج إلى المزيد من الحديد والوقود.", TextToSpeech.QUEUE_FLUSH, null, null);
            }
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

    private static class Vehicle {
        String name;
        int ironCost;
        int fuelCost;
        int armor;

        Vehicle(String name, int ironCost, int fuelCost, int armor) {
            this.name = name;
            this.ironCost = ironCost;
            this.fuelCost = fuelCost;
            this.armor = armor;
        }
    }
}
