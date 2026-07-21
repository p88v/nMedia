# nMedia

nMedia is an Android social media application that allows users to view a post feed, create and manage posts, like content and share posts.

The application uses Retrofit for network communication, Room for local data storage and follows an MVVM-based architecture with Repository, Coroutines and Flow.

## Screenshots

<!-- Screenshots will be added here -->

## Features

- View social media feed
- Create new posts
- Edit existing posts
- Delete posts
- Like and unlike posts
- Share posts
- Display likes, shares and views counters
- Local data persistence with Room
- Network communication with Retrofit
- Reactive UI updates with Flow
- Notification permission handling

## Tech Stack

- Kotlin
- Android SDK
- XML
- ViewBinding
- MVVM
- Repository Pattern
- Retrofit
- OkHttp
- Gson
- Room
- Kotlin Coroutines
- Flow
- RecyclerView
- ListAdapter / DiffUtil
- Navigation Component

## Architecture

The application follows an MVVM-based architecture:

```text
UI
│
├── Fragment / Activity
├── RecyclerView / Adapter
│
▼
ViewModel
│
▼
Repository
│
├── Retrofit API
│
└── Room Database
```

The UI layer handles user interactions and observes application state exposed by the ViewModel.

The ViewModel manages UI-related data and delegates data operations to the Repository.

The Repository provides a single entry point for working with remote and local data sources.

## Data Flow

The application combines remote data received through Retrofit with local persistence provided by Room.

```text
Remote API
    ↓
 Retrofit
    ↓
Repository
    ↓
   Room
    ↓
   Flow
    ↓
ViewModel
    ↓
    UI
```

Room provides reactive data updates through Flow, allowing the UI to automatically receive changes when stored posts are updated.

## Post Operations

User interactions are passed through the application layers:

```text
User Action
    ↓
Adapter / Fragment
    ↓
ViewModel
    ↓
Repository
    ↓
Remote API / Room
    ↓
Updated Data
    ↓
Flow
    ↓
UI
```

This approach is used for operations such as:

- Creating posts
- Editing posts
- Deleting posts
- Liking and unliking posts
- Sharing posts

## Local Storage

Room is used for local data persistence.

The database layer consists of:

```text
Entity
  ↓
 DAO
  ↓
Room Database
```

DAO methods provide access to stored posts, while Flow allows database changes to be observed reactively.

## Network Layer

Retrofit is used for communication with the remote REST API.

```text
Retrofit
   ↓
API Service
   ↓
Repository
```

OkHttp is used as the HTTP client, while Gson handles JSON serialization and deserialization.

## Project Structure

```text
ru.netology.nmedia
│
├── activity
├── adapter
├── api
├── dao
├── db
├── dto
├── entity
├── repository
├── service
├── ui
├── util
└── viewmodel
```

## Getting Started

1. Clone the repository.
2. Open the project in Android Studio.
3. Sync the project with Gradle.
4. Run the application on an emulator or Android device.

## What I Practiced

This project was created to practice and consolidate:

- MVVM architecture
- Repository pattern
- REST API integration with Retrofit
- Local persistence with Room
- Kotlin Coroutines
- Flow
- RecyclerView
- ListAdapter and DiffUtil
- CRUD operations
- Network and local data interaction
- Android Navigation Component
- Reactive UI updates
- Runtime permission handling