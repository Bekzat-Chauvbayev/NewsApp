// Top-level build file where you can add configuration options common to all sub-projects/modules.
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    alias(libs.plugins.jetbrainsKotlinJvm) apply false
    alias(libs.plugins.kotlinSerialization) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.dagger.hilt.android) apply false
    alias(libs.plugins.kapt) apply false
    alias(libs.plugins.detect) apply false


}

allprojects.onEach { project ->
    project.afterEvaluate {
        with(project.plugins) {
            if ((hasPlugin(libs.plugins.jetbrainsKotlinAndroid.get().pluginId))
                || hasPlugin(libs.plugins.jetbrainsKotlinJvm.get().pluginId)
            ) {
                apply(libs.plugins.detect.get().pluginId)

                project.extensions.configure<DetektExtension> {
                    config.setFrom(rootProject.files("default-detect-config.yml"))
                }
                project.dependencies.add("detektPlugins", libs.detekt.formatting.get().toString())
            }
        }
    }
}