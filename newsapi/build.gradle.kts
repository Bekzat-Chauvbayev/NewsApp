plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")

}



java {
    sourceCompatibility = JavaVersion.VERSION_1_7
    targetCompatibility = JavaVersion.VERSION_1_7
}

dependencies{
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")

    implementation ("androidx.annotation:annotation:1.7.1")
    implementation ("com.github.skydoves:retrofit-adapters-result:1.0.9")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0")




    }
