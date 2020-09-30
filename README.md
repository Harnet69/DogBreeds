# DogBreeds app [![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=Harnet69_DogBreeds&metric=alert_status)](https://sonarcloud.io/dashboard?id=Harnet69_DogBreeds)
Android application for introducing with dog breeds and showing the breed details 

## Application screenshots:
![Game process](https://github.com/Harnet69/DogBreeds/blob/master/app/GitHubMediaFile/dog_breeds.gif)

## Application installation:
- download app from [Google Play](https://play.google.com/store/apps/details?id=com.harnet.dogbreeds) or scan this QR code by your Android phone
![Game process](https://github.com/Harnet69/Location/blob/master/app/GitHubMediaFile/DogBreedsQR.png)
- download .apk [Dog breeds v.1.0 installer](https://drive.google.com/file/d/13ZXZO4C31WNDmkWBKCjSDuo11xITwVuz/view?usp=sharing) and run it on Android phone
- clone a project code from this repo to your computer, and run it via Android studio or another Android emulator

## Application pdf presentation: 
[Dog breeds presentation](https://drive.google.com/file/d/1L-hkecVw8j1RB5upHoxr3FdjqQYzyjHS/view?usp=sharing)

## Technologies:
### Implemented:
- AndroidX (Google repository extension library. Includes JetPack too. All new projects after SDK28 must be on this)
- MVVM architecture pattern (ViewModel and LiveData library)
- fragments as UI screens
- SwipeRefreshLayout for pool(drag down)&refresh a RecyckerView 
- navigation library to switch between fragments
- Retrofit library & OWN REALIZATION of retrieving data from API
- RxJava generate objects for RecyclerView items from this data
- Glide library & OWN REALIZATION for loading and handling app images
### In progress:
- Room database for storing data, which was got from backend API, and when the screen will be updates - data will get from the database
- Palette Library for managing backgrond of details page
- dataBinding library to map information from backend to interfaces(screen layouts: detail screen will be binding to item of recycler view)
- Android permissions for SMS message
- Share preferences for sharing app's data between another apps
- Notifications when the data was got from backend API
- Multidex for fixing errors
- SonarCloud for monitoring a code quality
- Fastlane as Android Build and Release Process automation
