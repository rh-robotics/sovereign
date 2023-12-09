PYTHON ?= python3
TO_CONVERT ?= simple.json

.PHONY: all
all: convert_for_development

.PHONY: convert_for_development
convert_for_development: generate_proto
	$(PYTHON) convertForDevelopment.py $(TO_CONVERT)

.PHONY: generate_proto
generate_proto:
	$(PYTHON) -m grpc_tools.protoc -I../proto/ --python_out=. --grpc_python_out=. Transfer.proto

.PHONY: clean
clean:
	rm *.pandat
	rm Transfer*