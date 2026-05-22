# Class Description: MainActivity

## 1. General Information
*   **Class Name:** `MainActivity`
*   **Type:** `Activity` (Main App Screen)
*   **Purpose:** Serves as the primary entry point and navigation hub. It manages switching between educational sections using fragments.
*   **Interaction:** Coordinates with `LoginActivity`, `SharedPreferences`, and fragments like `TheoryFragment`, `PracticeFragment`, and `QuizFragment`.

## 2. Variables (Fields)
| Name | Type | Purpose | Usage |
| :--- | :--- | :--- | :--- |
| `sharedPreferences` | `SharedPreferences` | Local storage | Login session check |
| `fragmentContainer` | `FrameLayout` | UI Placeholder | Holds current fragment |
| `btnTheory`... | `Button` | Navigation controls | Switching screens |

## 3. Class Methods
**Method Name:** `loadFragment`
*   **Type:** `private`
*   **Return Value:** `void`
*   **Parameters:** `Fragment fragment`
*   **Function:** Replaces the current fragment in the UI container with a new one using `FragmentManager`.
*   **When called:** When a user clicks a navigation button.

## 4. Lifecycle
*   **`onCreate()`:** Initializes the UI, checks if the user is logged in, and loads the default fragment (Theory).

## 7. General Logic
The class acts as a director. It checks the "credentials" (login status). If valid, it presents the menu. When a menu item is selected, it swaps the displayed fragment.

## 8. Simple Explanation
Think of `MainActivity` as a **Smart TV**. The TV itself is the Activity. The different apps (YouTube, Netflix, etc.) are the **Fragments**. The remote control buttons switch between them. If the TV detects no subscription (no login), it shows the "Sign In" screen (`LoginActivity`).
