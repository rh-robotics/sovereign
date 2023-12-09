import sys
import json
import base64
import Transfer_pb2
from google.protobuf.json_format import ParseDict

timestamp_format = "%Y-%m-%d-%H:%M:%S"


def read_json_file(json_file_path):
    with open(json_file_path, 'r') as file:
        return json.load(file)


def convert_to_grpc(i, call):
    print("Converting message of type '" + call['type'] + "' (at '" + str(
        call['timestamp']['seconds']) + ":" + str(call['timestamp']['nanos']) + "'):\n" + str(
        call['message']), end="\n\n")

    assert (hasattr(Transfer_pb2, call['type']) and callable(getattr(Transfer_pb2, call['type'])))
    message = ParseDict(call['message'], getattr(Transfer_pb2, call['type'])())
    data = base64.b64encode(message.SerializeToString()).decode('utf-8')

    return {
        'timestamp': call['timestamp'],
        'command': {'type': call['type'], 'data': data}
    }


def main():
    json_file_path = sys.argv[1]
    output_file_path = sys.argv[1].replace(".json", ".pandat")

    # Read JSON file
    data = read_json_file(json_file_path)

    # Loop over all the calls.
    calls = []
    for i, call in enumerate(data):
        calls.append(convert_to_grpc(i, call))

    with open(output_file_path, "w+") as f:
        json.dump(calls, f, indent=4)

    print("Output written to '" + output_file_path + "'.")


if __name__ == '__main__':
    main()
