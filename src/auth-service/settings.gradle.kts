rootProject.name = "easel-auth-service"

include(":common-module")
findProject(":common-module")?.projectDir = file("../common-module")
