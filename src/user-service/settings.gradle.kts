rootProject.name = "easel-user-service"

include(":common-module")
findProject(":common-module")?.projectDir = file("../common-module")
