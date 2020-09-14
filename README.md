# DogBreeds app (Kotlin, AndroidX & JetPack)

## Goals:
- teach user to recognize different breeds of dogs in game manner 

## Technologies:
- AndroidX (Google repository extension library. Includes JetPack too. All new projects affter SDK28 must be on this )
- MVVM architecture pattern (ViewModel and LiveData library)
- navigation library to switch between fragments
- dataBinding library to map information from backend to interfaces(screen layouts: detail screen will be binding to item of recycler view)
- fragments as UI screens
- retrofit library retrieve data from API
- rxJava generate objects for RecyclerView items from this data
- Room database for storing data, which was got from backend API, and when the screen will be updates - data will get from the database
- Palette Library for managing backgrond of details page
- Android permissions for SMS message
- Share preferences for sharring app's data between another apps
- Notifications when the data was got from backend API
- Multidex for fixing errors

## Features:
- pool(drag down) to refresh an RecyckerView 
