# Macavity

![image](https://user-images.githubusercontent.com/49984953/89312258-bab77f80-d67f-11ea-9224-0c5030271988.png)

Macavity is a prototype carpooling application I have developed for Final Year Project module at the University of Kent.

![image](https://user-images.githubusercontent.com/49984953/89315065-133c4c00-d683-11ea-98ca-d1a3d5f2b722.png) 
![image](https://user-images.githubusercontent.com/49984953/89315072-146d7900-d683-11ea-84b8-8d8cb2e94690.png) 
![image](https://user-images.githubusercontent.com/49984953/89315084-16373c80-d683-11ea-81fd-d720b5085c72.png)

<b>Architecture pattern</b>

The application attempts to implement MVVP architecture pattern using ViewModels + Livedata + RxJava.

<b>Navigation</b>

The application uses Navigation component  for navigation between fragments which are hosted by two activities.

<b>Programming language</b>

Kotlin

<b>Dependency injection framework</b>

Dagger 2

<b>Remote database</b>

The database used for the project is Firebase Realtime Database.

<b>Illustrations</b>

Illustrations have been made by Rytis Jonikas, a student from Vilnius Academy of Arts.

<b>Unit testing</b>

Due to the time constrains only an example has been made to showcase the ability of writing tests. The only class that was unit tested in the project was <i>AddJourneyViewModel</i>, the test class was called <i>AddJourneyViewModelTest</i>.

<b>Error handling</b>

Due to the time constrains the application lacks proper error handling. I was planning on catching data related errors with RxJava error-handling operators and passing them to viewmodels where they would propagate them to the view using viemodel error state (enum).

<b>How to compile the project:</b>

Please send me an email asking for a file containing API keys and put it in *\Macavity\app\src\main\res\values
OR
Create your own API keys

<b>Setting up the remote database (Firebase Realtime Database)</b>

Two empty nodes need to be created manually before the logic of the mobile application can work.
- "groups" and "users" 

![image](https://user-images.githubusercontent.com/49984953/89312055-74622080-d67f-11ea-9a6f-fa67f8ab6a80.png)

<b>Report</b>

If anyone is interested in reading a 55-page report about this project, please send me an email.

