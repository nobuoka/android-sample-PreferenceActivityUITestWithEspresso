android-sample-PreferenceActivityUITestWithEspresso
============================================================

このプロジェクトは、Android アプリ開発における `PreferenceActivity` の自動 UI テストのサンプルコードです。
自動 UI テストには、Espresso というライブラリを使用しています。

* [Espresso](http://code.google.com/p/android-test-kit/wiki/Espresso)

## ビルド

ビルドシステムには Gradle を使用しています。

* [Gradle](http://www.gradle.org/)
* [Android Gradle プラグイン](http://tools.android.com/tech-docs/new-build-system)

### 準備するもの

* [Java SE Development Kit (JDK)](http://www.oracle.com/technetwork/java/javase/downloads/index.html) 6 または 7
* [Android SDK](http://developer.android.com/sdk/index.html) with Build Tools 19.0.1

Gradle ラッパーが本プロジェクトに含まれていますので、Gradle を用意する必要はありません。

### テスト実行方法

コマンドライン上で実行する方法を説明します。

まず、Android SDK のパスを指定する必要があります。
環境変数 `ANDROID_HOME` に Android SDK のディレクトリパスを設定するか、もしくは
local.properties ファイルに `sdk.dir=/path/to/sdk` 形式で記述します。
ちなみに、Android Studio で本プロジェクトを開くと、自動的に local.properties
ファイルが作成され、`sdk.dir` が設定されるはずです。

次に、本プロジェクトのディレクトリで次のコマンドを実行します。

```
./gradlew connectedInstrumentTest
```

上記コマンドにより、アプリとテストのビルドが行われ、接続されている端末上でテストが実行されます。
