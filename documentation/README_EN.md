# 📱 Android Application Documentation "Trigo_Claude"

________________________________________
## 🧾 General Information
**Project Name:**  
Trigo_Claude  
**Author(s):**  
Zeev Fraiman  
**Date:**  
May 2024  
**Language:**  
Java  
**Development Environment:**  
Android Studio  
**Android Version (minSdk / targetSdk):**  
28 / 36  
________________________________________
## 🎯 Project Goal
•	**What task does the app solve:**  
Helps users study trigonometry using interactive tools and visualization.  
•	**Why is this task important:**  
Trigonometry is often difficult for students due to its abstract nature. Visualization on a trigonometric circle makes learning visual and understandable.  
•	**Target audience:**  
School students, college students, and anyone wanting to refresh their trigonometry knowledge.  
________________________________________
## 📌 Application Requirements
**Functional Requirements**  
•	User authorization.  
•	Interactive trigonometric circle with angle adjustment.  
•	Real-time display of sin, cos, tan values.  
•	Sections for Theory, Practice, and Quizzes.  
•	Learning progress tracking.  
**Non-functional Requirements**  
•	**Performance:** Fast rendering of graphical elements.  
•	**Usability:** Intuitive interface with tabbed navigation.  
•	**Reliability:** Stable performance when switching between fragments.  
________________________________________
## 🧠 General Architecture
•	**Selected Approach:**  
–	MVC (Model-View-Controller)  
•	**Why this was chosen:**  
The project has medium complexity, and the classic Android architecture (Activity/Fragment as Controller) provides sufficient flexibility and ease of implementation without overcomplicating the code.  
•	**Main System Components:**  
–	Activity (MainActivity, LoginActivity) — Lifecycle and navigation management.  
–	Fragments — Logic for individual screens (Theory, Quiz, etc.).  
–	Custom View (TrigonometryCircleView) — Graphical representation of the circle.  
________________________________________
## 🧩 UML Diagram
**Structure Description:**  
[MainActivity] –> [Fragments (Theory, Quiz, etc.)]  
[TheoryFragment] –> [TrigonometryCircleView]  
[MainActivity] –> [SharedPreferences (Data)]  
________________________________________
## 📂 Package Organization
**Package:** `zeev.fraiman.trigo_claude`  
•	**Why these packages are separated:**  
All classes are grouped in one main package to ensure the logical unity of the educational application.  
•	**How this helps scalability:**  
Allows for easy addition of new fragments or helper classes in the future.  
________________________________________
## 🧩 Detailed Class Description
**📌 Class: MainActivity**  
**Role:**  
Main screen and app coordinator.  
**Responsibility:**  
Fragment management, authorization check, handling the navigation menu.  
**Main Methods:**  
- `onCreate()` — UI initialization and login check.  
- `loadFragment()` — Dynamic fragment replacement in the container.  
- `logout()` — Session data reset.  
**Interaction with other classes:**  
Interacts with all fragments and `LoginActivity`.  

**📌 Class: TrigonometryCircleView**  
**Role:**  
Custom graphical component.  
**Responsibility:**  
Drawing the trigonometric circle, vectors, and projections of functions (sin, cos).  

**📌 Class: TheoryFragment**  
**Role:**  
Learning screen.  
**Responsibility:**  
Managing the interactive circle and displaying mathematical data.  
________________________________________
## 🔄 Application Workflow Diagram
**Scenario:**  
1. Launch -> SharedPreferences check -> Redirect to LoginActivity (if not logged in).  
2. LoginActivity -> Data entry -> State saving -> MainActivity.  
3. MainActivity -> Select "Theory" -> Display TheoryFragment.  
4. Interaction with SeekBar -> Update TrigonometryCircleView.  
________________________________________
## 🎨 UI/UX Analysis
•	**Why the interface is made this way:**  
Bottom button navigation was chosen for quick access to main sections.  
•	**Principles used:**  
–	**Simplicity:** Minimum unnecessary elements on the screen.  
–	**Logic:** Sequence of "Theory -> Practice -> Quiz".  
–	**Accessibility:** Large control elements (SeekBar).  
•	**What can be improved:**  
Add dark theme and landscape orientation support for graphs.  
________________________________________
## ⚙️ Threading
**Description:**  
•	Mainly uses the **Main Thread**, as trigonometric calculations are not resource-intensive.  
•	**Prevention of:**  
–	**Hangs (ANR):** Absence of heavy synchronous I/O operations.  
–	**Memory Leaks:** Use of `WeakReference` if necessary and correct management of fragment lifecycle.  
________________________________________
## 💾 Data Management
•	**Where is data stored:**  
SharedPreferences.  
•	**Why this method was chosen:**  
For storing authorization status and simple user progress, this is the most lightweight and efficient method.  
•	**How it is ensured:**  
–	**Safety:** Automatic writing upon state change.  
–	**Correctness:** Data validation before writing.  
________________________________________
## 🌐 Networking
•	Networking is not present in the current version (Offline mode).  
________________________________________
## 🔐 Security (Basic Level)
•	**Is there sensitive data:**  
Only the user's login.  
•	**How is it protected:**  
Stored in private SharedPreferences mode (accessible only to the app).  
________________________________________
## 🧪 Testing
•	**Tests available:**  
–	**Unit tests:** Checking mathematical calculations.  
–	**UI tests:** Checking tab switching.  
________________________________________
## 🐞 Error Handling
•	Exception handling (Division by zero) when calculating tangent/cotangent.  
•	Validation of empty fields during login.  
________________________________________
## ⚡ Performance
•	**Optimization:** Using an efficient `onDraw` method in the custom View.  
•	**Bottlenecks:** Complex animations (if added).  
________________________________________
## 🚀 Extensibility
•	Adding function graphs (sine wave, cosine wave).  
•	Firebase integration for cloud progress storage.  
•	Multi-language support.  
________________________________________
## 📊 Project Self-Assessment
| Criterion | Rating (1–10) |
| :--- | :--- |
| Architecture | 8 |
| Code | 9 |
| UI/UX | 8 |
| Reliability | 10 |
| **Overall Level** | **9** |
________________________________________
## 🏁 Conclusion
•	**What worked best:**  
Interactive trigonometric circle.  
•	**What was difficult:**  
Precise vector drawing and canvas coordinate calculations.  
•	**What skills did you acquire:**  
Working with Canvas in Android, fragment management, state saving.  
________________________________________
## 📎 Appendices
•	Screenshots: (to be added to the images folder)  
•	Diagrams: UML Class Diagram  
•	Repository Links: [GitHub Link]
