[日本語版README](https://github.com/LeoAndo/OpenWeatherMapKotlinSample/blob/main/readme/README_JP.md)

# Overview

This is a OpenWeatherMap API Sample Application.<br>
When developing using this app, please add the following settings to `local.properties` as shown in the capture below.<br>
**Because API KEY is managed as confidential information.**<br>
<img width="1109" alt="スクリーンショット 2021-08-01 20 29 55" src="https://user-images.githubusercontent.com/16476224/127769264-c2a37897-f2c0-4ef8-b720-f15499ca1002.png">


# development environment
<img width="686" alt="127751156-0638bd97-e532-43e9-be12-e758a7118141" src="https://user-images.githubusercontent.com/16476224/127752570-e46e0931-4d36-43c9-9441-903011660580.png">

# capture (Pixcel 5 OS:12)

<img src="https://user-images.githubusercontent.com/16476224/149372012-2f5c832f-a587-40e9-9b47-85f5328f10b2.gif" width=320 />

# problem of implementation 
Due to the large number of Web API response values, the type may be incorrect.<br>
I looked at Logcat and fixed some things, but it is possible that I haven't fixed all the data that may come with Double.<br>
[PR](https://github.com/LeoAndo/OpenWeatherMapKotlinSample/pull/7#issue-700855530)Also commented, using Andorid Studio's "JsonToKotlinClass" plugin
I'm just using the `data class` that was` generated`.<br>
Below is the data class that receives the response<br>
https://github.com/LeoAndo/OpenWeatherMapKotlinSample/tree/main/app/src/main/java/com/example/openweathermapkotlinsample/data/response

# implementation flow

Please proceed with the implementation according to the following procedure<br>
[pull request](https://github.com/LeoAndo/OpenWeatherMapKotlinSample/pulls?q=is%3Apr+is%3Aclosed)

# For java

The Java version of the code will be uploaded soon.
