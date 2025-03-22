## Initial technical analysis
- Monolithic module
  - Data
    - Implement Repository patterns
    - Removed unused class
    - Implemented database migrations
    - Is possible implemented offline mode
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
  - Removed commented code and unused import
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
- Monolithic module for features
- Network/DS in monolithic modules 
- Direct access to API services from viewModels

## Actions taken and technical rationale
- Implement structure for features into api/impl modules for separation of implementations
- Separated Network/DS in core modules
- Added repository pattern for dataSources

## Potential future improvements if more time were available
- Created command line utility for generated features/modules