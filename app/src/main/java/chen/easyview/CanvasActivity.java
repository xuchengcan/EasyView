package chen.easyview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import chen.easyview.view.CanvasTestView;

public class CanvasActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvas);

        CanvasTestView canvasTest = (CanvasTestView)findViewById(R.id.canvas);
//        canvasTest.invalidate();



    }


}
