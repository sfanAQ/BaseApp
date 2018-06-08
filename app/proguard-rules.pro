# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

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

#混淆 ProGuard常用语法
#-libraryjars class_path 应用的依赖包，如android-support-v4
#-keep [,modifier,...] class_specification 不混淆某些类
#-keepclassmembers [,modifier,...] class_specification 不混淆类的成员
#-keepclasseswithmembers [,modifier,...] class_specification 不混淆类及其成员
#-keepnames class_specification 不混淆类及其成员名
#-keepclassmembernames class_specification 不混淆类的成员名
#-keepclasseswithmembernames class_specification 不混淆类及其成员名
#-assumenosideeffects class_specification 假设调用不产生任何影响，在proguard代码优化时会将该调用remove掉。如system.out.println和Log.v等等
#-dontwarn [class_filter] 不提示warnning

#Android 混淆原则:
#反射用到的类不混淆
#JNI方法不混淆
#AndroidMainfest中的类不混淆，四大组件和Application的子类和Framework层下所有的类默认不会进行混淆
#Parcelable的子类和Creator静态成员变量不混淆，否则会产生android.os.BadParcelableException异常
#使用GSON、fastjson等框架时，所写的JSON对象类不混淆，否则无法将JSON解析成对应的对象
#使用第三方开源库或者引用其他第三方的SDK包时，需要在混淆文件中加入对应的混淆规则
#有用到WEBView的JS调用也需要保证写的接口方法不混淆
#####################################################################################
#指定代码的压缩级别
-optimizationpasses 5

#包名不混合大小写
-dontusemixedcaseclassnames

#不去忽略非公共的库类
-dontskipnonpubliclibraryclasses

#优化/不优化输入的类文件
-dontoptimize

#混淆时是否做预校验
-dontpreverify

#混淆时是否记录日志
-verbose

#混淆时所采用的算法
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

##忽略警告
#-ignorewarning
#忽略所有警告
-ignorewarnings

#保护注解
-keepattributes *Annotation*

#保护反射的正常调用 如果混淆报错建议关掉
-keepattributes Signature
-keepattributes EnclosingMethod

#抛出异常时保留代码行号
-keepattributes SourceFile,LineNumberTable
#重命名抛出异常时的文件名称
-renamesourcefileattribute SourceFile

#保持 native 方法不被混淆
-keepclasseswithmembernames class * {
    native <methods>;
}

#不混淆哪些类
-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService

#保持自定义控件类不被混淆
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

#不混淆所有View的子类及其子类的get、set方法
-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
    public *** get*();
}

#不混淆Activity中参数类型为View的所有方法
-keepclassmembers class * extends android.app.Activity {
    public void *(android.view.View);
}

#不混淆Parcelable和它的子类，还有Creator成员变量
-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

#保持Serializable不被混淆
-keepnames class * implements java.io.Serializable

#不混淆Serializable接口的子类中指定的某些成员变量和方法
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    !private <fields>;
    !private <methods>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

#保持枚举 enum 类不被混淆 如果混淆报错，建议直接使用上面的 -keepclassmembers class * implements java.io.Serializable即可
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

#不混淆R类里及其所有内部static类中的所有static变量字段
-keepclassmembers class **.R$* {
    public static <fields>;
}

#关闭所有日志 log, java.io.Print, printStackTrace
-assumenosideeffects class android.util.Log {
    public static *** e(...);
    public static *** w(...);
    public static *** i(...);
    public static *** d(...);
    public static *** v(...);
}
#关闭打印日志
-assumenosideeffects class java.io.PrintStream {
    public *** print(...);
    public *** println(...);
}
#关闭异常日志
-assumenosideeffects class java.lang.Throwable {
    public *** printStackTrace(...);
}

#如果有用到WebView的JS调用接口，需加入如下规则
-keepclassmembers class fqcn.of.javascript.interface.for.webview {
    public *;
}

#如果引用了v4或者v7包
-dontwarn android.support.**
