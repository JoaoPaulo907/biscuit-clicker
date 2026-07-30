# Changelog

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
- Added a `Log` class to centralize logging.
- Minor improvements to the model classes.
