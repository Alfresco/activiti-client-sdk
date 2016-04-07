# Alfresco Activiti Clients (Early Access)

## Introduction
This project contains Java/Android libs to consume easily Activiti BPM Suite Public REST API.

They include a set of APIs that allows developers to quickly build Activiti-enabled Java & Android applications. 

Those projects are currently used to build [Activiti Android APP](https://github.com/Alfresco/activiti-android-app) available on the [Google Play Store](https://play.google.com/store/apps/details?id=com.activiti.android.app) 

## Important Notice

Alfresco Activiti Client is currently in Early Access mode. It evolves as you use them, as you give feedback, and as the developers update and add content. We like to think app development as services that grow and evolve with the involvement of the community.

##Overview
- **Client Java**: Contains Client API Project for Alfresco Activiti BPM Suite
    * **Activiti Java Client** set of APIs to interact with Alfresco Activiti (1.4+) Public API
    * **Client API Commons** utility used by all client APIs
- **Client Android**: Regroups SDK build on top of Client APIs to solve specific Android problematic (but also Java compatible...)
    * **Activiti Android Client** Simple wrapper on top of Java Client. It's used as foundation for [Activiti Android APP](https://github.com/Alfresco/activiti-android-app)
- **Docs**: contains informations about Activiti REST API

### Status
| Projects   |      Status      |  Informations |
|----------|:-------------:|------:|------:|
|[Activiti Java Client](client-java/activiti-java-client)   | **Beta**   |Full API coverage, simple test  |
|[Client API Commons](client-java/client-commons)   | **Beta**  | To evolve regarding other evolution  |
|[Activiti Android Client](client-android/activiti-android-client)   | **Beta**   |Currently replace the previous Activiti SDK layer in Activiti Android APP  |


## Technical Overview
Client API projects are based on 3 Java/Android librairies

* [OkHttp][2]
* [Retrofit][1]
* [Gson][3]

[Retrofit][1] is a type-safe REST client for Java/Android built by Square. The library provides a powerful framework for authenticating and interacting with APIs.

Retrofit send network requests with [OkHttp][2] an HTTP & HTTP/2 client for Android and Java applications also built by Square. This library makes downloading JSON or XML data from a web API fairly straightforward. 

Once the data is downloaded then it is parsed into a Plain Old Java Object (POJO) which must be defined for each "resource" in the response. Retrofit supports many different parsers for processing network response data ([Gson][3], [Jackson][4], [Moshi][5], [Protobuf][6], [Wire][7], [Simple XML][8])

### Advantages

* Easy to consume: API can be consumed synchronuously and/or asynchronuously and/or in a Reactive Approach ([RxJava][9])
* Easy to setup: API declaration is simple and POJO model can be easily generated via Swagger Definition
* Simple architecture & Easy to customize: Each component can be modified and customized to match 
* Large community and Open Source compatible

## Build

2 maven profiles are available **activiti-client-java** (activated by default) and **activiti-client-all**

**Build all Librairies**
    
    mvn clean install -DskipTests=true


##More informations

**Retrofit**

- [Simple HTTP with Retrofit 2 (Droidcon NYC 2015)](https://speakerdeck.com/jakewharton/simple-http-with-retrofit-2-droidcon-nyc-2015)
- [Consuming APIs with Retrofit](https://guides.codepath.com/android/Consuming-APIs-with-Retrofit#setup)
- [Retrofit 2.0: The biggest update yet on the best HTTP Client Library for Android](http://inthecheesefactory.com/blog/retrofit-2.0/en)
- [Retrofit Series](https://futurestud.io/blog/retrofit-2-upgrade-guide-from-1-9)

**OkHttp**

- [A Few 'Ok' Libraries (Droidcon MTL 2015)](https://speakerdeck.com/jakewharton/a-few-ok-libraries-droidcon-mtl-2015)

**Gson**

- [Gson User Guide](https://github.com/google/gson/blob/master/UserGuide.md)

**RxJava & RxAndroid**

- [Grokking RxJava](http://blog.danlew.net/2014/09/15/grokking-rxjava-part-1/)
- [Retrofit and RxJava, Android multi-threaded REST requests](http://randomdotnext.com/retrofit-rxjava/)


## License

    Alfresco Activiti Client Librairies

    Copyright © 2016 Alfresco Software, Ltd. and others.

    This product distribution is made available under the Apache 2.0 license.
    
    
[1]: http://square.github.io/retrofit/
[2]: http://square.github.io/okhttp/
[3]: https://github.com/google/gson
[4]: http://wiki.fasterxml.com/JacksonHome
[5]: http://github.com/square/moshi
[6]: http://github.com/google/protobuf
[7]: http://github.com/square/wire
[8]: http://simple.sourceforge.net/
[9]: https://github.com/ReactiveX/RxJava