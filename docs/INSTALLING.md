# Installing

Maven

```xml
<dependency>
  <groupId>com.accenture.testing.bdd</groupId>
  <artifactId>bdd-for-all</artifactId>
  <version>1.0.0</version>
</dependency>
```

Buildr

```sbtshell
'com.accenture.testing.bdd:bdd-for-all:jar:1.0.0'
```

Ivy

```xml
<dependency org="com.accenture.testing.bdd" name="bdd-for-all" rev="1.0.0">
  <artifact name="bdd-for-all" type="jar" />
</dependency>
```

Groovy Grape

```scala
@Grapes(
@Grab(group='com.accenture.testing.bdd', module='bdd-for-all', version='1.0.0')
)
```

Gradle/Grails

```sbtshell
compile 'com.accenture.testing.bdd:bdd-for-all:1.0.0'
```

SBT

```sbtshell
libraryDependencies += "com.accenture.testing.bdd" % "bdd-for-all" % "1.0.0"
```

Leiningen

```java
[com.accenture.testing.bdd/bdd-for-all "1.0.0"]
```

