rootProject.name = "easel-social-service"

include(":common-module")
findProject(":common-module")?.projectDir = file("../common-module")
