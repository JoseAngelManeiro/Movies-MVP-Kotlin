# mvp-clean-dagger-rxjava

This version of the app uses [RxJava](https://github.com/ReactiveX/RxJava) to handle the asynchronous work of the UseCases.

### What you need

This project gets the information from [The Movie Database (TMDb) API](https://developers.themoviedb.org/3/getting-started/introduction). If you want to run the project correctly, you will have to use your own **API Key** in the [MoviesRemoteDataSourceImpl](https://github.com/JoseAngelManeiro/Movies-MVP-Kotlin/blob/mvp-clean-dagger-rxjava/app/src/main/kotlin/com/joseangelmaneiro/movies/data/source/remote/MoviesRemoteDataSourceImpl.kt) class.
