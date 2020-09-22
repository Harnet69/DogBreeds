# DogBreeds app (Kotlin, AndroidX & JetPack)

## Goals:
- teach user to recognize different breeds of dogs in game manner 

## Technologies:
- AndroidX (Google repository extension library. Includes JetPack too. All new projects after SDK28 must be on this)
- MVVM architecture pattern (ViewModel and LiveData library)
- fragments as UI screens
- SwipeRefreshLayout for pool(drag down)&refresh a RecyckerView 
- navigation library to switch between fragments
- Retrofit library & OWN REALIZATION of retrieving data from API
- RxJava generate objects for RecyclerView items from this data
- Glide library & OWN REALIZATION for loading and handling app images
- Room database for storing data, which was got from backend API, and when the screen will be updates - data will get from the database
- Palette Library for managing backgrond of details page
- dataBinding library to map information from backend to interfaces(screen layouts: detail screen will be binding to item of recycler view)
- Android permissions for SMS message
- Share preferences for sharing app's data between another apps
- Notifications when the data was got from backend API
- Multidex for fixing errors
