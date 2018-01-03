# Compiling
```bash
make build
```
equivalent to
```bash
mkdir -p out/build
find src/main/ -iname "*.java" > .sourcelist
javac -d out/build @.sourcelist; rm .sourcelist
```

# Running
```bash
cd out/build/
java agh.cs.actparser.ActParserApplication -f "../../assets/uokik.txt" 
```

## Available options
See `agh.cs.actparser.ActParserApplication -h`:

```
-a --articles   One or more articles (artykuły) to display. Format: Range
-c --chapters   Chapters (rozdziały) to dispay. Format: Range
-f --file       File containing document to parse. Format: Text
-h --help       Display this help message. Format: Flag
-l --letter     Letter (litera) to display. Format: Identifier
-u --paragraph  Paragraph (ustęp) to display. Format: Identifier
-p --point      Point (punkt) to display. Format: Identifier
-s --sections   One or more sections (działy) to display. Format: Range
-t --toc        Whether to display table of contents. Format: Flag
Available input formats with examples:
Range: 1..4 or I..IV or 2 (equivalent to 2..2)
Identifier: 3 or III or 105ia
String: konstytucja.txt
Flag: (does not require any value)

Example invocation:
-a 2 -u 2 -p 2 -l a -f ../../assets/uokik.txt
```
