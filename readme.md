# Android Clean Architecture Components Boilerplate

Note: This is a fork of our original [Clean Architecture Boilerplate](https://github.com/bufferapp/android-clean-architecture-boilerplate), except in this repo we have switched out the MVP approach found in the presentation layer to now use ViewModels from the Android Architecture Components Library and Koin.
The caching layer now also uses Room.

Welcome 👋 We hope this boilerplate is not only helpful to other developers, but also that it helps to educate in the area of architecture. We created this boilerplate for a few reasons:

1. To experiment with modularisation
2. To experiment with the Android Architecture Components
3. To share some approaches to clean architecture, especially as we've been [talking a lot about it](https://academy.realm.io/posts/converting-an-app-to-use-clean-architecture/)
4. To use as a starting point in future projects where clean architecture feels appropriate

It is written 100% in Kotlin with both UI and Unit tests - we will also be keeping this up-to-date as libraries change!

### Disclaimer

Note: The use of clean architecture may seem over-complicated for this sample project. However, this allows us to keep the amount of boilerplate code to a minimum and also demonstrate the approach in a simpler form.

Clean Architecture will not be appropriate for every project, so it is down to you to decide whether or not it fits your needs 🙂

## Languages, libraries and tools used

* [Kotlin](https://kotlinlang.org/)
* [Room](https://developer.android.com/topic/libraries/architecture/room.html)
* [Android Architecture Components](https://developer.android.com/topic/libraries/architecture/index.html)
* Android Support Libraries
* [RxJava2](https://github.com/ReactiveX/RxJava/wiki/What's-different-in-2.0)
* [Koin](https://github.com/InsertKoinIO/koin)
* [Glide](https://github.com/bumptech/glide)
* [Retrofit](http://square.github.io/retrofit/)
* [OkHttp](http://square.github.io/okhttp/)
* [Gson](https://github.com/google/gson)
* [Timber](https://github.com/JakeWharton/timber)
* [Mockito](http://site.mockito.org/)
* [Espresso](https://developer.android.com/training/testing/espresso/index.html)
* [Robolectric](http://robolectric.org/)

## Requirements

* JDK 1.8
* [Android SDK](https://developer.android.com/studio/index.html)
* Android O ([API 28](https://developer.android.com/preview/api-overview.html))
* Latest Android SDK Tools and build tools.

## Architecture

The architecture of the project follows the principles of Clean Architecture. Here's how the sample project implements it:

![architecture](https://github.com/bufferapp/clean-architecture-koin-boilerplate/blob/master/art/architecture.png?raw=true)

The sample app when run will show you a simple list of all the Bufferoos (Buffer team members!).
<p align="center">
<img src="https://github.com/bufferapp/android-clean-architecture-boilerplate/blob/master/art/device_screenshot.png" alt="Drawing" style="width: 10px;"/>
</p>

Let's look at each of the architecture layers and the role each one plays :)

### Presentation

The presentation Module contains everything that is related to the user facing parts of our application. This layer should use MVVM to handle the orchestration of data from the data source to the UI - this will be made up of an Android Architecture Components View Model class implementation which will use the Use Case classes to retrieve data. The View Model class will then create an instance of an immutable UI state class which will then be passed to the View which has subscribed to the data stream. 

This View will then use the model to render it's state. The UI state should be the only state of the View, this way there is only one source of truth for how the display of content is constructed. The UI state should be a sealed class and make use of a Resource State instance (LOADING, SUCCESS, ERROR) so that UI states can easily be composed.

### Data

The Data Module is our access point to external data layers and is used to fetch data from multiple sources (the cache and network), it also contains the Use Case classes (domain logic) which are uses to carry out operations on the data (such as creating an update, closing a conversation). The data layer contains the Data Models which are used in this module and modules higher than it (such as the UI module). The Remote and Cache module models will map to this model, past here the model will never change so it makes no sense to perform any more mappings.

The Use Case classes will use a repository interface which defines the set of operations which can be performed within the data layer. This interface is the implemented by a Data Repository class which will use a Data Store Factory to retrieve an instance of a Data Store Interface to retrieve data from. This Data Store Interface will be implemented by an external layer and allows us to abstract the source of data from our data layer - allowing us to create a more flexible and decoupled data source

### Remote

The Remote module handles all communications with remote sources, in our case it makes simple API calls using a Retrofit interface. This service will be used within a Remote Data store implementation class to retrieve instance of the Remote Model representations - this data store class implements the data store interface from the data layer. The data store class uses a model mapper that will map these Remote models to the model representation found within the Data module.

### Cache

The Cache module handles all communication with the local database which is used to cache data. For this our Database should use the Room architecture component library for the data base, whos data is accessed using DAO classes from said library. 

The cache layer will have it's on Cache Model representations which will be retrieved using the DAOs through the Cache Data Store implementation class, which implements the data store interface from the data layer. This data store class will use a model mapper that will map these Cache models to the model representation found within the Data module.

## Conclusion

We will be happy to answer any questions that you may have on this approach, and if you want to lend a hand with the boilerplate then please feel free to submit an issue and/or pull request 🙂

Again to note, use Clean Architecture where appropriate. This is example can appear as over-architectured for what it is - but it is an example only. The same can be said for individual models for each layer, this decision is down to you. In this example, the data used for every model is exactly the same, so some may argue that "hey, maybe we don't need to map between the presentation and user-interface layer". Or maybe you don't want to modularise your data layer into data/remote/cache and want to just have it in a single 'data' module. That decision is down to you and the project that you are working on 🙌🏻

## Thanks

A special thanks to the authors involved with these two repositories, they were a great resource during our learning!

- https://github.com/android10/Android-CleanArchitecture

- https://github.com/googlesamples/android-architecture
