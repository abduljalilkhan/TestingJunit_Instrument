plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.testing.UI_Unit"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.testing.UI_Unit"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    testImplementation 'junit:junit:4.13.2'

    // Unit test dependencies
    testImplementation 'org.mockito:mockito-core:4.0.0' // For general mocking
    testImplementation 'org.mockito:mockito-inline:4.0.0' // For mocking static methods
    testImplementation "org.mockito.kotlin:mockito-kotlin:4.0.0" //For Kotlin-specific mocking

    testImplementation 'org.robolectric:robolectric:4.8.1' // Check for the latest version

    testImplementation 'androidx.arch.core:core-testing:2.1.0'
    testImplementation 'org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.0'

    ///android test dependency
    androidTestImplementation 'androidx.test.ext:junit:1.2.1'

    androidTestImplementation 'androidx.test.espresso:espresso-core:3.6.1'
    androidTestImplementation 'androidx.test.espresso:espresso-contrib:3.6.1' // For RecyclerView support

    // Android instrumented test dependencies
    androidTestImplementation 'org.mockito:mockito-android:4.0.0'// For instrumented tests
    androidTestImplementation "org.mockito.kotlin:mockito-kotlin:4.0.0"

    androidTestImplementation 'androidx.fragment:fragment-testing:1.8.5' // Use the latest
    androidTestImplementation 'androidx.test:runner:1.6.2' // Test runner
    androidTestImplementation 'androidx.test:rules:1.6.1'

    //ui automator test
    androidTestImplementation 'androidx.test.uiautomator:uiautomator:2.2.0'
}