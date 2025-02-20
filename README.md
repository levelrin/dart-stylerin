[![Build](https://github.com/levelrin/dart-stylerin/actions/workflows/build.yml/badge.svg?branch=main)](https://github.com/levelrin/dart-stylerin/actions/workflows/build.yml?query=branch%3Amain)
[![codecov](https://codecov.io/gh/levelrin/dart-stylerin/graph/badge.svg?token=noW3yHdtQq)](https://codecov.io/gh/levelrin/dart-stylerin)
[![](https://tokei.rs/b1/github/levelrin/dart-stylerin?category=code)](https://github.com/levelrin/dart-stylerin)
[![License](https://img.shields.io/badge/license-MIT-green.svg)](https://github.com/levelrin/dart-stylerin/blob/main/LICENSE)

## About

It's a code formatter for [Dart](https://dart.dev/).

Yes, we have [the official formatter](https://dart.dev/tools/dart-format) already, but it's not good enough.

For example, we have the following code:
```dart
void main(){
  final RussianDoll doll=RussianDoll('Rin',RussianDoll('Revomin',RussianDoll('Ian',RussianDoll('Rajitha'))));
  doll.unwrap();
}
class RussianDoll{
  final String _name;
  final RussianDoll? _child;
  const RussianDoll(this._name,[this._child]);
  RussianDoll? child(){return this._child;}
  void unwrap(){print(this._name);if(this._child!=null){this._child.unwrap();}}
}
```

The official formatter formats the code like this:
```dart
void main() {
  final RussianDoll doll = RussianDoll('Rin',
      RussianDoll('Revomin', RussianDoll('Ian', RussianDoll('Rajitha'))));
  doll.unwrap();
}
```

Our formatter formats the code like this:
```dart
void main() {
  final RussianDoll doll = RussianDoll(
    'Rin',
    RussianDoll(
      'Revomin',
      RussianDoll(
        'Ian',
        RussianDoll('Rajitha')
      )
    )
  );
  doll.unwrap();
}
```

Our format shows the composition better.

## Quick Start

Please install Java 11+ if you haven't already.

1. [Download](https://github.com/levelrin/dart-stylerin/releases) the zip file that matches with your Java version and unzip it to get the jar file.
2. Run the command `java -jar dart-stylerin-{app-version}-{java-version}.jar path/to/target.dart`

## Command Options

```
usage: java -jar dart-stylerin-{app-version}-{java-version}.jar [options]
 -h,--help              Show help messages.
 -q,--quiet             Do not print debug logs.
 -r,--recursive <arg>   Format files in the directory recursively.
 -v,--version           Print the version.
```

## Disclaimer

Not all [grammar rules](https://github.com/antlr/grammars-v4/blob/master/dart2/Dart2Parser.g4) are supported yet.

Please always be ready to recover the file and use our formatter at your own risk.

If you find a bug or have a suggestion, please create a new [issue](https://github.com/levelrin/dart-stylerin/issues).
