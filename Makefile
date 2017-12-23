build:
	mkdir -p out/build
	find src/main/ -iname "*.java" > .sourcelist
	javac -d out/build @.sourcelist; rm .sourcelist
