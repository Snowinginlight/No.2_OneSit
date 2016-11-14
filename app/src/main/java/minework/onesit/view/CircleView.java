package minework.onesit.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by 无知 on 2016/11/11.
 */

public class CircleView extends ImageView {

    private Paint mPaint;
    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(getWidth()/2,getHeight()/2,getWidth()/2,mPaint);
    }
}
