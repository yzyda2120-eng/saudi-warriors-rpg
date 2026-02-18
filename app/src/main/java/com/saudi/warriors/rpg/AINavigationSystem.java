package com.saudi.warriors.rpg;

import android.speech.tts.TextToSpeech;
import java.util.Locale;

/**
 * نظام التنقل الذكي (Saudi AI Navigation)
 * يساعد المكفوفين على التنقل في ساحة المعركة باستخدام الصوت المحيطي والذكاء الاصطناعي.
 */
public class AINavigationSystem {
    private TextToSpeech tts;
    private float playerX, playerY, playerZ;
    private float targetX, targetY, targetZ;

    public AINavigationSystem(TextToSpeech tts) {
        this.tts = tts;
    }

    /**
     * توجيه اللاعب نحو الهدف باستخدام الصوت.
     */
    public void guideToTarget(float tx, float ty, float tz) {
        this.targetX = tx;
        this.targetY = ty;
        this.targetZ = tz;
        
        float distance = calculateDistance();
        float angle = calculateAngle();

        String direction = getDirectionText(angle);
        tts.speak("الهدف على بعد " + (int)distance + " متر، اتجه نحو " + direction, TextToSpeech.QUEUE_ADD, null, null);
    }

    private float calculateDistance() {
        return (float) Math.sqrt(Math.pow(targetX - playerX, 2) + Math.pow(targetY - playerY, 2));
    }

    private float calculateAngle() {
        return (float) Math.toDegrees(Math.atan2(targetY - playerY, targetX - playerX));
    }

    private String getDirectionText(float angle) {
        if (angle > -22.5 && angle <= 22.5) return "الشرق";
        if (angle > 22.5 && angle <= 67.5) return "الشمال الشرقي";
        if (angle > 67.5 && angle <= 112.5) return "الشمال";
        if (angle > 112.5 && angle <= 157.5) return "الشمال الغربي";
        if (angle > 157.5 || angle <= -157.5) return "الغرب";
        if (angle > -157.5 && angle <= -112.5) return "الجنوب الغربي";
        if (angle > -112.5 && angle <= -67.5) return "الجنوب";
        return "الجنوب الشرقي";
    }

    public void updatePlayerPosition(float x, float y, float z) {
        this.playerX = x;
        this.playerY = y;
        this.playerZ = z;
    }
}
