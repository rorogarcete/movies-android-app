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
- No contain domain layer
- Used data response in UI layer

## Actions taken and technical rationale
- Added repository pattern for dataSources
- Implement domain layer
- Implement mappers for data/domain layers

## Potential future improvements if more time were available
- Implement structure for features into api/impl modules
- Separated Network/DS in core modules
- Created command line utility for generated features/modules