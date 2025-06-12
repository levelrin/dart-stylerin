// Define return types and classes
class ProcessedData {

  final String content;

  ProcessedData(this.content);

}

class RawData {

  final String data;

  RawData(this.data);

}

class EncodedData {

  final String encoded;

  EncodedData(this.encoded);

}

// Simulate async operation returning a Future<RawData>
Future<RawData> fetchRawData({required String source, required int retries}) async {
  await Future.delayed(Duration(milliseconds: 100));
  return RawData('raw_from_$source');
}

// Top-level function that receives a callback returning a Future
ProcessedData processData(Future<EncodedData> Function() asyncOperation) {
  // We're using `.then` inside the function to chain and transform the Future
  EncodedData? result;
  asyncOperation().then((EncodedData data) {
    result = data;
  });
  // In real code, you'd return a Future here,
  // but to match the original structure, we're simplifying
  return ProcessedData(result?.encoded ?? "default");
}

// Main function
void main() {
  final output = processData(
        () async {
      return (
          fetchRawData(
            source: "serverA",
            retries: 3,
          ) as Future<RawData>
      ).then((final RawData raw) => EncodedData("encoded_${raw.data}"));
    },
  );
  print("Processed content: ${output.content}");
}
