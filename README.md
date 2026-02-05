# Notaday - Daily Notes & Todo App

A minimal, elegant daily note-taking and to-do management app for Android, designed for professionals, freelancers, and artists.

## Features

### Core Functionality
- 📅 **Daily Notes**: Create and manage notes organized by date with an intuitive calendar view
- ✅ **Todo Management**: Convert notes to todos with deadlines, reminders, and priorities
- 🔍 **Search**: Quickly find notes across all dates
- 📎 **Attachments**: Add photos and documents (up to 25MB per file, max 10 per note)
- 🔔 **Notifications**: Smart reminders using WorkManager with Doze mode support
- 📄 **PDF Export**: Professional A4 format exports with customizable options
- 📤 **Share**: Share notes via WhatsApp, Email, SMS, and more

### Technical Highlights
- **Modern Architecture**: Clean Architecture + MVVM pattern
- **Jetpack Compose**: Full Material Design 3 UI with iOS-inspired minimal theme
- **Local-First**: Room database for offline-first experience
- **Dependency Injection**: Hilt for clean, testable code
- **Kotlin Coroutines**: Efficient async operations
- **Dark Mode**: Full light/dark theme support

## Tech Stack

- **Language**: Kotlin
- **UI**: Jetpack Compose with Material Design 3
- **Architecture**: Clean Architecture + MVVM
- **Database**: Room
- **DI**: Hilt
- **Async**: Kotlin Coroutines + Flow
- **Notifications**: WorkManager
- **Image Loading**: Coil
- **PDF Generation**: Android PdfDocument

## Requirements

- **Min SDK**: 26 (Android 8.0)
- **Target SDK**: 34
- **JDK**: 17

## Building the App

### Using Android Studio
1. Clone the repository
2. Open the project in Android Studio
3. Sync Gradle files
4. Run on device/emulator

### Using Command Line
```bash
# Debug build
./gradlew assembleDebug

# Release build
./gradlew assembleRelease
```

## GitHub Actions - Automatic APK Builds

This project includes GitHub Actions workflow that automatically builds APKs:

### Triggers
- Push to `main`, `develop`, or `claude/**` branches
- Pull requests to `main`
- Manual workflow dispatch
- Tag pushes (creates GitHub releases)

### Artifacts
- **Debug APK**: Available in Actions tab (30 days retention)
- **Release APK**: Unsigned, ready for signing (30 days retention)

### Downloading APKs
1. Go to the **Actions** tab in GitHub
2. Click on the latest workflow run
3. Scroll to **Artifacts** section
4. Download `notaday-debug-apk` or `notaday-release-apk`

### Creating a Release
To create a GitHub release with APK files:
```bash
git tag v1.0.0
git push origin v1.0.0
```

## Project Structure

```
app/
├── data/
│   ├── local/
│   │   ├── dao/          # Room DAOs
│   │   ├── database/     # Database and converters
│   │   └── entities/     # Room entities
│   └── repository/       # Repository implementations
├── domain/
│   ├── model/            # Business models
│   ├── repository/       # Repository interfaces
│   └── usecase/          # Business logic
├── presentation/
│   ├── calendar/         # Calendar/Home screen
│   ├── notedetail/       # Note creation/editing
│   ├── notelist/         # All notes list
│   ├── theme/            # App theming
│   ├── navigation/       # Navigation setup
│   └── components/       # Reusable UI components
├── di/                   # Dependency injection modules
└── util/                 # Utility classes
```

## Database Schema

### Note Entity
- id, title, content, date
- createdAt, updatedAt
- isTodo, isCompleted
- deadline, reminderTime, priority

### Attachment Entity
- id, noteId (FK)
- filePath, fileType, fileName
- createdAt

**Relationship**: Note 1-to-many Attachments (cascade delete)

## Permissions

The app requests the following permissions:
- **Camera**: For taking photos
- **Storage**: For accessing gallery and files (API level specific)
- **Notifications**: For todo reminders (Android 13+)
- **Exact Alarms**: For precise notification timing

## Design Philosophy

- **Minimal**: Clean, iOS-inspired design with soft pastel colors
- **Professional**: No technical jargon in UI
- **Efficient**: Fast, smooth animations and transitions
- **Accessible**: Full dark/light mode support

## Future Enhancements (Post-MVP)

- Cloud sync
- Home screen widget
- Recurring todos
- Voice notes
- Note templates
- Export to other formats (Markdown, JSON)

## License

[Add your license here]

## Contributing

[Add contributing guidelines]

## Contact

[Add contact information]

---

Built with ❤️ using Kotlin and Jetpack Compose
