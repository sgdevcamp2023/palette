rootProject.name = "easel-notification-service"

include(":common-module")
findProject(":common-module")?.projectDir = file("../common-module")
