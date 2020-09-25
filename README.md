# MessengerApp
Basic messaging app to display messages over local WebSocket. With automated replies to mimic a second user.

<img src="https://github.com/hmalik144/MessengerApp/blob/master/readme/ezgif.com-video-to-gif%20(1).gif?raw=true" width="400" height="800" />

## Objective
The task was to create an android messaging application over a WebSocket. The application should be a one screen application hosting a message entry box, send button and message list hosting the conversation. The Conversation should be ordered with oldest messages at the top and newest at the bottom. 
 - Message List and Text Entry Box
 - Distinguish between received and sent messages
 - Item sectioning after a break of more than 2 hours of no activity
 - Persistent storage and observables
 - Two-way messaging
 - Animating message entry
 
## Approach

### WebSocket with coroutines
This uses the android kotlin native components, Coroutines, for concurrency. I used the tinder Scarlet WebSocket library, Retrofit inspired WebSocket client, to simply handle the WebSocket operations.
*NB. Nearly all the resources online for tinder scarlet point towards using RxJava - this is quite unique whilst still being "out of the box"*

### Room Database for storing the conversation
Room database is a powerful tool for caching data. Room also offers the use of live data queries which seamlessly allows for displaying messages.

### Trigger a random message after I send one
Since this WebSocket was local there were no messages being received from another member. When a message is sent, by the user, after a delay of second message containing a random string is sent as another user.

### Recycler view to host conversation
I created a recycler view to host the conversation. The messages were submitted via an edit text & submit button - however pressing enter will submit the message. The message clears after it is sent. It handles messages sent, messages received and sectioning messages.

### Item sectioning
When the WebSocket is connected to the last message stored is read for its time stamp. If the duration between that timestamp and now is greater than two hours then a sectioning message is sent to the database.

### Kodein dependency injection
Simple kotlin dependency injection to seamlessly create module is application class and retrieve them via "KodeinAware" implementation.

### Network interception 
If the user is offline then messages will not be sent. With the tinder scarlet library, I used a network interceptor.

## Limitation

### Sent/Not Sent messages?
It’s hard to tell if the message has been successfully sent over the WebSocket. (Work in progress)

### Animation presence minimal
Not much consideration was given to animations in this project (Work in progress). Recycler views have an animation class that can be 

### Online Status
Would have a live data observer working with WebSocket connection to determine connection.

## Architectural Pattern

MVVM - Model View ViewModel
SOLID coding

## Jetpack

* [AndroidX](https://developer.android.com/jetpack)

## Built With

* [Kodein](https://github.com/Kodein-Framework/Kodein-DI) - Painless Kotlin Dependency Injection
* [Scarlet](https://github.com/Tinder/Scarlet) - A Retrofit inspired WebSocket client for Kotlin, Java, and Android
* [Okhttp](https://github.com/square/okhttp) - Square’s meticulous HTTP client for Java and Kotlin.
* [Room Persistence Library](https://developer.android.com/topic/libraries/architecture/room) - The Room persistence library provides an abstraction layer over SQLite to allow for
* [Gson](https://github.com/google/gson)- A Java serialization/deserialization library to convert Java Objects into JSON and back

## Submitted by

* **Haider Malik** - *Android Developer*
