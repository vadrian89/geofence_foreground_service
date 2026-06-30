# Agent Guide: Geofence Foreground Service

## Architecture Overview
This is a Flutter plugin providing geofencing with persistent foreground services on Android and standard background updates on iOS.

- **Frontend (Dart):** `lib/geofence_foreground_service.dart` is the main entry point. Platform implementations reside in `geofence_foreground_service_method_channel.dart`.
- **Android Implementation:**
  - `GeofenceForegroundServicePlugin.kt`: Entry point, handles `MethodChannel` calls and coordinates geofence registration via `GeofencingClient`.
  - `GeofenceForegroundService.kt`: Android Foreground Service that handles geofence transition intents.
  - `BackgroundWorker.kt`: `ListenableWorker` that spawns a secondary `FlutterEngine` to execute Dart logic via the `callbackDispatcher`.
- **Key Integration:** Uses Google Play Services Geofencing and Android WorkManager for background task execution.

## Critical Patterns
- **Background Dispatcher:** Users must pass a top-level Dart function (the `callbackDispatcher`) marked with `@pragma('vm:entry-point')`. The Android side stores its handle in `SharedPreferences` via `SharedPreferenceHelper`.
- **Method Channels:**
  - Foreground: `ps.byshy.geofence/foreground_geofence_foreground_service`
  - Background: `ps.byshy.geofence/background_geofence_foreground_service`
- **Intent Extras:** Names are generated via `extraNameGen` utility to prevent collisions with other plugins.
- **Zone Processing:** The plugin calculates the center of polygon coordinates on Android to register a circular geofence (`GeofenceBuilder.kt`).

## Documentation Rule
- Do not use local absolute file paths (for example, `/home/...`) or `file://` links in docs; use repository-relative paths only.

## Key Files
- `lib/geofence_foreground_service.dart` - Public Dart API.
- `android/src/main/kotlin/com/f2fk/geofence_foreground_service/GeofenceForegroundServicePlugin.kt` - Android bridge.
- `android/src/main/kotlin/com/f2fk/geofence_foreground_service/BackgroundWorker.kt` - Manages background Dart execution.
- `android/src/main/kotlin/com/f2fk/geofence_foreground_service/utils/SharedPreferenceHelper.kt` - Manages persistence of callback handles and service configuration.

## Developer Workflow
- **Environment:** Requires Android `minSdkVersion` 29+ and iOS 12.0+.
- **Build:** `flutter pub get` in root, then standard build commands.
- **Testing:** `flutter test` for unit tests (mocks method channels). Use the `example/` project for manual verification of foreground service behavior.
- **Debug Mode:** Setting `isInDebugMode: true` triggers local notifications via `DebugHelper` for task start/complete events.
