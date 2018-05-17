# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\tool\androidtool\android-sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# 代码混淆压缩比，在0~7之间，默认为5，一般不做修改
-optimizationpasses 5

# 混合时不使用大小写混合，混合后的类名为小写
-dontusemixedcaseclassnames

# 保留以下包内的所有类不被混淆
-keep class store.chinaotec.com.medicalcare.javabean.**{*;}
-keep class store.chinaotec.com.medicalcare.utill.**{*;}
-keep class store.chinaotec.com.medicalcare.dialog.**{*;}
-keep class store.chinaotec.com.medicalcare.receiver.**{*;}
-keep class store.chinaotec.com.medicalcare.view.**{*;}
-keep class store.chinaotec.com.medicalcare.listener.**{*;}
-keep class store.chinaotec.com.medicalcare.localalbum.**{*;}

#ucrop图片裁剪库的混淆
-dontwarn com.yalantis.ucrop**
-keep class com.yalantis.ucrop** { *; }
-keep interface com.yalantis.ucrop** { *; }
#打包出错
-keepattributes EnclosingMethod
#科大讯飞混淆
-keep class com.iflytek.**{*;}
-keepattributes Signature