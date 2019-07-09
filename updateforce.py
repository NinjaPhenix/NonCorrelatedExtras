from os import system

if __name__ == "__main__":
    system("gradlew --stop")
    system("gradlew cleanLoomBinaries")
    system("gradlew cleanLoomMappings")
    system("gradlew cleanIdea")
    system("gradlew openIdea")
    input("Press any key to exit.")
