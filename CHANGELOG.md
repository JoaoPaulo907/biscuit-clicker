# Changelog

## v1.0.3

### Visible Changes

- Now every click on the main button displays a simple animation.
- Removed the border from main button (I think it was kinda ugly).

### Internal Changes

- Added an invisible layer for visual effects (which means there can be more of them in the future!).
- Added an `EffectManager` class to centralize effects and animations.
- Added a `line()` method to the `Logger` class to make line breaks easier.

## v1.0.2

### Visible Changes

- Fixed a bug where the bps was not updated after purchasing an upgrade.
- Restyled the purchase and upgrade buttons.

### Internal Changes

- Corrected the version in `pom.xml`.
- Removed duplicated version information from JavaDocs.

## v1.0.1

### Visible Changes

- Now you can no longer upgrade a building you don't own.
- Added a biscuit image to the main button, for obvious reasons (honestly, it should've been there from the start).

### Internal Changes

- Added view classes to make the Controller leaner.
- Added two interfaces to improve polymorphism.
- Added a `Logger` class to centralize logging.
- Minor improvements to the model classes.
