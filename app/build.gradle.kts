plugins {
    kotlin("android")
    kotlin("android.extensions")
    alias(libs.plugins.androidApplication)
    id("com.google.gms.google-services")
    alias(libs.plugins.googleAndroidLibrariesMapsplatformSecretsGradlePlugin)
}

android {

    namespace = "com.example.connect"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.connect"
        minSdk = 23
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        /*debug {
            buildConfigField "String", "MY_API_KEY", "\"your_debug_api_key\""
        }*/
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

// Use the secrets plugin to load the MAPS_API_KEY
secrets {
    propertiesFileName = "secrets.properties"
    defaultPropertiesFileName = "local.defaults.properties"
    ignoreList.add("keyToIgnore")
    ignoreList.add("sdk.*")
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.navigation.fragment)

    implementation("com.google.android.gms:play-services-maps:18.2.0")
    //implementation ("com.google.android.gms:play-services-places:18.0.0")
    implementation ("com.google.android.libraries.places:places:3.4.0")

    implementation("com.google.maps.android:maps-ktx:5.0.0")
    implementation(libs.navigation.ui)
    implementation(libs.activity)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.database)
    implementation(libs.play.services.location)
    implementation(libs.places)
    implementation(libs.firebase.storage)
    // Import the Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:33.0.0"))


    // TODO: Add the dependencies for Firebase products you want to use
    // When using the BoM, don't specify versions in Firebase dependencies
    implementation("com.google.firebase:firebase-analytics")

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
