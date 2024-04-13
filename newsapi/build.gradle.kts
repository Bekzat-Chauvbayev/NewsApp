plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")

}



java {
    sourceCompatibility = JavaVersion.VERSION_19
    targetCompatibility = JavaVersion.VERSION_19
}

dependencies{
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")

    implementation ("androidx.annotation:annotation:1.7.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")
    configurations.all {
        resolutionStrategy {
            force ("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0")
            force ("com.github.skydoves:retrofit-adapters-result:1.0.9")
        }
    }

    implementation(platform("com.squareup.okhttp3:okhttp-bom:4.12.0"))
    implementation("com.squareup.okhttp3:okhttp")
    implementation("com.squareup.okhttp3:logging-interceptor")



    }
