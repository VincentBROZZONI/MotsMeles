package com.example.info706.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

public class CanvasGrille extends View {

    private Paint paint;
    private Rect rect;

    public CanvasGrille(Context context) {
        super(context);
        paint = new Paint();
        rect = new Rect();
    }

    @Override
    public void onDraw (Canvas canvas){
            super.onDraw(canvas);
            paint.setColor(Color.BLUE);
            paint.setStrokeWidth(3);

            canvas.drawRect(0,0,200,200,paint);
    }
}
