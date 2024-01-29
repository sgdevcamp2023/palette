rootProject.name = "easel-gateway-service"

include(":common-module")
findProject(":common-module")?.projectDir = file("../common-module")
