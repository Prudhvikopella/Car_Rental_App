package com.android.carrentalapp.roundview;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.Dimension;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.android.carrentalapp.R;

/**
 * Created by xulc 2022/8/8
 */
public class RoundViewDelegate {

    public static final int DEFAULT_COLOR_WHITE = Color.parseColor("#FFFFFF");
    public static final int DEFAULT_COLOR_TRANSPARENT = Color.parseColor("#00FFFFFF");

    protected static final int INVALID_ID = 0;
    private View view;
    private Context context;
    private @ColorRes
    int backgroundColorRes;
    private @ColorRes
    int backgroundPressColorRes;
    private @ColorRes
    int strokeColorRes;
    private @ColorRes
    int strokePressColorRes;
    private @ColorRes
    int textPressColorRes;
    private @ColorInt
    int backgroundColor;
    private @ColorInt
    int backgroundPressColor;
    private @ColorInt
    int strokeColor;
    private @ColorInt
    int strokePressColor;
    private @ColorInt
    int textPressColor;
    private @Dimension
    int strokeWidth;
    private @Dimension
    int cornerRadius;
    private @Dimension
    int cornerRadiusTL;
    private @Dimension
    int cornerRadiusTR;
    private @Dimension
    int cornerRadiusBL;
    private @Dimension
    int cornerRadiusBR;
    private boolean isRadiusHalfHeight;
    private boolean isWidthHeightEqual;
    private boolean isRippleEnable;

    private GradientDrawable gdBackground = new GradientDrawable();
    private GradientDrawable gdBackgroundPress = new GradientDrawable();
    private float[] radiusArr = new float[8];

    public RoundViewDelegate(View view, Context context, @Nullable AttributeSet attrs) {
        this.view = view;
        this.context = context;
        obtainAttributes(context, attrs);
        setBgSelector();
    }

    private void obtainAttributes(Context context, @Nullable AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RoundViewDelegate);
        backgroundColor = a.getColor(R.styleable.RoundViewDelegate_uiCoreRvBackgroundColor, DEFAULT_COLOR_TRANSPARENT);
        backgroundPressColor = a.getColor(R.styleable.RoundViewDelegate_uiCoreRvBackgroundPressColor, DEFAULT_COLOR_WHITE);
        strokeColor = a.getColor(R.styleable.RoundViewDelegate_uiCoreRvStrokeColor, DEFAULT_COLOR_TRANSPARENT);
        strokePressColor = a.getColor(R.styleable.RoundViewDelegate_uiCoreRvStrokePressColor, DEFAULT_COLOR_WHITE);
        textPressColor = a.getColor(R.styleable.RoundViewDelegate_uiCoreRvTextPressColor, DEFAULT_COLOR_WHITE);

