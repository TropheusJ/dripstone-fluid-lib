# Dripstone Fluid Lib

## Setup
```groovy
maven {
    url = "https://maven.devos.community/snapshots/"
}
```
```groovy
modImplimentation(include("io.github.tropheusj:dripstone-fluid-lib:3.0.0"))
```

## Use
Using this lib is as simple as implementing the `DripstoneInteractingFluid`
interface onto your fluid class. See the javadoc for each method on use. An
example implementation can be found in the test mod.
