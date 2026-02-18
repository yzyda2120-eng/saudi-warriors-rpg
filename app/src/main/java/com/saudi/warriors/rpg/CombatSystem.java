package com.saudi.warriors.rpg;

import android.os.Vibrator;
import android.os.VibrationEffect;
import android.speech.tts.TextToSpeech;

/**
 * نظام القتال المتقدم (Advanced Combat System)
 * محاكاة واقعية للأسلحة مع نظام التغذية الراجعة اللمسية.
 */
public class CombatSystem {
    private TextToSpeech tts;
    private Vibrator vibrator;

    public CombatSystem(TextToSpeech tts, Vibrator vibrator) {
        this.tts = tts;
        this.vibrator = vibrator;
    }

    /**
     * إطلاق النار بسلاح محدد.
     */
    public void fireWeapon(String weaponName, String mode) {
        tts.speak("تم إطلاق النار بسلاح " + weaponName + " في وضع " + mode, TextToSpeech.QUEUE_FLUSH, null, null);
        
        // محاكاة الارتداد باستخدام الاهتزاز (SDK 31+)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            vibrator.vibrate(VibrationEffect.createPredefined(VibrationEffect.EFFECT_HEAVY_CLICK));
        } else {
            vibrator.vibrate(100);
        }
    }

    /**
     * إعادة تعبئة الذخيرة.
     */
    public void reloadWeapon(String weaponName) {
        tts.speak("جاري إعادة تعبئة " + weaponName, TextToSpeech.QUEUE_FLUSH, null, null);
        vibrator.vibrate(50);
    }

    /**
     * التحقق من حالة السلاح.
     */
    public void checkWeaponStatus(String weaponName, int ammo) {
        tts.speak("سلاح " + weaponName + "، الذخيرة المتبقية " + ammo, TextToSpeech.QUEUE_FLUSH, null, null);
    }
}
