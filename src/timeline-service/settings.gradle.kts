rootProject.name = "easel-timeline-service"

include(":common-module")
findProject(":common-module")?.projectDir = file("../common-module")
