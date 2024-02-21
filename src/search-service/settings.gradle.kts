rootProject.name = "easel-search-service"

include(":common-module")
findProject(":common-module")?.projectDir = file("../common-module")
