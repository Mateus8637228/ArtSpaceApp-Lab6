package com.example.artspaceapp

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.graphics.Bitmap
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Rule
import org.junit.Test
import java.io.File
import java.io.FileInputStream

class ArtSpaceScenarioTest {
    @get:Rule val rule = createAndroidComposeRule<MainActivity>()

    @Test fun navigationAndRotation_keepSelectedArtwork() {
        rule.activityRule.scenario.onActivity { it.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT }
        rule.waitUntil(10000) { rule.activity.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT }
        rule.onNodeWithText("Previous").assertIsNotEnabled()
        rule.onNodeWithText("Next").performClick()
        rule.onNodeWithText("Падение мятежных ангелов").assertExists()
        screenshot("artspace-portrait")
        val oldActivity = rule.activity
        rule.activityRule.scenario.onActivity { it.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE }
        rule.waitUntil(10000) { rule.activity !== oldActivity && rule.activity.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE }
        rule.onNodeWithText("Падение мятежных ангелов").assertExists()
        screenshot("artspace-landscape")
        rule.onNodeWithText("Next").performClick()
        rule.onNodeWithText("Рождение Венеры").assertExists()
        rule.onNodeWithText("Next").assertIsNotEnabled()
        rule.onNodeWithText("Previous").performClick()
        rule.onNodeWithText("Previous").performClick()
        rule.onNodeWithText("Тайная вечеря").assertExists()
        rule.activityRule.scenario.recreate()
        rule.onNodeWithText("Тайная вечеря").assertExists()
    }

    private fun screenshot(name: String) {
        rule.waitForIdle()
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        instrumentation.uiAutomation.waitForIdle(1000, 10000)
        val file = File(instrumentation.targetContext.getExternalFilesDir("evidence"), "$name.png")
        val bitmap = instrumentation.uiAutomation.takeScreenshot()
        file.outputStream().use { bitmap.compress(Bitmap.CompressFormat.PNG, 100, it) }
        bitmap.recycle()
        // Preserve the real device capture when Gradle uninstalls the test application.
        instrumentation.uiAutomation.executeShellCommand("cp ${file.absolutePath} /data/local/tmp/lab6-$name.png").use { descriptor ->
            FileInputStream(descriptor.fileDescriptor).use { it.readBytes() }
        }
    }
}
