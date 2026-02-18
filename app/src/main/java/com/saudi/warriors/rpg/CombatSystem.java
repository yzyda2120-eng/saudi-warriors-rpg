package com.saudi.warriors.rpg;

import android.os.Vibrator;
import android.os.VibrationEffect;
import android.speech.tts.TextToSpeech;
import java.util.HashMap;
import java.util.Map;

/**
 * نظام القتال المتقدم (Advanced Combat System)
 * محاكاة واقعية للأسلحة مع نظام التغذية الراجعة اللمسية.
 */
public class CombatSystem {
    private TextToSpeech tts;
    private Vibrator vibrator;
    private Map<String, Weapon> arsenal;
    private String currentWeapon;

    public CombatSystem(TextToSpeech tts, Vibrator vibrator) {
        this.tts = tts;
        this.vibrator = vibrator;
        this.arsenal = new HashMap<>();
        
        // إضافة أسلحة حقيقية (صقر، شاهين)
        arsenal.put("صقر", new Weapon("صقر", 30, 100, 100)); // رشاش
        arsenal.put("شاهين", new Weapon("شاهين", 10, 200, 250)); // قناص
        currentWeapon = "صقر";
    }

    /**
     * إطلاق النار بسلاح محدد.
     */
    public void fireWeapon() {
        Weapon weapon = arsenal.get(currentWeapon);
        if (weapon != null && weapon.ammo > 0) {
            weapon.ammo--;
            tts.speak("إطلاق نار بسلاح " + weapon.name + ". الذخيرة المتبقية " + weapon.ammo, TextToSpeech.QUEUE_FLUSH, null, null);
            
            // محاكاة الارتداد (Recoil) بناءً على قوة السلاح
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
                vibrator.vibrate(VibrationEffect.createPredefined(VibrationEffect.EFFECT_HEAVY_CLICK));
            } else {
                vibrator.vibrate(weapon.recoilIntensity);
            }
        } else {
            tts.speak("لا توجد ذخيرة في سلاح " + currentWeapon, TextToSpeech.QUEUE_FLUSH, null, null);
        }
    }

    public void switchWeapon(String weaponName) {
        if (arsenal.containsKey(weaponName)) {
            currentWeapon = weaponName;
            tts.speak("تم التبديل إلى سلاح " + weaponName, TextToSpeech.QUEUE_FLUSH, null, null);
        }
    }

    public void reload() {
        Weapon weapon = arsenal.get(currentWeapon);
        if (weapon != null) {
            weapon.ammo = weapon.maxAmmo;
            tts.speak("تمت إعادة تعبئة سلاح " + weapon.name, TextToSpeech.QUEUE_FLUSH, null, null);
            vibrator.vibrate(50);
        }
    }

    private static class Weapon {
        String name;
        int ammo;
        int maxAmmo;
        int recoilIntensity;

        Weapon(String name, int ammo, int maxAmmo, int recoilIntensity) {
            this.name = name;
            this.ammo = ammo;
            this.maxAmmo = maxAmmo;
            this.recoilIntensity = recoilIntensity;
        }
    }
}
