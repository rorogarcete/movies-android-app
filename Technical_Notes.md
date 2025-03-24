## Initial technical analysis
- Monolithic module
  - Data
    - Implement Repository patterns
    - Removed unused class
    - Implemented database migrations
    - Is possible implemented offline mode
  - Domain
    - Create domain layer with models and mappers
    - Paging source must access repositories
  - UI
    - ViewModels must access repositories
- Encapsulate the network layer in a module
  - Separated in distinct functions OkHttpClient and Retrofit 
  - Create qualifiers for expose API service
  - Create class for handler network exceptions
  - Create class for handler network error logs
  - Create class for interceptors
- Encapsulate Design System-related classes in a module
  - Create a generic error component to perform retries
  - Removed commented code and unused imports
  - Used readable name for class. Ex.: Rename Type.kt for TypographySpec.kt or related
  - Used tokens for colors definition. Ex.: Tokens.black
  - Is possible implemented tokens for: 
    - Text and icons
    - Background
    - Lines and borders
    - Skeletons
  - Is possible implements dimensions for:
    - spacing
    - radius
    - elevations
    - icons
    - strokes

## Key issues identified
- Direct access to API services from viewModels
- Use api key in query param 
- No contain domain layer
- Used data response in UI layer

## Actions taken and technical rationale
- Added repository pattern for dataSources
- Implement domain layer
- Implement mappers for data/domain layers
- Implement network module
- Implement logger module 
- Implement features-flag module

## Potential future improvements if more time were available
- Implement structure for features into api/impl modules
- Separated DS in core modules
- Created command line utility for generated features/modules

## Changelog
- Feature
  - Added loggers module
  - Added features-flag module
  - Added network module
- Refactors
  - Added repository patterns in data layer
  - Added clients Module for amplitude and provides OkHttp/Authenticator
  - Added firebase init fakes
  - Updated screens packages
  - Removed movie folder from data layer
- Fix
  - Removed unused preferences keys
  - Removed qualifier MicroService to APIServices
  - Removed unused code/imports and removed dispatcher IO
  - Correct import in screens and moved MainActivity
  - Correct import in viewModels
- Enhancement
  - Added models and mappers for domain layer
  - Added AppLoggingInterceptors qualifier
  - Added local.properties for get access_token
  - Updated libs and used versions catalog
  - Moved DI factories in impl modules
  - Moved pagingSource in domain layer
  - Used domain models in ui layer
  - Use @APIService in repository
- Docs
  - Added technical notes
  - Updated tech notes
  - Added project updated

## Project Updated
Download the project and add ACCESS_TOKEN key with your token in local.properties file
Ex.: ACCESS_TOKEN=your_access_token