package com.saudi.warriors.rpg;

import android.app.Activity;
import android.os.Bundle;
import android.os.Vibrator;
import android.os.VibrationEffect;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;
import java.util.Locale;

/**
 * النشاط الرئيسي المطور والمستقر للعبة "Saudi War RPG"
 * مصمم ليعمل من أندرويد 8.0 وحتى أندرويد 16+ (SDK 35).
 */
public class MainActivity extends Activity implements TextToSpeech.OnInitListener {

    private TextToSpeech tts;
    private Vibrator vibrator;
    private GestureDetector gestureDetector;
    
    private AINavigationSystem navigationSystem;
    private CombatSystem combatSystem;
    private CraftingSystem craftingSystem;
    private SpatialAudioEngine audioEngine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(android.R.layout.simple_list_item_1);

        // 1. تهيئة الواجهة العربية الذكية (Saudi Accessible UI)
        tts = new TextToSpeech(this, this);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        // 2. تهيئة الأنظمة الحقيقية والمستقرة
        navigationSystem = new AINavigationSystem(tts);
        combatSystem = new CombatSystem(tts, vibrator);
        craftingSystem = new CraftingSystem(tts);
        audioEngine = new SpatialAudioEngine(this);

        // 3. تهيئة نظام الإيماءات السعودية المتقدم (Advanced Saudi Gestures)
        gestureDetector = new GestureDetector(this, new SaudiGestureListener());

        initializeGame();
    }

    private void initializeGame() {
        String status = "تم تشغيل محرك الحرب السعودي (SDK 35). متوافق مع أندرويد 8.0 وحتى 16+.";
        Toast.makeText(this, status, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            tts.setLanguage(new Locale("ar", "SA"));
            tts.speak("مرحباً بك في حرب الصحراء الشاملة. جاري تحميل الأنظمة السعودية المتطورة والمستقرة.", TextToSpeech.QUEUE_FLUSH, null, null);
            
            // محاكاة توجيه اللاعب نحو الهدف الأول
            navigationSystem.updatePlayerPosition(0, 0, 0);
            navigationSystem.guideToTarget(100, 50, 0);
            
            // محاكاة صوت دبابة قادمة من اليمين (Spatial Audio)
            audioEngine.playSpatialSound("دبابة", 90, 0.5f);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event) || super.onTouchEvent(event);
    }

    private class SaudiGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            // إيماءة "صيحة الحرب" - إطلاق نار مع اهتزاز متطور
            combatSystem.fireWeapon();
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            // إيماءة "هزة السيف" - قراءة الحالة الشاملة
            tts.speak("الحالة المحدثة: الدروع مئة بالمئة، الذخيرة كاملة، الاتصال بالسيرفر السعودي ممتاز.", TextToSpeech.QUEUE_FLUSH, null, null);
            craftingSystem.checkResources();
            
            // اهتزاز متوافق مع كافة الإصدارات
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                vibrator.vibrate(50);
            }
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            // إيماءة "سحب سريع" - جمع موارد أو صناعة
            if (velocityX > 0) {
                craftingSystem.collectResource("حديد", 10);
                craftingSystem.collectResource("وقود", 5);
            } else {
                craftingSystem.craftVehicle("دبابة الفهد");
            }
            return true;
        }
    }

    @Override
    protected void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }
}