        strokeWidth = a.getDimensionPixelSize(R.styleable.RoundViewDelegate_uiCoreRvStrokeWidth, 0);
        cornerRadius = a.getDimensionPixelSize(R.styleable.RoundViewDelegate_uiCoreRvCornerRadius, 0);
        cornerRadiusTL = a.getDimensionPixelSize(R.styleable.RoundViewDelegate_uiCoreRvCornerRadiusTL, 0);
        cornerRadiusTR = a.getDimensionPixelSize(R.styleable.RoundViewDelegate_uiCoreRvCornerRadiusTR, 0);
        cornerRadiusBL = a.getDimensionPixelSize(R.styleable.RoundViewDelegate_uiCoreRvCornerRadiusBL, 0);
        cornerRadiusBR = a.getDimensionPixelSize(R.styleable.RoundViewDelegate_uiCoreRvCornerRadiusBR, 0);
        isRadiusHalfHeight = a.getBoolean(R.styleable.RoundViewDelegate_uiCoreRvIsRadiusHalfHeight, false);
        isWidthHeightEqual = a.getBoolean(R.styleable.RoundViewDelegate_uiCoreRvIsWidthHeightEqual, false);
        isRippleEnable = a.getBoolean(R.styleable.RoundViewDelegate_uiCoreRvIsRippleEnable, true);
        a.recycle();
    }

    public void setBgSelector() {
        StateListDrawable bg = new StateListDrawable();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && isRippleEnable) {
            setDrawable(gdBackground, backgroundColor, strokeColor);
            RippleDrawable rippleDrawable = new RippleDrawable(getPressedColorSelector(backgroundColor, backgroundPressColor), gdBackground, null);
            view.setBackground(rippleDrawable);
        } else {
            setDrawable(gdBackground, backgroundColor, strokeColor);
            //-android.R.attr.state_pressed means the value of state_pressed = false
            bg.addState(new int[]{-android.R.attr.state_pressed}, gdBackground);
            if (backgroundPressColor != Integer.MAX_VALUE || strokePressColor != Integer.MAX_VALUE) {
                int color;
                int strokeColor;
                if (backgroundPressColor == Integer.MAX_VALUE) {
                    color = backgroundColor;
                } else {
                    color = backgroundPressColor;
                }
                if (strokePressColor == Integer.MAX_VALUE) {
                    strokeColor = this.strokeColor;
                } else {
                    strokeColor = this.strokePressColor;
                }
                setDrawable(gdBackgroundPress, color, strokeColor);
                bg.addState(new int[]{android.R.attr.state_pressed}, gdBackgroundPress);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                view.setBackground(bg);
            } else {
                view.setBackgroundDrawable(bg);
            }
        }

        if (view instanceof TextView && textPressColor != Integer.MAX_VALUE) {
            ColorStateList colorStateList = new ColorStateList(
                    new int[][]{new int[]{-android.R.attr.state_pressed}, new int[]{android.R.attr.state_pressed}},
                    new int[]{
                            ((TextView) view).getTextColors().getDefaultColor(),
                            textPressColor
                    }
            );
            ((TextView) view).setTextColor(colorStateList);
        }
    }

    private void setDrawable(GradientDrawable gd, @ColorInt int color, @ColorInt int strokeColor) {
        gd.setColor(color);
        if (cornerRadiusTL > 0 || cornerRadiusTR > 0 || cornerRadiusBR > 0 || cornerRadiusBL > 0) {
            radiusArr[0] = (float) cornerRadiusTL;
            radiusArr[1] = (float) cornerRadiusTL;
            radiusArr[2] = (float) cornerRadiusTR;
            radiusArr[3] = (float) cornerRadiusTR;
            radiusArr[4] = (float) cornerRadiusBR;
            radiusArr[5] = (float) cornerRadiusBR;
            radiusArr[6] = (float) cornerRadiusBL;
            radiusArr[7] = (float) cornerRadiusBL;
            gd.setCornerRadii(radiusArr);
        } else {
            gd.setCornerRadius(cornerRadius);
        }
        gd.setStroke(strokeWidth, strokeColor);
    }

    private ColorStateList getPressedColorSelector(@ColorInt int normalColor, @ColorInt int pressedColor) {
        int[][] states = new int[][]{
                new int[]{android.R.attr.state_pressed},
                new int[]{android.R.attr.state_focused},
                new int[]{android.R.attr.state_activated},
                new int[]{}
        };
        int[] colors = new int[]{
                pressedColor,
                pressedColor,
                pressedColor,
                normalColor
        };
        return new ColorStateList(states, colors);
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
        setBgSelector();
    }

    public int getBackgroundPressColor() {
        return backgroundPressColor;
    }

    public void setBackgroundPressColor(int backgroundPressColor) {
        this.backgroundPressColor = backgroundPressColor;
        setBgSelector();
    }

    public int getStrokeColor() {
        return strokeColor;
    }

    public void setStrokeColor(int strokeColor) {
        this.strokeColor = strokeColor;
        setBgSelector();
    }

    public int getStrokePressColor() {
        return strokePressColor;
    }

    public void setStrokePressColor(int strokePressColor) {
        this.strokePressColor = strokePressColor;
        setBgSelector();
    }

    public int getTextPressColor() {
        return textPressColor;
    }

    public void setTextPressColor(int textPressColor) {
        this.textPressColor = textPressColor;
        setBgSelector();
    }

    public int getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
        setBgSelector();
    }

    public int getCornerRadius() {
        return cornerRadius;
    }

    public void setCornerRadius(int cornerRadius) {
        this.cornerRadius = cornerRadius;
        setBgSelector();
    }

    public int getCornerRadiusTL() {
        return cornerRadiusTL;
    }

    public void setCornerRadiusTL(int cornerRadiusTL) {
        this.cornerRadiusTL = cornerRadiusTL;
        setBgSelector();
    }

    public int getCornerRadiusTR() {
        return cornerRadiusTR;
    }

    public void setCornerRadiusTR(int cornerRadiusTR) {
        this.cornerRadiusTR = cornerRadiusTR;
        setBgSelector();
    }

    public int getCornerRadiusBL() {
        return cornerRadiusBL;
    }

    public void setCornerRadiusBL(int cornerRadiusBL) {
        this.cornerRadiusBL = cornerRadiusBL;
        setBgSelector();
    }

    public int getCornerRadiusBR() {
        return cornerRadiusBR;
    }

    public void setCornerRadiusBR(int cornerRadiusBR) {
        this.cornerRadiusBR = cornerRadiusBR;
        setBgSelector();
    }

    public boolean isRadiusHalfHeight() {
        return isRadiusHalfHeight;
    }

    public void setRadiusHalfHeight(boolean radiusHalfHeight) {
        isRadiusHalfHeight = radiusHalfHeight;
        view.invalidate();
        view.requestLayout();
    }

    public boolean isWidthHeightEqual() {
        return isWidthHeightEqual;
    }

    public void setWidthHeightEqual(boolean widthHeightEqual) {
        isWidthHeightEqual = widthHeightEqual;
        view.invalidate();
        view.requestLayout();
    }

    public boolean isRippleEnable() {
        return isRippleEnable;
    }

    public void setRippleEnable(boolean rippleEnable) {
        isRippleEnable = rippleEnable;
        setBgSelector();
    }

    public void setBackgroundColorRes(int backgroundColorRes) {
        if (backgroundColorRes == this.backgroundColorRes) {
            return;
        }
        this.backgroundColorRes = backgroundColorRes;
        if (backgroundColorRes != INVALID_ID) {
            setBackgroundColor(ContextCompat.getColor(context, backgroundColorRes));
        } else {
            setBackgroundColor(Color.TRANSPARENT);
        }
    }

    public void setBackgroundPressColorRes(int backgroundPressColorRes) {
        if (backgroundPressColorRes == this.backgroundPressColorRes) {
            return;
        }
        this.backgroundPressColorRes = backgroundPressColorRes;
        if (backgroundPressColorRes != INVALID_ID) {
            setBackgroundPressColor(ContextCompat.getColor(context, backgroundPressColorRes));
        } else {
            setBackgroundPressColor(Integer.MAX_VALUE);
        }
    }

    public void setStrokeColorRes(int strokeColorRes) {
        if (strokeColorRes == this.strokeColorRes) {
            return;
        }
        this.strokeColorRes = strokeColorRes;
        if (strokeColorRes != INVALID_ID) {
            setStrokeColor(ContextCompat.getColor(context, strokeColorRes));
        } else {
            setStrokeColor(Color.TRANSPARENT);
        }
    }

    public void setStrokePressColorRes(int strokePressColorRes) {
        if (strokePressColorRes == this.strokePressColorRes) {
            return;
        }
        this.strokePressColorRes = strokePressColorRes;
        if (strokePressColorRes != INVALID_ID) {
            setStrokePressColor(ContextCompat.getColor(context, strokePressColorRes));
        } else {
            setStrokePressColor(Integer.MAX_VALUE);
        }
    }

    public void setTextPressColorRes(int textPressColorRes) {
        if (textPressColorRes == this.textPressColorRes) {
            return;
        }
        this.textPressColorRes = textPressColorRes;
        if (textPressColorRes != INVALID_ID) {
            setTextPressColor(ContextCompat.getColor(context, textPressColorRes));
        } else {
            setTextPressColor(Integer.MAX_VALUE);
        }
    }

    public void setColorRes(int backgroundColorRes, int backgroundPressColorRes, int strokeColorRes, int strokePressColorRes, int textPressColorRes) {
        this.backgroundColorRes = backgroundColorRes;
        this.backgroundPressColorRes = backgroundPressColorRes;
        this.strokeColorRes = strokeColorRes;
        this.strokePressColorRes = strokePressColorRes;
        this.textPressColorRes = textPressColorRes;
        setupValue();
    }

    private void setupValue() {
        if (backgroundColorRes != INVALID_ID) {
            backgroundColor = ContextCompat.getColor(context, backgroundColorRes);
        } else {
            backgroundColor = Color.TRANSPARENT;
        }
        if (backgroundPressColorRes != INVALID_ID) {
            backgroundPressColor = ContextCompat.getColor(context, backgroundPressColorRes);
        } else {
            backgroundPressColor = Integer.MAX_VALUE;
        }
        if (strokeColorRes != INVALID_ID) {
            strokeColor = ContextCompat.getColor(context, strokeColorRes);
        } else {
            strokeColor = Color.TRANSPARENT;
        }
        if (strokePressColorRes != INVALID_ID) {
            strokePressColor = ContextCompat.getColor(context, strokePressColorRes);
        } else {
            strokePressColor = Integer.MAX_VALUE;
        }
        if (textPressColorRes != INVALID_ID) {
            textPressColor = ContextCompat.getColor(context, textPressColorRes);
        } else {
            textPressColor = Integer.MAX_VALUE;
        }
        setBgSelector();
    }
}
