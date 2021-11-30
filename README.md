# UIObfuscator: Impeding UI Analysis on Android Apps

## 简介
UIObfuscator 提供了九种UI混淆方法，将UIObfuscator 作用于安卓app之后，现有的自动化UI分析工具性能会减弱。

## 运行代码：

- 将文件夹导入eclipse
- 配置bulid path
  - 从https://github.com/soot-oss/soot下载soot.psf文件
  - 在eclipse中file=>import=>team=>team project
  - 在file中选择刚才下载的soot.psf
  - 点击finish

- 配置 src/hk/polyu/Configuration.java.文件

  - ```
    public static String apkPath =“ ”//单个apk文件路径，建议从F-Droid中下载
    ```

  - ```
    public static String apkDirectory = " "//新建存放所有原始apk数据集的目录
    ```

  - ```
    public static String apkOutputDirectory = "”//新建存放混淆后apk集合的目录
    ```

  - ```
    public static String platformPath =“”//本地android jdk platforms文件夹路径
    ```

    

- Run src/hk/polyu/MainImpl.java 文件  

## 各个文件夹实现的功能介绍：

#### MLF：在app的布局文件中添加不可见的view组件来修改视图层次结构。

ViewInjection.inject();实现了插入组件算法

#### SLF：将伪造的布局文件插入到 APK 中，而原始布局文件将从中提取 APK 并在运行时加载以恢复应用程序的视图层次结构。

#### IPA：通过拦截app活动之间的直接转换关系，注入额外的代理活动来修改app的ATG。

InjectProxy.inject();实现了注入代理活动算法

#### ESC：编码字符串常量，特别是那些表示app activity类名的字符串常量，为ATG构造器设置额外的障碍

/ESC/src/hk/polyu/repackage/StringManipulation.java中实现了对类名的编码算法

#### RFC：通过Java反射机制重写涉及活动转换相关API的函数调用，阻碍ATG的构建过程。

#### PAM：为了隐藏与构建ATG相关的方法调用，首先从APK中提取包含活动转换相关API的app方法，然后在运行时加载并执行以完成原始操作。

#### UVH：不是直接修改布局文件，而是通过字节码创建和插入视图组件，以动态更新应用程序的视图层次结构。

#### MOW：当一个app活动要渲染到设备屏幕上时，会启动一个覆盖窗口来获取窗口焦点，防止app的运行时视图层次结构被UIAutomator捕获。

manifestParser.exec();实现了分析manifest文件

apkParser.exec();实现了分析apk文件并添加覆盖

#### PAS：在app的每个活动中启用FLAG_SECURE属性，让窗口的内容不能出现在截图中

WindowFlag.generate();实现了对该属性的操作
