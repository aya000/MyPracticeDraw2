package com.hencoder.hencoderpracticedraw2.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ComposePathEffect;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.DiscretePathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathEffect;
import android.graphics.SumPathEffect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class Practice12PathEffectView extends View {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Path path = new Path();

    public Practice12PathEffectView(Context context) {
        super(context);
    }

    public Practice12PathEffectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice12PathEffectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        paint.setStyle(Paint.Style.STROKE);

        path.moveTo(50, 100);
        path.rLineTo(50, 100);
        path.rLineTo(80, -150);
        path.rLineTo(100, 100);
        path.rLineTo(70, -120);
        path.rLineTo(150, 80);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 使用 Paint.setPathEffect() 来设置不同的 PathEffect

        // 第一处：CornerPathEffect
        CornerPathEffect cornerEffect = new CornerPathEffect(50);
        paint.setPathEffect(cornerEffect);
        canvas.drawPath(path, paint);

        canvas.save();
        canvas.translate(500, 0);
        // 第二处：DiscretePathEffect
        PathEffect discreteEffect = new DiscretePathEffect(20, 5);
        paint.setPathEffect(discreteEffect);
        canvas.drawPath(path, paint);
        canvas.restore();

        canvas.save();
        canvas.translate(0, 200);
        // 第三处：DashPathEffect
        /**
         * 此处数组长度必须为偶数
         * 它的构造方法 DashPathEffect(float[] intervals, float phase) 中，
         * 第一个参数 intervals 是一个数组，它指定了虚线的格式：
         * 数组中元素必须为偶数（最少是 2 个），
         * 按照「画线长度、空白长度、画线长度、空白长度」……的顺序排列，
         * 例如上面代码中的 20, 5, 10, 5 就表示虚线是按照「画 20 像素、空 5 像素、画 10 像素、空 5 像素」的模式来绘制；
         * 第二个参数 phase 是虚线的偏移量。
         * */
        PathEffect dashEffect = new DashPathEffect(new float[]{5, 20, 30, 10}, 5);
        paint.setPathEffect(dashEffect);
        canvas.drawPath(path, paint);
        canvas.restore();

        canvas.save();
        canvas.translate(500, 200);
        // 第四处：PathDashPathEffect
        Path pathDash = new Path();
        pathDash.addRoundRect(0, 0, 10, 10, 5, 15, Path.Direction.CW);
        PathEffect pathDashPath = new PathDashPathEffect(pathDash, 30, 20, PathDashPathEffect.Style.TRANSLATE);
        paint.setPathEffect(pathDashPath);
        canvas.drawPath(path, paint);
        canvas.restore();

        canvas.save();
        canvas.translate(0, 400);
        // 第五处：SumPathEffect
        /**
         * 这是一个组合效果类的 PathEffect 。它的行为特别简单，就是分别按照两种 PathEffect 分别对目标进行绘制。
         * */
        PathEffect effect1 = new CornerPathEffect(60);
        PathEffect effect2 = new DashPathEffect(new float[]{10, 5, 20, 10}, 10);
        PathEffect sumEffect = new SumPathEffect(effect1, effect2);
        paint.setPathEffect(sumEffect);
        canvas.drawPath(path, paint);
        canvas.restore();

        canvas.save();
        canvas.translate(500, 400);
        // 第六处：ComposePathEffect
        /**
         * 这也是一个组合效果类的 PathEffect 。不过它是先对目标 Path 使用一个 PathEffect，然后再对这个改变后的 Path 使用另一个 PathEffect。
         * 它的构造方法 ComposePathEffect(PathEffect outerpe, PathEffect innerpe) 中的两个 PathEffect 参数，  innerpe 是先应用的， outerpe 是后应用的。
         * */
        PathEffect outEffect = new CornerPathEffect(30);
        PathEffect innerEffect = new DiscretePathEffect(20, 5);
        PathEffect composeEffect = new ComposePathEffect(outEffect, innerEffect);
        paint.setPathEffect(composeEffect);
        canvas.drawPath(path, paint);
        canvas.restore();
    }
}
