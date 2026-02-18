package com.saudi.warriors.rpg;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.SoundPool;
import java.util.HashMap;
import java.util.Map;

/**
 * محرك الصوت المحيطي (3D Spatial Audio Engine)
 * يمثل "عين" اللاعب المكفوف بدقة 360 درجة.
 */
public class SpatialAudioEngine {
    private SoundPool soundPool;
    private Map<String, Integer> soundMap;
    private Context context;

    public SpatialAudioEngine(Context context) {
        this.context = context;
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        
        soundPool = new SoundPool.Builder()
                .setMaxStreams(10)
                .setAudioAttributes(audioAttributes)
                .build();
        
        soundMap = new HashMap<>();
    }

    /**
     * تشغيل صوت في موقع محدد بالنسبة للاعب.
     * @param soundName اسم الصوت (رصاص، دبابة، انفجار)
     * @param azimuth الزاوية الأفقية (0-360 درجة)
     * @param distance المسافة (0.0 إلى 1.0 حيث 1.0 هو الأقرب)
     */
    public void playSpatialSound(String soundName, float azimuth, float distance) {
        Integer soundId = soundMap.get(soundName);
        if (soundId == null) return;

        // حساب شدة الصوت لكل أذن (Stereo Panning)
        float leftVolume = 1.0f;
        float rightVolume = 1.0f;

        if (azimuth > 0 && azimuth < 180) {
            leftVolume = 1.0f - (azimuth / 180.0f);
        } else if (azimuth >= 180 && azimuth < 360) {
            rightVolume = (azimuth - 180.0f) / 180.0f;
        }

        // تطبيق المسافة (Distance Attenuation)
        leftVolume *= distance;
        rightVolume *= distance;

        soundPool.play(soundId, leftVolume, rightVolume, 1, 0, 1.0f);
    }

    public void loadSound(String name, int resId) {
        soundMap.put(name, soundPool.load(context, resId, 1));
    }
}
