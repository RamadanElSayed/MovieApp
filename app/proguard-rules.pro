-keepattributes RuntimeVisibleAnnotations,AnnotationDefault,InnerClasses,Signature,EnclosingMethod
-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable

-keep,includedescriptorclasses class com.app.movieapp.**$$serializer { *; }
-keepclassmembers class com.app.movieapp.** {
    *** Companion;
}
-keepclasseswithmembers class com.app.movieapp.** {
    kotlinx.serialization.KSerializer serializer(...);
}

-keep class * extends androidx.work.ListenableWorker {
    <init>(...);
}

-dontwarn org.slf4j.**
-dontwarn java.lang.management.**
