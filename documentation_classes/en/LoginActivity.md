# Class Description: LoginActivity

## 1. General Information
*   **Class Name:** `LoginActivity`
*   **Type:** `Activity`
*   **Purpose:** Handles user authentication (Login and Registration).
*   **Interaction:** Saves data to `SharedPreferences` and starts `MainActivity` upon success.

## 2. Variables
| Name | Type | Purpose | Usage |
| :--- | :--- | :--- | :--- |
| `isLoginMode` | `boolean` | Toggle state | UI updates |
| `etUsername` | `EditText` | Name input | Validation |

## 3. Methods
**Method Name:** `performLogin`
*   **Function:** Compares user input with stored values in memory.
*   **When called:** On "Login" button click.

## 8. Simple Explanation
`LoginActivity` is like a **Guard at a gate**. He has a notebook (`SharedPreferences`) with names. If you are in it, you pass. If not, you can ask him to write your name down (Register).
