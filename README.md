
# GameRecord
## Definition
**GameRecord** is a game diary application. It allows users to view all existing games one by one. User can interact with the games. That means when a game is selected, user can see all details about the game. It displays the name of the game with it's cover photo. It shows when that game was published and the publisher company. There is also available platforms that selected game published for. Lastly, it shows how long to beat this game in order to finish it's main story. Also user can rate all of the games from one to five stars. Another page is designed for listing all games that is commented highest to lowest in 2024 by the other sources. User can see the cover photo, genre of the game and platforms of that game in that list. Again user can have the same interactions by clicking the game.

## Usage Scenarios
* Main Page: This is the page where the application starts. At the top of the page, there is **a latest game** button. In this page, user can see all the existing games by scrolling the page down. Games are listed as four by four with a little outer frame for aesthetic purposes.

![Main Screen.png](https://i.ibb.co/yVrbvBY/gr11.png)

*  Game Detail Page: This is the page where user can interact with the game with ranking it from 1 to 5 stars. Also a detailed description is provided for the user with other details. It shows the name of the game with it's cover photo, publish date and publisher company, available platforms and how long to beat information.
  
![DetailPage.jpeg](https://i.ibb.co/3NyLHDp/gr22.png)

* Latest Game Page: This is the page where users can see highest commented games listed from high to low. Again provided informations are cover photo, name, genre and platforms of the game. User can scroll to see all the available games.
  
![LatestGamePage.jpeg](https://i.ibb.co/5kFxnTF/gr33.png)


## Set Up and Run the Project
### **Prerequisites**

Ensure the following tools and software are installed on your system:

-   **Android Studio (Latest Stable Version)**
-   **Java Development Kit (JDK 1.8)**
-   **Gradle (Embedded in Android Studio)**
-   **Internet Connection** (for fetching dependencies)

### **Run**

1. Open a terminal and clone the repository:
 - `git clone <repository_url>` 	
 - `cd gamerecord_app`
2. Open the Project in Android Studio
3. Sync Project with Gradle Files
4. Configure SDK and Dependencies
	-   **Compile SDK Version:** 35
	-   **Target SDK Version:** 34
	-   **Min SDK Version:** 34
5. Build the Project
	- `./gradlew build`
6. Run the Application


## Project Members and Responsibilities

 - **Eren Darak**: Implementation of Game List Page (Main page) and all it's fragment, viewmodel, adapters and layouts. Navigation component and README file.
 - **Kerem Okumuş**: Latest Game Page, its functionalities, fragment, adapter, viewmodel and it's layouts. Architecture and design of the documents.
 - **Ragıp Şamil Bekiryazıcı**: Detail Game Page, its functionalities, fragment, viewmodel and it's layouts. Also API integration.
