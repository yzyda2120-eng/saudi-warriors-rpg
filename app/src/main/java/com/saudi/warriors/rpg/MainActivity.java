package com.saudi.warriors.rpg;

import android.app.Activity;
import android.os.Bundle;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;
import java.util.Locale;

/**
 * النشاط الرئيسي المطور بالكامل للعبة "Saudi War RPG"
 * يربط كافة الأنظمة (التنقل، القتال، التصنيع، الحماية، الأونلاين، والواجهة).
 */
public class MainActivity extends Activity implements TextToSpeech.OnInitListener {

    private TextToSpeech tts;
    private Vibrator vibrator;
    private GestureDetector gestureDetector;
    
    private AINavigationSystem navigationSystem;
    private CombatSystem combatSystem;
    private CraftingSystem craftingSystem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(android.R.layout.simple_list_item_1);

        // 1. تهيئة الواجهة العربية الذكية (Saudi Accessible UI)
        tts = new TextToSpeech(this, this);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        // 2. تهيئة الأنظمة المتطورة
        navigationSystem = new AINavigationSystem(tts);
        combatSystem = new CombatSystem(tts, vibrator);
        craftingSystem = new CraftingSystem(tts);

        // 3. تهيئة نظام الإيماءات السعودية المتقدم (Advanced Saudi Gestures)
        gestureDetector = new GestureDetector(this, new SaudiGestureListener());

        initializeGame();
    }

    private void initializeGame() {
        String status = "تم تشغيل محرك الحرب السعودي المطور بالكامل. الأنظمة الستة تعمل بأحدث التقنيات.";
        Toast.makeText(this, status, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            tts.setLanguage(new Locale("ar", "SA"));
            tts.speak("مرحباً بك في حرب الصحراء الشاملة. جاري تحميل الأنظمة السعودية المتطورة.", TextToSpeech.QUEUE_FLUSH, null, null);
            
            // محاكاة توجيه اللاعب نحو الهدف الأول
            navigationSystem.updatePlayerPosition(0, 0, 0);
            navigationSystem.guideToTarget(100, 50, 0);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event) || super.onTouchEvent(event);
    }

    private class SaudiGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            // إيماءة "صيحة الحرب" - إطلاق نار
            combatSystem.fireWeapon("رشاش الصقر الحديث", "آلي");
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            // إيماءة "هزة السيف" - قراءة الحالة الشاملة
            tts.speak("الحالة المحدثة: الدروع مئة بالمئة، الذخيرة كاملة، الاتصال بالسيرفر السعودي ممتاز.", TextToSpeech.QUEUE_FLUSH, null, null);
            craftingSystem.checkResources();
            vibrator.vibrate(50);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            // إيماءة "سحب سريع" - جمع موارد أو صناعة
            if (velocityX > 0) {
                craftingSystem.collectResource("حديد", 10);
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
