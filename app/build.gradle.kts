plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.anonymousgradingapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.anonymousgradingapp"
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
        isCoreLibraryDesugaringEnabled = true

        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.activity:activity:1.8.2")

    // added dependencies (by user)
    implementation("com.amplifyframework:core:1.4.0")
    implementation("com.amplifyframework:aws-auth-cognito:1.4.0")
    implementation("com.amplifyframework:aws-api:2.14.11")
    implementation("com.amazonaws:aws-android-sdk-cognitoidentityprovider:2.6.0")
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.0.10")

    implementation("com.google.mlkit:barcode-scanning:17.2.0")
    implementation("com.journeyapps:zxing-android-embedded:4.1.0")
    implementation("com.google.zxing:core:3.4.0")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}