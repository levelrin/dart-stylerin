/*
 * Copyright (c) 2020 Rin (https://www.levelrin.com)
 *
 * This file has been created under the terms of the MIT License.
 * See the details at https://github.com/levelrin/DartStyleRin/blob/master/LICENSE
 */

import 'package:dart_style_rin/files/leaked_code_files.dart';
import 'package:test/test.dart';

void main() {
  group('LeakedCodeFiles', () {
    test('.check() should return the injected exit code.', () {
      expect(
        LeakedCodeFiles(0, 2).check(),
        0
      );
    });
    test('.format() should return the injected exit code.', () {
      expect(
        LeakedCodeFiles(0, 2).format(),
        2
      );
    });
  });
}