package zeev.fraiman.trigo_claude;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class TrigonometryCircleView extends View {

    private Paint circlePaint;
    private Paint linePaint;
    private float angle = 0f;
    private String highlightedFunction = null;
    private Paint sinPaint, cosPaint, tanPaint, cotPaint, secPaint, cscPaint;


    public TrigonometryCircleView(Context context) {
        super(context);
        init();
    }

    public TrigonometryCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TrigonometryCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setColor(0xFF888888); // Gray
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(5);

        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setColor(0xFFFF0000); // Red
        linePaint.setStrokeWidth(8);

        sinPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        sinPaint.setColor(0xFF00FF00); // Green
        sinPaint.setStrokeWidth(5);

        cosPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        cosPaint.setColor(0xFF0000FF); // Blue
        cosPaint.setStrokeWidth(5);

        tanPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        tanPaint.setColor(0xFFFF00FF); // Magenta
        tanPaint.setStrokeWidth(5);
        
        cotPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        cotPaint.setColor(0xFFFFFF00); // Yellow
        cotPaint.setStrokeWidth(5);

        secPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        secPaint.setColor(0xFF00FFFF); // Cyan
        secPaint.setStrokeWidth(5);

        cscPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        cscPaint.setColor(0xFFFF8800); // Orange
        cscPaint.setStrokeWidth(5);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float centerX = getWidth() / 2f;
        float centerY = getHeight() / 2f;
        float radius = Math.min(centerX, centerY) * 0.8f;

        // Draw the unit circle
        canvas.drawCircle(centerX, centerY, radius, circlePaint);

        // Draw axes
        canvas.drawLine(centerX - radius * 1.1f, centerY, centerX + radius * 1.1f, centerY, circlePaint);
        canvas.drawLine(centerX, centerY - radius * 1.1f, centerX, centerY + radius * 1.1f, circlePaint);

        // Calculate the line end point based on the angle
        float angleInRadians = (float) Math.toRadians(angle);
        float cosA = (float) Math.cos(angleInRadians);
        float sinA = (float) Math.sin(angleInRadians);

        float endX = centerX + radius * cosA;
        float endY = centerY - radius * sinA; // Y is inverted in canvas

        // Draw the line for the angle
        canvas.drawLine(centerX, centerY, endX, endY, linePaint);

        if (highlightedFunction != null) {
            float tanValue = (cosA != 0) ? sinA / cosA : Float.POSITIVE_INFINITY;
            float cotValue = (sinA != 0) ? cosA / sinA : Float.POSITIVE_INFINITY;

            switch (highlightedFunction) {
                case "sin":
                    canvas.drawLine(endX, endY, endX, centerY, sinPaint);
                    break;
                case "cos":
                    canvas.drawLine(endX, endY, centerX, endY, cosPaint);
                    break;
                case "tan":
                    if (Float.isFinite(tanValue)) {
                        float tan_x = centerX + radius;
                        float tan_y_end = centerY - radius * tanValue;
                        if (cosA < 0) { // Quadrants 2 and 3
                           tan_x = centerX - radius;
                           tan_y_end = centerY + radius * tanValue;
                        }
                        canvas.drawLine(tan_x, centerY, tan_x, tan_y_end, tanPaint);
                    }
                    break;
                case "cot":
                    if (Float.isFinite(cotValue)) {
                        float cot_y = centerY - radius;
                        float cot_x_end = centerX + radius * cotValue;
                         if (sinA < 0) { // Quadrants 3 and 4
                           cot_y = centerY + radius;
                           cot_x_end = centerX - radius * cotValue;
                        }
                        canvas.drawLine(centerX, cot_y, cot_x_end, cot_y, cotPaint);
                    }
                    break;
                case "sec":
                    if (Float.isFinite(tanValue)) {
                        float tan_x = centerX + radius;
                        float tan_y_end = centerY - radius * tanValue;
                        if (cosA < 0) { // Quadrants 2 and 3
                           tan_x = centerX - radius;
                           tan_y_end = centerY + radius * tanValue;
                        }
                        canvas.drawLine(centerX, centerY, tan_x, tan_y_end, secPaint);
                    }
                    break;
                case "csc":
                     if (Float.isFinite(cotValue)) {
                        float cot_y = centerY - radius;
                        float cot_x_end = centerX + radius * cotValue;
                         if (sinA < 0) { // Quadrants 3 and 4
                           cot_y = centerY + radius;
                           cot_x_end = centerX - radius * cotValue;
                        }
                        canvas.drawLine(centerX, centerY, cot_x_end, cot_y, cscPaint);
                    }
                    break;
            }
        }
    }

    /**
     * Sets the angle and redraws the view.
     * @param angle in degrees
     */
    public void setAngle(float angle) {
        this.angle = angle;
        invalidate(); // Request a redraw
    }

    public void setHighlightedFunction(String function) {
        this.highlightedFunction = function;
        invalidate();
    }
}
