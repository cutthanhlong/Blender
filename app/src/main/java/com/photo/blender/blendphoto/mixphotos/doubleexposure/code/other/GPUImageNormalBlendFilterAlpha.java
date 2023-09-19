package com.photo.blender.blendphoto.mixphotos.doubleexposure.code.other;

import jp.co.cyberagent.android.gpuimage.GPUImageTwoInputFilter;

public class GPUImageNormalBlendFilterAlpha extends GPUImageTwoInputFilter {
    public static final String NORMAL_BLEND_FRAGMENT_SHADER = "varying highp vec2 textureCoordinate;\n varying highp vec2 textureCoordinate2;\n \n uniform sampler2D inputImageTexture;\n uniform sampler2D inputImageTexture2;\n \n void main()\n {\n     lowp vec4 c2 = texture2D(inputImageTexture, textureCoordinate);\n\t lowp vec4 c1 = texture2D(inputImageTexture2, textureCoordinate2);\n     \n     lowp vec4 outputColor;\n     \n     outputColor.r = 0.4 * c1.r + c2.r * c2.a * 0.6;\n\n     outputColor.g = 0.4 * c1.g + c2.g * c2.a * 0.6;\n     \n     outputColor.b = 0.4 * c1.b + c2.b * c2.a * 0.6;\n     \n     outputColor.a = 0.4 * c1.a + c2.a * 0.6;\n     \n     gl_FragColor = outputColor;\n }";

    public GPUImageNormalBlendFilterAlpha() {
        super(NORMAL_BLEND_FRAGMENT_SHADER);
    }
}
