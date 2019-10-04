# Contributing

## Issues
- Include log files where possible.
- Include the information shown on the debug screen if possible.
- Check using the hitbox view and by changing graphical effects first to try to determine the cause of the issue.
- Issue templates should be followed where possible.

## Building from source code
To build this library from source code the Eclipse IDE is currently needed.

The eclipse project for the login servers can be found under [RPGOnlineLoginServer](/RPGOnlineLoginServer).
This is an Eclipse for PHP developers project. No dependecies are currently needed for this project.

The eclipse project for the game engine can be found under [RPGOnline2](/RPGOnline2).
This is an Eclipse for Java developers project. The dependecies can be found under /libs and can be added to the project.

## Pull Requests
Pull requests for the game engine must meet the following conditions:
- Any new code must be well documented in the source code.
- Do not update the /docs folder. This will be done with every new release or snapshot.
- Do not update the version number.
- Any new dependencies will be carefully considered before a change is made.
- The pull request must state the changes made in the code.
- Commit names should be readable and describe the changes made (e.g. instead of "bugs and fixes" use "fixed ___" to notate fixed issues.)

## Code style
### Java
- Tabs (equal to 4 spaces) should be used to indent.
- The `{` should be on the same line as the declaration.
- The `}` should be on its own line.
- Added `@Override` to function overrides
- Package names are `lowercase`. Multiple words should be concatenated.
- Class, interface, enum and annotation names should be in `UpperCamelCase`
- File names should follow the same rules as class names.
- Interface names should not be prefixed with `I`.
- Method names should be in `lowerCamelCase`.
- Constant names should be `CAPITALISED_WITH_UNDERSCORES`.
- Other fields, parameters and local varaible names should be written in `lowerCamelCase` or `lowercase_with_underscores`.
- Generic types should be single-letter indentifiers that are capitalised (e.g. `T`) or a class name with a capital letter appended (e.g. `RequestT`).
- Acronyms in `UpperCamelCase` should be capitalised (e.g. `HTTPServer`).
- Acronyms in `lowerCamelCase` should be lowercase if they are at the start of the declaration (e.g. `httpConnection`).
- Acronyms in `lowercase` should be lowercase (e.g. `http`).
- Java files hould be in UTF-8 or Cp1252
- License text must be included at the start of the file.
- The second item (after the license) of the java file must be a package declaration.
- The package delaration must be followed by the imports for the file.
- Do not use wildcard imports.
- Static imports can be used where they imrpove code readability.
- Method with the same name should be placed together.
- Constructors should be placed together.
- Any methods needed only by the constructor should be placed imediately after the constructors.
- Class fields should be placed at the top of the class.
- Static fields should go before instance fields.
- Private fields should go after public fields while still respecting the above rule.
- The opening and closing brace `{` or `}` of an empty code block should be on separate lines.
- Lines should not exceed 100 characters.
- Line breaks should occur before operators.
- Array declarations should not use the C style declaration: `String[] args` not `String args[]`.
- Switch block fallthrough should be avoided or commented.
- Default blocks should be included in switch statements except for when an enum constant is used.
- The following order should be used for modifiers: `public protected private abstract default static final transient volatile synchronized native strictfp`
- Caught exceptions should not be ignored.

### GLSL
- Tabs (equal to 4 spaces) should be used to indent.
- The `{` should be on the same line as the declaration.
- The `}` should be on its own line.
- Lines should not exceed 80 characters.
- Acronyms in `UpperCamelCase` should be capitalised (e.g. `HTTPServer`).
- Acronyms in `lowerCamelCase` should be lowercase if they are at the start of the declaration (e.g. `httpConnection`).
- Acronyms in `lowercase` should be lowercase (e.g. `http`).
- Line breaks should occur before operators.
- Method names should be in `lowerCamelCase`.
- Constant names should be `CAPITALISED_WITH_UNDERSCORES`.
- Other fields, parameters and local varaible names should be written in `lowerCamelCase` or `lowercase_with_underscores`.
- Files should be names in `lowerCamelCase` or `lowercase_with_underscores`.

### PHP
- Tabs (equal to 4 spaces) should be used to indent.
- The `{` should be on the same line as the declaration.
- The `}` should be on its own line.
- Lines should not exceed 80 characters.
- Acronyms in `UpperCamelCase` should be capitalised (e.g. `HTTPServer`).
- Acronyms in `lowerCamelCase` should be lowercase if they are at the start of the declaration (e.g. `httpConnection`).
- Acronyms in `lowercase` should be lowercase (e.g. `http`).
- Line breaks should occur before operators.
- Method names should be in `lowerCamelCase`.
- Constant names should be `CAPITALISED_WITH_UNDERSCORES`.
- Other fields, parameters and local varaible names should be written in `lowerCamelCase` or `lowercase_with_underscores`.
- Files shoudl be named in `lowerCamelCase`.
