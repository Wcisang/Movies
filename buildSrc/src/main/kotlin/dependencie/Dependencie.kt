package dependencie

enum class Dependencie(val route: String) {
    KOTLIN_STD("org.jetbrains.kotlin:kotlin-stdlib:${Version.KOTLIN}"),
    CORE_KTX("androidx.core:core-ktx:${Version.CORE_KTX}"),
    APPCOMPAT("androidx.appcompat:appcompat:${Version.APP_COMPAT}"),
    CONSTRAINT_LAYOUT("androidx.constraintlayout:constraintlayout:${Version.CONSTRAINT_LAYOUT}"),
    LIVEDATA("androidx.lifecycle:lifecycle-livedata-ktx:${Version.LIFECYCLE}"),
    VIEWMODEL("androidx.lifecycle:lifecycle-viewmodel-ktx:${Version.LIFECYCLE}"),
    VIEWMODEL_SAVED_STATE("androidx.lifecycle:lifecycle-viewmodel-savedstate:${Version.LIFECYCLE}"),
    HILT_VIEWMODEL("androidx.hilt:hilt-lifecycle-viewmodel:${Version.HILT}"),
    //HILT_COMMON("androidx.hilt:hilt-common:${Version.HILT}"),
    HILT_ANDROID("com.google.dagger:hilt-android:${Version.HILT_ANDROID}"),
    COROUTINES("org.jetbrains.kotlinx:kotlinx-coroutines-android:${Version.COROUTINES}"),
    PAGING("androidx.paging:paging-runtime:${Version.PAGING}"),
    FRAGMENT_KTX("androidx.fragment:fragment-ktx:${Version.FRAGMENT_KTX}"),
    RETROFIT("com.squareup.retrofit2:retrofit:${Version.RETROFIT}"),
    GSON_CONVERTER("com.squareup.retrofit2:converter-gson:${Version.RETROFIT}"),
    COIL("io.coil-kt:coil:${Version.COIL}"),
    ROOM("androidx.room:room-runtime:${Version.ROOM}"),
    ROOM_KTX("androidx.room:room-ktx:${Version.ROOM}"),
    APP_STARTUP("androidx.startup:startup-runtime:${Version.APP_STARTUP}")
}

enum class KaptDependencie(val route : String) {
    HILT("androidx.hilt:hilt-compiler:${Version.HILT}"),
    HILT_ANDROID("com.google.dagger:hilt-android-compiler:${Version.HILT_ANDROID}"),
    ROOM("androidx.room:room-compiler:${Version.ROOM}")
}

enum class TestDependencie(val route: String) {
    JUNIT("junit:junit:${Version.JUNIT}")
}

enum class AndroidTestDependencie(val route: String) {
    JUNIT("androidx.test.ext:junit:${Version.JUNIT_EXT}"),
    ESPRESSO("androidx.test.espresso:espresso-core:${Version.ESPRESSO}")
}