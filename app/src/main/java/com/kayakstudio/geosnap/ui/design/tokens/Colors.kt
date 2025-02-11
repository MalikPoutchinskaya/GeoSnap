package com.kayakstudio.geosnap.ui.design.tokens

import androidx.compose.ui.graphics.Color

object Colors {

    //
    // Palettes
    // -----------------------------------------------------

    val neutral50 = Color(0xfffefefe)
    val neutral100 = Color(0xffe7e7e7)
    val neutral200 = Color(0xffb4b4b4)
    val neutral300 = Color(0xff8c8c8c)
    val neutral400 = Color(0xff6b6b6b)
    val neutral500 = Color(0xff2c2c2c)

    val teal900 = Color(0xff082127)
    val teal600 = Color(0xff104755)
    val teal400 = Color(0xff41717d)
    val teal200 = Color(0xff92aeb4)
    val teal50 = Color(0xffe7edef)

    val red900 = Color(0xffBF2D00)
    val red600 = Color(0xffF54100)
    val red400 = Color(0xffFF6634)
    val red200 = Color(0xffFFA383)
    val red50 = Color(0xffFFF0EB)

    val green900 = Color(0xff002d1f)
    val green600 = Color(0xff006243)
    val green400 = Color(0xff33896e)
    val green200 = Color(0xff8abbac)
    val green50 = Color(0xffEBFFF9)

    val purple900 = Color(0xff27224b)
    val purple600 = Color(0xff5449a2)
    val purple500 = Color(0xff5c50b2)
    val purple400 = Color(0xff7d73c1)
    val purple200 = Color(0xffb4afdc)
    val purple50 = Color(0xffefeef7)

    val mustard900 = Color(0xff6b4f24)
    val mustard600 = Color(0xffe7ac4d)
    val mustard500 = Color(0xfffebd55)
    val mustard400 = Color(0xfffeca77)
    val mustard200 = Color(0xffffe1b1)
    val mustard50 = Color(0xfffff8ee)

    //
    // Roles
    // -----------------------------------------------------

    val orange = mustard500
    val orangeLight = mustard200
    val orangeUltraLight = mustard50
    val secondaryColor = purple500
    val secondaryLightColor = purple200
    val errorText = red900
    val errorBackground = red200
    val errorBackgroundLight = red50
    val successText = green900
    val successBackground = green200
    val warningText = mustard900
    val warningBackground = mustard200
    val infoText = purple900
    val infoBackground = purple200
    val tooltipBackground = purple50

    fun bgBrushedColors() = listOf(
        orangeLight,
        secondaryLightColor.copy(alpha = 0.5f),
    )

    fun textBrushedColors() = listOf(
        orange,
        orangeLight.copy(alpha = 0.5f),
    )

    fun getBlueShimmerColors(): List<Color> {
        return listOf(
            purple900,
            purple600,
            purple400,
            purple200,
            purple400,
            purple600,
            purple900,
        )
    }

    fun getOrangeShimmerColors(): List<Color> {
        return listOf(
            mustard900,
            mustard600,
            mustard400,
            mustard200,
            mustard400,
            mustard600,
            mustard900,
        )
    }

    fun getGreenShimmerColors(): List<Color> {
        return listOf(
            green900,
            green600,
            green400,
            green200,
            green400,
            green600,
            green900,
        )
    }

    fun getRedShimmerColors(): List<Color> {
        return listOf(
            red900,
            red600,
            red400,
            red200,
            red400,
            red600,
            red900,
        )
    }
}
