AFIS -A Fantastic Invertible Shougi- マニュアル
===============================================

- バージョン       : 1.0.0
- アイデア考案者   : チームつぬちよ (代表者 : 藤田玄)
- 実装者           : 大内真一
- 作成日           : 2017/09/02
- 最終更新日       : 2018/07/17
- 連絡先           : 
- 実行ファイル名   : afis.jar
- 動作確認・開発環境
  - OS             : Ubuntu 18.04
  - プロセッサ     : Intel(R) Core(TM) i7-3667U CPU @ 2.00GHz
  - メモリ         : 8GB RAM
  - Java           : openjdk version "1.8.0_171"

使い方
------

### 通常のアプリの使い方
#### zipの展開
下記のフォルダ構成にします。(配布zip展開直後のフォルダ構成)
フォルダなども配置してください。

    .
    |-- README.txt
    |-- afis-1.0-SNAPSHOT.jar
    |-- config
    |   |-- koma.properties
    |   `-- play.properties
    `-- log
        `-- dummy.txt

#### アプリの起動
afis-1.0-SNAPSHOT.jarをダブルクリックしてください。  

### 設定値の変更
#### 駒の数
configフォルダ内のkoma.propertiesをメモ帳などで開いてください。

それぞれの設定値は、ゲーム開始時の駒の数になります。  
例えば、飛車のみにしたい場合は下記のような設定にしてください。  
***※半角数字以外は使用できません***

```properties
# Count of komas
fu=0
kin=0
gin=0
keima=0
kyosha=0
hisha=9999
kaku=0
ou=0
```

#### 対CPUモード
configフォルダ内のplay.propertiesをメモ帳などで開いてください。

`vsCPU`という設定値を`vsCPU=false`にすると設定をオフにできます。  
(先手、後手ともにプレイヤーが操作できるようになる)

プレイの結果
------------

logフォルダ内にCSVファイルが生成されています。  
Excelなどで開くことができるので、そちらをご確認ください。

アプリが動かないとき
--------------------

### Javaのバージョンを上げる
アプリの動作にはJavaがお使いのPCにインストールされている必要があります。  
下記のURLから最新のJavaをインストールしてから、再度アプリの実行を試してください。

https://java.com/ja/download/

<!-- vim: set ft=markdown: -->
