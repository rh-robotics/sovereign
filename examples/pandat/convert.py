import base64

if __name__ == '__main__':
    binary = base64.decodebytes(input("base64: ").encode("ascii"))
    with open(input("out: "), "wb") as f:
        f.write(binary)
