# アプリ概要

OpenWeatherMap APIを使ったサンプルアプリです<br>
このアプリを使って開発する場合は、以下のキャプチャの通り、local.propertiesを設定追加してください<br>
<img width="1109" alt="スクリーンショット 2021-08-01 20 29 55" src="https://user-images.githubusercontent.com/16476224/127769264-c2a37897-f2c0-4ef8-b720-f15499ca1002.png">


# 開発環境
<img width="686" alt="127751156-0638bd97-e532-43e9-be12-e758a7118141" src="https://user-images.githubusercontent.com/16476224/127752570-e46e0931-4d36-43c9-9441-903011660580.png">

# capture (Pixcel 5 OS:11)

<img src="https://user-images.githubusercontent.com/16476224/127769176-89c53820-b538-490d-93ce-11e9cb81b039.gif" width=320 />

# 実装上の課題
Web APIのレスポンス値の数が多いため、型が間違っている場合があリます。<br>
Logcatを見ていくつか直しましたが、Doubleで来る可能性のあるデータを直しきれていない可能性があります。<br>
以下、レスポンスを受けるデータクラスです<br>
https://github.com/LeoAndo/OpenWeatherMapKotlinSample/tree/main/app/src/main/java/com/example/openweathermapkotlinsample/data/response

# 実装手順

以下の手順通り、実装を進めてください<br>
[pull request](https://github.com/LeoAndo/OpenWeatherMapKotlinSample/pulls?q=is%3Apr+is%3Aclosed)

# For java

Java版のコードはそのうちアップ予定です。
