/*
 * Copyright (C) 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.android.setupdesign.util;

import android.content.Context;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.setupcompat.partnerconfig.PartnerConfig;
import com.google.android.setupcompat.partnerconfig.PartnerConfigHelper;

/** Helper class to apply partner configurations to a textView. */
final class TextViewPartnerStyler {

  /** Applies given partner configurations {@code textPartnerConfigs} to the {@code textView}. */
  public static void applyPartnerCustomizationStyle(
      @NonNull TextView textView, @NonNull TextPartnerConfigs textPartnerConfigs) {

    if (textView == null || textPartnerConfigs == null) {
      return;
    }

    Context context = textView.getContext();
    if (textPartnerConfigs.getTextColorConfig() != null) {
      int textColor =
          PartnerConfigHelper.get(context)
              .getColor(context, textPartnerConfigs.getTextColorConfig());
      if (textColor != 0) {
        textView.setTextColor(textColor);
      }
    }

    if (textPartnerConfigs.getTextLinkedColorConfig() != null) {
      int linkTextColor =
          PartnerConfigHelper.get(context)
              .getColor(context, textPartnerConfigs.getTextLinkedColorConfig());
      if (linkTextColor != 0) {
        textView.setLinkTextColor(linkTextColor);
      }
    }

    if (textPartnerConfigs.getTextSizeConfig() != null) {
      float textSize =
          PartnerConfigHelper.get(context)
              .getDimension(context, textPartnerConfigs.getTextSizeConfig(), 0);
      if (textSize > 0) {
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
      }
    }

    if (textPartnerConfigs.getTextFontFamilyConfig() != null) {
      String fontFamilyName =
          PartnerConfigHelper.get(context)
              .getString(context, textPartnerConfigs.getTextFontFamilyConfig());
      Typeface font = Typeface.create(fontFamilyName, Typeface.NORMAL);
      if (font != null) {
        textView.setTypeface(font);
      }
    }

    if (PartnerConfigHelper.shouldApplyExtendedPartnerConfig(context)) {
      if (textPartnerConfigs.getTextMarginTop() != null
          || textPartnerConfigs.getTextMarginBottom() != null) {
        int topMargin;
        int bottomMargin;
        final ViewGroup.LayoutParams lp = textView.getLayoutParams();
        if (lp instanceof LinearLayout.LayoutParams) {
          final LinearLayout.LayoutParams mlp = (LinearLayout.LayoutParams) lp;
          if (textPartnerConfigs.getTextMarginTop() != null) {
            topMargin =
                (int)
                    PartnerConfigHelper.get(context)
                        .getDimension(context, textPartnerConfigs.getTextMarginTop());
          } else {
            topMargin = mlp.topMargin;
          }

          if (textPartnerConfigs.getTextMarginBottom() != null) {
            bottomMargin =
                (int)
                    PartnerConfigHelper.get(context)
                        .getDimension(context, textPartnerConfigs.getTextMarginBottom());
          } else {
            bottomMargin = mlp.bottomMargin;
          }
          mlp.setMargins(mlp.leftMargin, topMargin, mlp.rightMargin, bottomMargin);
          textView.setLayoutParams(lp);
        }
      }
    }
    textView.setGravity(textPartnerConfigs.getTextGravity());
  }

  /**
   * Applies given partner configurations {@code textPartnerConfigs} to the {@code textView}.
   *
   * @param textView A text view would apply the gravity
   * @param textPartnerConfigs A partner conflagrations contains text gravity would be set
   */
  public static void applyPartnerCustomizationLightStyle(
      @NonNull TextView textView, @NonNull TextPartnerConfigs textPartnerConfigs) {

    if (textView == null || textPartnerConfigs == null) {
      return;
    }

    textView.setGravity(textPartnerConfigs.getTextGravity());
  }

  /** Keeps the partner conflagrations for a textView. */
  public static class TextPartnerConfigs {
    private final PartnerConfig textColorConfig;
    private final PartnerConfig textLinkedColorConfig;
    private final PartnerConfig textSizeConfig;
    private final PartnerConfig textFontFamilyConfig;
    private final PartnerConfig textMarginTopConfig;
    private final PartnerConfig textMarginBottomConfig;
    private final int textGravity;

    public TextPartnerConfigs(
        @Nullable PartnerConfig textColorConfig,
        @Nullable PartnerConfig textLinkedColorConfig,
        @Nullable PartnerConfig textSizeConfig,
        @Nullable PartnerConfig textFontFamilyConfig,
        @Nullable PartnerConfig textMarginTopConfig,
        @Nullable PartnerConfig textMarginBottomConfig,
        int textGravity) {
      this.textColorConfig = textColorConfig;
      this.textLinkedColorConfig = textLinkedColorConfig;
      this.textSizeConfig = textSizeConfig;
      this.textFontFamilyConfig = textFontFamilyConfig;
      this.textMarginTopConfig = textMarginTopConfig;
      this.textMarginBottomConfig = textMarginBottomConfig;
      this.textGravity = textGravity;
    }

    public PartnerConfig getTextColorConfig() {
      return textColorConfig;
    }

    public PartnerConfig getTextLinkedColorConfig() {
      return textLinkedColorConfig;
    }

    public PartnerConfig getTextSizeConfig() {
      return textSizeConfig;
    }

    public PartnerConfig getTextFontFamilyConfig() {
      return textFontFamilyConfig;
    }

    public PartnerConfig getTextMarginTop() {
      return textMarginTopConfig;
    }

    public PartnerConfig getTextMarginBottom() {
      return textMarginBottomConfig;
    }

    public int getTextGravity() {
      return textGravity;
    }
  }

  private TextViewPartnerStyler() {}
}
